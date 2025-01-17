package com.thaidot.identity.configuration;

import com.thaidot.identity.client.ProfileClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
@Slf4j
public class ProfileClientConfig {

    @Value("${profile.url}")
    private String profileServiceUrl;

    @Bean
    public ProfileClient profileClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(profileServiceUrl)
                .requestFactory(getClientRequestFactory())
                .requestInterceptor(getClientInterceptor())
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(ProfileClient.class);
    }

    private ClientHttpRequestFactory getClientRequestFactory() {
        ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(3))
                .withReadTimeout(Duration.ofSeconds(3));
        return ClientHttpRequestFactories.get(clientHttpRequestFactorySettings);
    }

    private ClientHttpRequestInterceptor getClientInterceptor() {
        return (HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            try {
                ServletRequestAttributes servletRequest = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                String token = servletRequest.getRequest().getHeader("Authorization"); // Fetch the token from IdentityService
                if (token != null && !token.isEmpty()) {
                    request.getHeaders().add(HttpHeaders.AUTHORIZATION, token);
                }
                return execution.execute(request, body);
            } catch (HttpClientErrorException e) {
                // Log the response body for failed requests
                log.error("Request failed with status: {} and body: {}", e.getStatusCode(), e.getResponseBodyAsString());
                throw e;
            }
        };
    }
}
