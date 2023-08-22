package com.issuetracker.account.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OauthEmailResponse {

	private String email;
	private boolean primary;
	private boolean verified;
	private String visibility;
}
