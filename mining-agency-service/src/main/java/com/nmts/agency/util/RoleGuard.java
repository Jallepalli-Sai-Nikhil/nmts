package com.nmts.agency.util;

import com.nmts.agency.exception.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class RoleGuard {

    public void requireRole(HttpServletRequest req, String role) {
        String userRole = req.getHeader("X-User-Role");
        if (userRole == null || !userRole.equals(role)) {
            throw new AccessDeniedException("Access denied. Required role: " + role);
        }
    }

    public void requireAnyRole(HttpServletRequest req, String... roles) {
        String userRole = req.getHeader("X-User-Role");
        if (userRole == null) {
            throw new AccessDeniedException("Access denied. No role provided.");
        }
        for (String role : roles) {
            if (userRole.equals(role)) {
                return;
            }
        }
        throw new AccessDeniedException("Access denied. Required one of roles: " + String.join(", ", roles));
    }

    public UUID extractUserId(HttpServletRequest req) {
        String userIdStr = req.getHeader("X-User-Id");
        if (userIdStr == null) {
            throw new RuntimeException("X-User-Id header is missing");
        }
        try {
            return UUID.fromString(userIdStr);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid X-User-Id header format");
        }
    }
}
