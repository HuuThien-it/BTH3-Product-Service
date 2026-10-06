package com.example.bth3product.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class AuthClient {

    private final RestClient restClient;

    public AuthClient(@Value("${auth.service.base-url}") String authServiceBaseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(authServiceBaseUrl)
                .build();
    }

    public boolean isTokenValid(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return false;
        }

        try {
            ResponseEntity<String> response = restClient.get()
                    .uri("/auth")
                    .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                    .retrieve()
                    .toEntity(String.class);

            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException ex) {
            return false;
        }
    }
}
