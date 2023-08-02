package com.issuetrackermax.domain.jwt.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
	private String secretKey;
}

