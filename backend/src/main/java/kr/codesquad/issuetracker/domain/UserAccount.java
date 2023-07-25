package kr.codesquad.issuetracker.domain;

import lombok.Getter;

@Getter
public class UserAccount {

	private Integer id;
	private String loginId;
	private String password;
	private String profileUrl;
	private Boolean isDeleted;
}
