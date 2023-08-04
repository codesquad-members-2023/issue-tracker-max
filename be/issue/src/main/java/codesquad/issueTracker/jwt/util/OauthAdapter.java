package codesquad.issueTracker.jwt.util;

import codesquad.issueTracker.jwt.domain.OAuthProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OauthAdapter {

    public static Map<String, OauthProvider> getOauthProviders(OAuthProperties properties) {
        Map<String, OauthProvider> oauthProvider = new HashMap<>();

        properties.getClient().forEach(
                (key, value) -> oauthProvider.put(
                        key, new OauthProvider(value, properties.getProvider().get(key))));
        return oauthProvider;
    }
}
