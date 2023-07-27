package kr.codesquad.issuetracker.infrastructure.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConfigurationProperties("jwt")
public class JwtProperties {

	private final String secretKey;
	private final long expirationMilliseconds;

	@ConstructorBinding
	public JwtProperties(String secretKey, long expirationMilliseconds) {
		this.secretKey = secretKey;
		this.expirationMilliseconds = expirationMilliseconds;
	}
}
