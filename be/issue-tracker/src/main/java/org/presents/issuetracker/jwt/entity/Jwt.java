package org.presents.issuetracker.jwt.entity;

import lombok.Getter;

@Getter
public class Jwt {
	private String refreshToken;
	private String loginId;
}
