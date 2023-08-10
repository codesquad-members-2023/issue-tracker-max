package codesquard.app.oauth.config;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import codesquard.app.oauth.InMemoryProviderRepository;
import codesquard.app.oauth.OauthAdapter;
import codesquard.app.oauth.OauthProvider;
import codesquard.app.oauth.properties.OauthProperties;

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
