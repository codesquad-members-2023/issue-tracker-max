package codesquad.issueTracker.jwt.domain;

import codesquad.issueTracker.user.domain.LoginType;
import codesquad.issueTracker.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfile {
    private final String email;
    private final String name;
    private final String imageUrl;


    @Builder
    public UserProfile(String email, String name, String imageUrl) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public User toUser(String providerName) {
        return User.builder()
                .email(email)
                .name(name)
                .profileImg(imageUrl)
                .loginType(LoginType.findByTypeString(providerName))
                .build();
    }
}
