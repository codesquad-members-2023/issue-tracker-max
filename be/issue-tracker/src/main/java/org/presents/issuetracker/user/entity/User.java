package org.presents.issuetracker.user.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
	private Long userId;
	private String loginId;
	private String password;
	private String image;
}
