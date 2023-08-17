package codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.OauthProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InMemoryProviderRepository {
    private final Map<String, OauthProvider> providers;

    public OauthProvider findByProviderName(String name) {
        return providers.get(name);
    }
}
