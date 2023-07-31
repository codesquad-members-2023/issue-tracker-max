package codesquard.app.user.entity;

public class User {

	private Long id;
	private final String userId;
	private final String email;
	private final String password;
	private final String avatarUrl;

	public User(String userId, String email, String password, String avatarUrl) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.avatarUrl = avatarUrl;
	}

	public String getUserId() {
		return userId;
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
