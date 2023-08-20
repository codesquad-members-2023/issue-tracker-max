package codesquad.issueTracker.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String userName;
    private String profileImgUrl;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken, Long userId, String userName, String profileImgUrl) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
    }
}

