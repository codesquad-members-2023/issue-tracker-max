package codesquad.issueTracker.global.config;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import codesquad.issueTracker.jwt.util.InMemoryProviderRepository;
import codesquad.issueTracker.oauth.domain.OAuthProperties;
import codesquad.issueTracker.oauth.util.OauthAdapter;
import codesquad.issueTracker.oauth.util.OauthProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(OAuthProperties.class)
@RequiredArgsConstructor
public class OauthConfig {

	private final OAuthProperties properties;

	@Bean
	public InMemoryProviderRepository inMemoryProviderRepository() {
		Map<String, OauthProvider> providers = OauthAdapter.getOauthProviders(properties);
		return new InMemoryProviderRepository(providers);
	}
}
