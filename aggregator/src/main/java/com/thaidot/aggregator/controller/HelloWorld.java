package com.thaidot.aggregator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class HelloWorld {



    @GetMapping("/hello")
    public Mono<String> hello() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> {
                    String name = context.getAuthentication().getName();
                    log.info("User name: {}", name);
                    return "Hello World!";
                });
    }
}
