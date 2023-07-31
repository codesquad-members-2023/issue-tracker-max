package codesquard.app.user.entity;

public class User {

	private Long id;
	private String loginId;
	private String email;
	private String password;
	private String avatarUrl;

	public User(String loginId, String email, String password, String avatarUrl) {
		this.loginId = loginId;
		this.email = email;
		this.password = password;
		this.avatarUrl = avatarUrl;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

}
