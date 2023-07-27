package kr.codesquad.issuetracker.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import kr.codesquad.config.JwtConfig;
import kr.codesquad.issuetracker.infrastructure.security.hash.PasswordEncoder;
import kr.codesquad.issuetracker.infrastructure.security.hash.SHA256;

@Import(JwtConfig.class)
@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new SHA256();
	}
}
