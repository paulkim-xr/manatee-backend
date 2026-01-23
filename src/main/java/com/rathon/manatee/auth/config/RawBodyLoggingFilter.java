package com.rathon.manatee.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RawBodyLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        ContentCachingRequestWrapper wrapped = new ContentCachingRequestWrapper(request);
        filterChain.doFilter(wrapped, response);

        if (request.getRequestURI().contains("/api/auth/mfa/callback")) {
            byte[] body = wrapped.getContentAsByteArray();
            String raw = new String(body, StandardCharsets.UTF_8);
            System.out.println("RAW_BODY=" + raw);
        }
    }
}
