package codesquad.issueTracker.user.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
	@NotNull(message = "이메일을 입력해주세요")
	private String email;
	@NotNull(message = "패스워드를 입력해주세요")
	private String password;

}
