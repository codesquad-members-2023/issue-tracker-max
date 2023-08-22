package com.issuetracker.account.ui.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OauthLoginResponse {

	private boolean existMember;
	private OauthAccountInfoResponse accountInfo;
	private JwtTokenResponse jwtToken;
}
