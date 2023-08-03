package org.presents.issuetracker.user.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private Long userId;
	private String loginId;
	private String password;
	private String image;
}
