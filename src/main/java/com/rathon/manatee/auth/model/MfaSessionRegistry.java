package com.rathon.manatee.auth.model;

import com.rathon.manatee.auth.controller.SessionController;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MfaSessionRegistry {
    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public static void registerSession(String username, HttpSession session) {
        sessions.put(username, session);
    }

    public static void markStatus(String username, MfaStatus status) {
        HttpSession session = sessions.get(username);
        if (session != null) {
            session.setAttribute(SessionController.MFA_AUTH_STATUS, status);
        }
    }

    public static HttpSession getSession(String username) {
        return sessions.get(username);
    }
}
