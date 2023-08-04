package codesquad.issueTracker.global.config;

import codesquad.issueTracker.jwt.domain.OAuthProperties;
import codesquad.issueTracker.jwt.util.InMemoryProviderRepository;
import codesquad.issueTracker.jwt.util.OauthAdapter;
import codesquad.issueTracker.jwt.util.OauthProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
