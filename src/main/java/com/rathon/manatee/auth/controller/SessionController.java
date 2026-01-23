package com.rathon.manatee.auth.controller;

import com.rathon.manatee.auth.config.passwordless.UsernameOnlyAuthenticationToken;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class SessionController {
    public static final String AUTH_STAGE = "authentication stage";
    public static final String MFA_AUTH_STATUS = "mfa auth status";
    public static final String USERNAME = "username";
    public static final String OTP_INIT_TIME = "otpInitTime";
    public static final String OTP_ATTEMPTS = "otpAttempts";

    private final String mfaServerUrl;
    private final String mfaServerKey;

    private final AuthenticationManager authManager;
    private final EmployeeService employeeService;
    private final MfaService mfaService;
    private final EmployeeMapperService employeeMapperService;

    public SessionController(AuthenticationManager authManager, EmployeeService employeeService, MfaService mfaService, EmployeeMapperService employeeMapperService, @Value("${mfa.base}") String mfaServerUrl, @Value("${mfa.key}") String mfaServerKey) {
        this.authManager = authManager;
        this.employeeService = employeeService;
        this.mfaService = mfaService;
        this.employeeMapperService = employeeMapperService;
        this.mfaServerUrl = mfaServerUrl;
        this.mfaServerKey = mfaServerKey;
    }

    @GetMapping("/me")
    public ResponseEntity<?> check(Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(Map.of("username", auth.getName(),"name", employeeService.findByUsername(auth.getName()).getName(),"permissions", auth.getAuthorities()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> cancelLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/username")
    public ResponseEntity<?> username(HttpServletRequest request, @RequestParam String username) {
        if (request.getSession() != null && AuthStage.FULLY_AUTHENTICATED.equals(request.getSession().getAttribute(AUTH_STAGE))) {
            return ResponseEntity.badRequest().body("Already logged in");
        }

        Employee employee = employeeService.findByUsername(username);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        EmployeeDto dto = employeeMapperService.toDto(employee);

        HttpSession session = request.getSession(true);
        session.setAttribute(USERNAME, employee.getUsername());

        log.info("session id: {}", session.getId());

        log.info("Session attribute: {}", session.getAttribute(USERNAME));
        log.info("Passwordless: {}", employee.getPasswordless());
        boolean otp = mfaService.verifyOtpV2("", dto.getUsername());
        boolean fido2 = mfaService.verifyFido2V2("", dto.getUsername());
        log.info("otp: {}, fido2: {}", otp, fido2);
        // mfaService.getOtpStatusV1(String.valueOf(dto.getCompany().id()), dto.getUsername())
        if (employee.getPasswordless() && (otp || fido2)) { // mfaService.getBioStatusV1(String.valueOf(dto.getCompany().id()), dto.getUsername()))
            Authentication auth = authManager.authenticate(new UsernameOnlyAuthenticationToken(username));
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            session.setAttribute(AUTH_STAGE, AuthStage.PASSWORD_VERIFIED);
            session.setAttribute(OTP_INIT_TIME, Instant.now());
            session.setAttribute(OTP_ATTEMPTS, 0);

            session.setAttribute(MFA_AUTH_STATUS, MfaStatus.PENDING);
            MfaSessionRegistry.registerSession(employee.getUsername(), session);
            String mfaUrl = buildMfaRedirectUrl(request, employee.getUsername());
            return ResponseEntity.ok(Map.of("mfaRequired", true, "mfaUrl", mfaUrl));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Enter password");
    }

    @PostMapping("/password")
    public ResponseEntity<?> password(HttpServletRequest request, @RequestParam String password) {
        if (request.getSession() != null && AuthStage.FULLY_AUTHENTICATED.equals(request.getSession().getAttribute(AUTH_STAGE))) {
            return ResponseEntity.badRequest().body("Already logged in");
        }

        HttpSession session = request.getSession(false);
        log.info("session id: {}", session.getId());
        if (request.getSession().getAttribute(USERNAME) == null) {
            return ResponseEntity.badRequest().body("Enter username first");
        }
        
        return login(request, new LoginDto((String) request.getSession().getAttribute(USERNAME), password));
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

            if (mfaService.verifyOtpV2(String.valueOf(employeeMapperService.toDto(employee).getCompany().id()), employee.getUsername()) || mfaService.verifyFido2V2(String.valueOf(employeeMapperService.toDto(employee).getCompany().id()), employee.getUsername())) { // mfaService.getBioStatusV1(String.valueOf(employeeMapperService.toDto(employee).getCompany().id()), employee.getUsername())
                session.setAttribute(AUTH_STAGE, AuthStage.PASSWORD_VERIFIED);
                session.setAttribute(OTP_INIT_TIME, Instant.now());
                session.setAttribute(OTP_ATTEMPTS, 0);

                session.setAttribute(MFA_AUTH_STATUS, MfaStatus.PENDING);
                MfaSessionRegistry.registerSession(employee.getUsername(), session);
                String mfaUrl = buildMfaRedirectUrl(request, employee.getUsername());
                return ResponseEntity.ok(Map.of("mfaRequired", true, "mfaUrl", mfaUrl));
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

        Instant otpStart = (Instant) session.getAttribute(OTP_INIT_TIME);
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

        Object attemptsObj = session.getAttribute(OTP_ATTEMPTS);
        int attempts = (attemptsObj instanceof Integer) ? (Integer) attemptsObj : 0;
        if (++attempts > 5) {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Too many OTP attempts");
        }
        session.setAttribute(OTP_ATTEMPTS, attempts);

        Long companyId = employeeMapperService.toDto(employee).getCompany().id();
        // mfaService.verifyOtpV1(username, companyId, otp)
        if (!mfaService.authOtpV2(username, companyId, otp)) {
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

        // mfaService.getOtpStatusV1(String.valueOf(employeeMapperService.toDto(employee).getCompany().id()), employee.getUsername())
        return ResponseEntity.ok(mfaService.verifyOtpV2(String.valueOf(employeeMapperService.toDto(employee).getCompany().id()), employee.getUsername()));
    }

    @PostMapping("/mfa/enroll")
    public ResponseEntity<?> mfaEnroll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);

        String userHandle = mfaService.enrollV2(username, employee.getName());
        return ResponseEntity.ok(Map.of(
                "username", username,
                "userHandle", userHandle,
                "server", mfaServerUrl
        ));
    }

    @PostMapping("/mfa/disable")
    public ResponseEntity<?> mfaDisable() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);

        if (mfaService.verifyFido2V2(null, username)) {
            mfaService.disableFido2V2(null, username);
            employee.setBioEnabled(false);
        }
        if (mfaService.verifyOtpV2(null, username)) {
            mfaService.disableOtpV2(null, username);
            employee.setOtpEnabled(false);

        }

        employee.setPasswordless(false);
        employeeService.update(employee);


        mfaService.deleteV2(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mfa/status")
    public ResponseEntity<?> mfaStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        String userHandle = mfaService.verifyV2(username);
        boolean status = userHandle != null && !userHandle.isEmpty();

        return ResponseEntity.ok(status);
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
        employeeService.update(employee);
        EmployeeDto dto = employeeMapperService.toDto(employee);

        String userHandle = mfaService.verifyV2(employee.getUsername());
        MfaService.OtpRegistrationData data = mfaService.enrollOtpV2(String.valueOf(dto.getCompany().id()), dto.getUsername());
        String secret = data.getSecret();
        MfaService.OtpPolicy policy = data.getPolicy();
        String algorithm = policy.getAlgorithm().name();
        String type = policy.getType().name();
        int digits =  policy.getDigits();
        int windowSize =  policy.getWindowSize();
        int keyLength = policy.getKeyLength();
        int counter = policy.getCounter();
        int period = policy.getPeriod();

        return ResponseEntity.ok(Map.of(
                "server", mfaServerUrl,
                "username", dto.getUsername(),
                "userHandle", userHandle,
                "id", dto.getId(),
                "email", dto.getEmail(),
                "tel", dto.getPhone(),
                "secret", secret,
                "policy", Map.of(
                    "algorithm", algorithm,
                    "type", type,
                    "digits", String.valueOf(digits),
                    "windowSize", String.valueOf(windowSize),
                    "keyLength", String.valueOf(keyLength),
                    "counter", String.valueOf(counter),
                    "period", String.valueOf(period)
                )
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
        disableBio();
        employee.setBioEnabled(false);
        disablePasswordless();
        employee.setPasswordless(false);
        employeeService.update(employee);
        EmployeeDto dto = employeeMapperService.toDto(employee);

        mfaService.disableOtpV2(String.valueOf(dto.getCompany().id()), dto.getUsername());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/otp/reset")
    public ResponseEntity<?> resetOtp(@RequestParam String password) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(auth.getName(), password);
            authManager.authenticate(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password incorrect");
        }

        ResponseEntity<?> disableResult = disableOtp();
        if (disableResult.getStatusCode().is4xxClientError() || disableResult.getStatusCode().is5xxServerError()) {
            return disableResult;
        }

        return enrollOtp();
    }

    @GetMapping("/bio/status")
    public ResponseEntity<?> bioStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        log.info("Username: {}", username);
        Employee employee = employeeService.findByUsername(username);
        EmployeeDto dto = employeeMapperService.toDto(employee);
        boolean status = mfaService.verifyFido2V2(String.valueOf(dto.getCompany().id()), dto.getUsername());
//        boolean status = mfaService.getBioStatusV1(String.valueOf(dto.getCompany().id()), dto.getUsername());
        MfaService.OtpPolicy policy = mfaService.getOtpPolicyV2(username);
        int digits = policy.getDigits();

        return ResponseEntity.ok(Map.of(
                "bio", status,
                "otp", digits
        ));
    }

    @PostMapping("/bio/enroll")
    public ResponseEntity<?> enrollBio() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);

        // mfaService.getOtpStatusV1(String.valueOf(employeeMapperService.toDto(employee).getCompany().id()), employee.getUsername())
//        if (!mfaService.verifyOtpV2(String.valueOf(employeeMapperService.toDto(employee).getCompany().id()), employee.getUsername())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Must be enrolled in OTP first");
//        }

        employee.setBioEnabled(true);
        employeeService.update(employee);
        EmployeeDto dto = employeeMapperService.toDto(employee);
        mfaService.enrollFidoV2(dto.getUsername(), dto.getName());
//        mfaService.notifyDeviceV1(String.valueOf(dto.getCompany().id()), dto.getUsername(), "Manatee", "지문 등록", "biometric/register");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/bio/disable")
    public ResponseEntity<?> disableBio() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);
        employee.setBioEnabled(false);
        employeeService.update(employee);
        EmployeeDto dto = employeeMapperService.toDto(employee);

        mfaService.disableFido2V2(String.valueOf(dto.getCompany().id()), dto.getUsername());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/mfa/callback")
    public ResponseEntity<?> callback(@RequestBody MfaService.ApiResponse<String> request) {
        log.info(request.toString());
        if (MfaService.MfaResult.TEST.equals(request.getCode())) {
            return ResponseEntity.ok().build();
        }
//        if (mfaServerKey == null || !mfaServerKey.equals(key)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        String username = request.getData();
        boolean success = request.isSuccess();
        log.info("Username: {}, success: {}", username, success);
        MfaSessionRegistry.markStatus(username, success ? MfaStatus.VERIFIED : MfaStatus.NOT_VERIFIED);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mfa/start")
    public ResponseEntity<?> startMfa(HttpServletRequest request) {
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
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        session.setAttribute(MFA_AUTH_STATUS, MfaStatus.PENDING);
        MfaSessionRegistry.registerSession(username, session);
        String mfaUrl = buildMfaRedirectUrl(request, username);
        return ResponseEntity.ok(Map.of("mfaRequired", true, "mfaUrl", mfaUrl));
    }

    @GetMapping("/mfa/polling")
    public ResponseEntity<?> polling(HttpServletRequest request) {
        System.out.println(request.getSession(false).getId());
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

    @GetMapping("/mfa/manage")
    public ResponseEntity<?> mfaManage(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        String origin = request.getHeader("Origin");
        String token = mfaService.createAuthSession(username, "manatee-service", origin);
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token creation failed");
        }

        return ResponseEntity.ok(Map.of("token", token));
    }

    private String buildMfaRedirectUrl(HttpServletRequest request, String username) {
        String origin = request.getHeader("Origin");
        String token = mfaService.createAuthSession(username, "manatee-service", origin);
        return token != null ? String.format("%s/index.html?token=%s", mfaServerUrl, token) : null;
    }

    @GetMapping("/passwordless/status")
    public ResponseEntity<?> passwordlessStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);

        return ResponseEntity.ok(employee.getPasswordless());
    }

    @PostMapping("/passwordless/enroll")
    public ResponseEntity<?> enrollPasswordless() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);
        employee.setPasswordless(true);
        employeeService.update(employee);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/passwordless/disable")
    public ResponseEntity<?> disablePasswordless() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();
        Employee employee = employeeService.findByUsername(username);
        employee.setPasswordless(false);
        employeeService.update(employee);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }





    @GetMapping("/pc-passkey/enroll")
    public ResponseEntity<?> passkeyEnroll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();

        String tx = mfaService.enrollPasskey(username);
        String sessionToken = mfaService.createAuthSession(username, "manatee-service", null);

        return ResponseEntity.ok(Map.of(
                "txId", tx,
                "popup", mfaServerUrl + "/index.html",
                "sessionToken", sessionToken
        ));
    }

    @GetMapping("/pc-passkey/status")
    public ResponseEntity<?> passkeyStatus(@RequestParam String tx) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();

        boolean status = mfaService.checkPasskey(username, tx);

        return ResponseEntity.ok(status);
    }

    @GetMapping("/pc-passkey/disable")
    public ResponseEntity<?> disablePasskey() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication missing");
        }

        String username = auth.getName();

        boolean success = mfaService.disablePasskey(username);

        return ResponseEntity.ok(success);
    }
}
