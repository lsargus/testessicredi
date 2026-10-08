package br.com.lsargus.testesicredi.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties (
    String secret,
    Long expiration
){}
