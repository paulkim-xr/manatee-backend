package com.rathon.manatee.auth.controller;

import com.rathon.manatee.auth.dto.LoginDto;
import com.rathon.manatee.auth.model.AuthStage;
import com.rathon.manatee.auth.model.MfaSessionRegistry;
import com.rathon.manatee.auth.model.MfaStatus;
import com.rathon.manatee.auth.service.MfaService;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.service.EmployeeService;
import com.rathon.manatee.database.service.mapper.EmployeeMapperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class SessionController {
    public static final String AUTH_STAGE = "authentication stage";
    public static final String MFA_AUTH_STATUS = "mfa auth status";

    private final AuthenticationManager authManager;
    private final EmployeeService employeeService;
    private final MfaService mfaService;
    private final EmployeeMapperService employeeMapperService;

    public SessionController(AuthenticationManager authManager, EmployeeService employeeService, MfaService mfaService, EmployeeMapperService employeeMapperService, MfaSessionRegistry mfaSessionRegistry) {
        this.authManager = authManager;
        this.employeeService = employeeService;
        this.mfaService = mfaService;
        this.employeeMapperService = employeeMapperService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> check(Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(Map.of("username", auth.getName(),"name", employeeService.findByUsername(auth.getName()).getName(),"permissions", auth.getAuthorities()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request,
                                   @RequestBody LoginDto authRequest) {
        HttpSession session = request.getSession(false);
        if (session != null && AuthStage.FULLY_AUTHENTICATED.equals(session.getAttribute(AUTH_STAGE))) {
            return ResponseEntity.badRequest().body("Already logged in");
        }

        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());
            Authentication auth = authManager.authenticate(token);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            Employee employee = employeeService.findByUsername(authRequest.username());

            if (Boolean.TRUE.equals(employee.getOtpEnabled())) {
                session.setAttribute(AUTH_STAGE, AuthStage.PASSWORD_VERIFIED);
                session.setAttribute("otpInitTime", Instant.now());
                session.setAttribute("otpAttempts", 0);

                Long companyId = employeeMapperService.toDto(employee).getCompany().id();
                if (Boolean.TRUE.equals(employee.getBioEnabled())) {
                    session.setAttribute(MFA_AUTH_STATUS, MfaStatus.PENDING);
                    MfaSessionRegistry.registerSession(employee.getUsername(), session);
                    mfaService.notifyDevice(String.valueOf(companyId), employee.getUsername(), "Manatee", "Login with fingerprint", "biometric/authenticate");
                } else {
                    mfaService.notifyDevice(String.valueOf(companyId), employee.getUsername());
                }
                return ResponseEntity.ok(Map.of("mfaRequired", true));
            } else {
                session.setAttribute(AUTH_STAGE, AuthStage.FULLY_AUTHENTICATED);
                return ResponseEntity.ok(Map.of(
                        "username", employee.getUsername(),
                        "name", employee.getName(),
                        "permissions", auth.getAuthorities()
                ));
            }

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/otp")
    public ResponseEntity<?> otp(HttpServletRequest request, @RequestParam String otp) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(AUTH_STAGE) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        AuthStage stage = (AuthStage) session.getAttribute(AUTH_STAGE);
        if (stage == AuthStage.FULLY_AUTHENTICATED) {
            return ResponseEntity.badRequest().body("Already fully authenticated");
        }
        if (stage != AuthStage.PASSWORD_VERIFIED) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("MFA not applicable");
        }

        Instant otpStart = (Instant) session.getAttribute("otpInitTime");
        if (otpStart != null && Duration.between(otpStart, Instant.now()).toMinutes() > 5) {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("MFA session expired");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);

        Object attemptsObj = session.getAttribute("otpAttempts");
        int attempts = (attemptsObj instanceof Integer) ? (Integer) attemptsObj : 0;
        if (++attempts > 5) {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Too many OTP attempts");
        }
        session.setAttribute("otpAttempts", attempts);

        Long companyId = employeeMapperService.toDto(employee).getCompany().id();
        if (!mfaService.verifyOtp(username, companyId, otp)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }

        // OTP is valid
        session.setAttribute(AUTH_STAGE, AuthStage.FULLY_AUTHENTICATED);
        session.setAttribute("isLoggedIn", true);

        return ResponseEntity.ok(Map.of(
                "username", employee.getUsername(),
                "name", employee.getName(),
                "permissions", auth.getAuthorities()
        ));
    }

    @GetMapping("/otp/status")
    public ResponseEntity<?> otpStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);

        return ResponseEntity.ok(employee.getOtpEnabled());
    }

    @PostMapping("/otp/enroll")
    public ResponseEntity<?> enrollOtp() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);
        employee.setOtpEnabled(true);
        employee.setBioEnabled(true);

        employeeService.update(employee);
        EmployeeDto dto = employeeMapperService.toDto(employee);

        String secret = mfaService.enrollOtp(String.valueOf(dto.getCompany().id()), dto.getUsername());

        return ResponseEntity.ok(Map.of(
                "companyId", dto.getCompany().id(),
                "username", dto.getUsername(),
                "secret", secret
        ));
    }

    @PostMapping("/otp/disable")
    public ResponseEntity<?> disableOtp() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);
        employee.setOtpEnabled(false);
        employee.setBioEnabled(false);
        employeeService.update(employee);
        EmployeeDto dto = employeeMapperService.toDto(employee);

        mfaService.disableOtp(String.valueOf(dto.getCompany().id()), dto.getUsername());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/mfa/callback")
    public ResponseEntity<?> callback(@RequestParam String username, @RequestParam Boolean success) {
        MfaSessionRegistry.markStatus(username, success ? MfaStatus.FIDO2_VERIFIED : MfaStatus.NOT_VERIFIED);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mfa/polling")
    public ResponseEntity<?> polling(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(AUTH_STAGE) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        AuthStage stage = (AuthStage) session.getAttribute(AUTH_STAGE);
        if (stage == AuthStage.FULLY_AUTHENTICATED) {
            return ResponseEntity.badRequest().body("Already fully authenticated");
        }
        if (stage != AuthStage.PASSWORD_VERIFIED) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("MFA not applicable");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        MfaStatus status = (MfaStatus) MfaSessionRegistry.getSession(auth.getName()).getAttribute(MFA_AUTH_STATUS);
        if (status == null || status.equals(MfaStatus.NOT_VERIFIED)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Verification not started or failed");
        } else if (status.equals(MfaStatus.PENDING)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Authentication pending");
        }

        session.setAttribute(AUTH_STAGE, AuthStage.FULLY_AUTHENTICATED);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }
}
