package kr.codesquad.issuetracker.domain;

import lombok.Getter;

@Getter
public class UserAccount {

	private Integer id;
	private String loginId;
	private String password;
	private String profileUrl;
	private Boolean isDeleted;

	public UserAccount(Integer id, String loginId, String password) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
	}

	public UserAccount(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
		this.profileUrl = "defaultImageUrl";
	}

	public boolean isSamePassword(String password) {
		return this.password.equals(password);
	}
}
