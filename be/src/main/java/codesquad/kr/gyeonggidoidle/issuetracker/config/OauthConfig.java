package codesquad.kr.gyeonggidoidle.issuetracker.config;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.OauthAdapter;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.OauthProperties;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.OauthProvider;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.repository.InMemoryProviderRepository;
import java.util.Map;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
