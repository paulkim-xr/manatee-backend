package com.rathon.manatee.auth.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(1)
public class RateLimitFilter implements Filter {

    private static final int MAX_ATTEMPTS = 10;
    private static final long TIME_WINDOW_MS = 60 * 1000; // 1 minute

    private static final Map<String, AttemptTracker> attempts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String ip = req.getRemoteAddr();
        String path = req.getRequestURI();

        if ("/api/auth/login".equals(path) || "/api/auth/otp".equals(path)) {
            AttemptTracker tracker = attempts.computeIfAbsent(ip, k -> new AttemptTracker());

            synchronized (tracker) {
                long now = Instant.now().toEpochMilli();
                if (now - tracker.timestamp > TIME_WINDOW_MS) {
                    tracker.timestamp = now;
                    tracker.count = 0;
                }

                tracker.count++;
                if (tracker.count > MAX_ATTEMPTS) {
                    res.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                    res.getWriter().write("Too many requests, please try again later.");
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    private static class AttemptTracker {
        int count = 0;
        long timestamp = Instant.now().toEpochMilli();
    }
}