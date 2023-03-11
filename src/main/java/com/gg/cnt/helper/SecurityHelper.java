package com.gg.cnt.helper;

import com.gg.cnt.errors.ObjectNotFoundException;
import com.gg.cnt.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class SecurityHelper {
    private SecurityHelper() {
    }

    public static Long getCurrentUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(authentication -> authentication.getPrincipal() instanceof CustomUserDetails)
                .map(authentication -> ((CustomUserDetails) authentication.getPrincipal()).getId())
                .orElseThrow(() -> new ObjectNotFoundException("currentUserId"));
    }

    public static String getCurrentUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(authentication -> authentication.getPrincipal() instanceof CustomUserDetails)
                .map(authentication -> ((CustomUserDetails) authentication.getPrincipal()).getUsername())
                .orElse(null);
    }
}
