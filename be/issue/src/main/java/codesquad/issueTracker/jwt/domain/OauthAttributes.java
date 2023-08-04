package codesquad.issueTracker.jwt.domain;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import java.util.Arrays;
import java.util.Map;

public enum OauthAttributes {
    GITHUB("github") {
        @Override
        public UserProfile of(Map<String, Object> attributes) {
            return UserProfile.builder()
                    .email((String) attributes.get("email"))
                    .name((String) attributes.get("login"))
                    .imageUrl((String) attributes.get("avatar_url"))
                    .build();
        }
    };

    private final String providerName;

    OauthAttributes(String name) {
        this.providerName = name;
    }

    public static UserProfile extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_SUPPORTED_PROVIDER))
                .of(attributes);
    }

    public abstract UserProfile of(Map<String, Object> attributes);
}
