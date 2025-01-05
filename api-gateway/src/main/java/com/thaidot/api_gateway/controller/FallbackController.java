package com.thaidot.api_gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/fallback/profile-service")
    public ResponseEntity<String> fallbackProfile() {
        return new ResponseEntity<>("Profile Service is temporarily unavailable. Please try again later.",
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/fallback/identity-service")
    public ResponseEntity<String> fallbackIdentity() {
        return new ResponseEntity<>("Identity service is temporarily unavailable. Please try again later.",
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/fallback/post-service")
    public ResponseEntity<String> fallbackPost() {
        return new ResponseEntity<>("Post service is temporarily unavailable. Please try again later.",
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/fallback/notification-service")
    public ResponseEntity<String> fallbackNotification() {
        return new ResponseEntity<>("Notification service is temporarily unavailable. Please try again later.",
                HttpStatus.SERVICE_UNAVAILABLE);
    }
}
