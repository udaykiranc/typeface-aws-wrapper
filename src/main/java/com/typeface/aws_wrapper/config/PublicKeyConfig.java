package com.typeface.aws_wrapper.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.client.RestTemplate;

import java.security.interfaces.RSAPublicKey;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PublicKeyConfig {

    private final IdentityServiceProperties identityServiceProperties;

    /**
     * Fetches the JWKS from the identity service on startup and extracts the first
     * RSA public key, storing it in memory as a Spring bean.
     *
     * Expected response: standard JWKS JSON  { "keys": [ { "kty":"RSA", "n":"...", "e":"..." } ] }
     */
    @Bean
    public RSAPublicKey identityServicePublicKey() {
        String uri = identityServiceProperties.getPublicKeyUri();
        log.info("Fetching JWKS from identity service: {}", uri);

        RestTemplate restTemplate = new RestTemplate();
        String jwksJson = restTemplate.getForObject(uri, String.class);

        if (jwksJson == null || jwksJson.isBlank()) {
            throw new IllegalStateException("Identity service returned empty JWKS from: " + uri);
        }

        try {
            JWKSet jwkSet = JWKSet.parse(jwksJson);
            List<RSAKey> rsaKeys = jwkSet.getKeys().stream()
                    .filter(k -> k instanceof RSAKey)
                    .map(k -> (RSAKey) k)
                    .toList();

            if (rsaKeys.isEmpty()) {
                throw new IllegalStateException("No RSA keys found in JWKS from: " + uri);
            }

            RSAPublicKey publicKey = rsaKeys.get(0).toRSAPublicKey();
            log.info("Public key loaded successfully from identity service (kid={}, keySize={})",
                    rsaKeys.get(0).getKeyID(), publicKey.getModulus().bitLength());
            return publicKey;

        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse JWKS from identity service: " + uri, e);
        }
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey identityServicePublicKey) {
        return NimbusJwtDecoder.withPublicKey(identityServicePublicKey).build();
    }
}
