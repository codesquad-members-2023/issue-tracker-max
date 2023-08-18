package org.presents.issuetracker.jwt.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class ReissueAccessTokenRequest {
	@NotEmpty
	private String refreshToken;
}
