package kr.codesquad.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import kr.codesquad.issuetracker.infrastructure.config.jwt.Jwt;
import kr.codesquad.issuetracker.infrastructure.config.jwt.JwtProperties;

@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

	private final JwtProperties properties;

	public JwtConfig(JwtProperties properties) {
		this.properties = properties;
	}

	@Bean
	public Jwt jwtProperties() {
		return new Jwt(properties.getSecretKey(), properties.getExpirationMilliseconds());
	}
}
