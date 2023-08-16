package codesquad.issueTracker.user.domain;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.user.dto.LoginRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@NoArgsConstructor
@Getter
public class User {

    private Long id;
    private String email;
    private String password;
    private String profileImg;
    private String name;
    private LoginType loginType;


    @Builder
    public User(Long id, String email, String password, String profileImg, String name,
                LoginType loginType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.name = name;
        this.loginType = loginType;
    }


    public void validateLoginUser(LoginRequestDto loginRequestDto) {
        if (password == null || !loginType.equals(LoginType.LOCAL)) {
            throw new CustomException(ErrorCode.GITHUB_LOGIN_USER);
        }
        if (!loginRequestDto.getEmail().equals(email)
                || !BCrypt.checkpw(loginRequestDto.getPassword(), password)) {
            throw new CustomException(ErrorCode.FAILED_LOGIN_USER);
        }
    }

}
