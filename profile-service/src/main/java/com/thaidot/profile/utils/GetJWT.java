package com.thaidot.profile.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class GetJWT {

    public Jwt getJWT() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (Jwt) principal;
    }

    public String getSubject() {
        return getJWT().getSubject().toString();
    }
}
