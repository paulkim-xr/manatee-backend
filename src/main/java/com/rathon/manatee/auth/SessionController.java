package com.rathon.manatee.auth;

import com.rathon.manatee.database.service.EmployeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
//@RequestMapping("/api/auth")
public class SessionController {
    private final EmployeeService service;

    public SessionController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<?> check(Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(Map.of("user", auth.getName(), "id", service.findByUsername(auth.getName()).getId(), "roles", auth.getAuthorities()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request,
                                        @RequestBody LoginDto authRequest) {
        try {
            request.login(authRequest.username(), authRequest.password());
            return ResponseEntity.ok(Map.of("user", authRequest.username(),
                    "id", service.findByUsername(authRequest.username()).getId(),
                    "roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities())
            );
        } catch (ServletException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }
}
