package com.thaidot.aggregator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class HelloWorld {

    @Autowired
    private Environment environment;


    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just(environment.getProperty("local.server.port"));
    }
}
