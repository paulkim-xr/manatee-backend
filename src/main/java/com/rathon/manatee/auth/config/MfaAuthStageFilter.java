package com.rathon.manatee.auth.config;

import com.rathon.manatee.auth.controller.SessionController;
import com.rathon.manatee.auth.model.AuthStage;
import com.rathon.manatee.auth.model.MfaSessionRegistry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class MfaAuthStageFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MfaSessionRegistry.registerSession(auth.getName(), session);

            if (AuthStage.PASSWORD_VERIFIED.equals(session.getAttribute(SessionController.AUTH_STAGE)) &&
                    request.getRequestURI().startsWith("/api") &&
                    !request.getRequestURI().contentEquals("/api/auth/otp") &&
                    !request.getRequestURI().contentEquals("/api/auth/bio/status") &&
                    !request.getRequestURI().contentEquals("/api/auth/mfa/callback") &&
                    !request.getRequestURI().contentEquals("/api/auth/mfa/polling") &&
                    !request.getRequestURI().contentEquals("/api/auth/username") &&
                    !request.getRequestURI().contentEquals("/api/auth/password")
            ) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("MFA required");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
