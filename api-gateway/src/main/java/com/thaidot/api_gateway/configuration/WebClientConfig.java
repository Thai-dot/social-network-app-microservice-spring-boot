package com.thaidot.api_gateway.configuration;

import com.thaidot.api_gateway.client.IdentityServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Value("${identity.url}")
    private String identityUrl;

    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl(identityUrl).build();
    }

    @Bean
    IdentityServiceClient identityServiceClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();

        return httpServiceProxyFactory.createClient(IdentityServiceClient.class);
    }
}
