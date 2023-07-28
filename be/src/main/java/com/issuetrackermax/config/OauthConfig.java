package com.issuetrackermax.config;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.issuetrackermax.domain.oAuth.InMemoryProviderRepository;
import com.issuetrackermax.domain.oAuth.entity.OauthProperties;
import com.issuetrackermax.domain.oAuth.service.OauthProvider;
import com.issuetrackermax.domain.oAuth.utils.OauthAdapter;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
public class OauthConfig {

	private final OauthProperties properties;

	public OauthConfig(OauthProperties properties) {
		this.properties = properties;
	}

	@Bean
	public InMemoryProviderRepository inMemoryProviderRepository() {
		Map<String, OauthProvider> providers = OauthAdapter.getOauthProviders(properties);
		return new InMemoryProviderRepository(providers);
	}
}
