package codesquad.issueTracker.user.domain;

import lombok.Builder;
import lombok.Getter;

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

}
