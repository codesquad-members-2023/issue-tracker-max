package com.issuetrackermax.config;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.issuetrackermax.domain.oauth.InMemoryProviderRepository;
import com.issuetrackermax.domain.oauth.entity.OauthProperties;
import com.issuetrackermax.domain.oauth.utils.OauthAdapter;
import com.issuetrackermax.service.oauth.OauthProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(OauthProperties.class)
public class OauthConfig {

	private final OauthProperties properties;

	@Bean
	public InMemoryProviderRepository inMemoryProviderRepository() {
		Map<String, OauthProvider> providers = OauthAdapter.getOauthProviders(properties);
		return new InMemoryProviderRepository(providers);
	}
}
