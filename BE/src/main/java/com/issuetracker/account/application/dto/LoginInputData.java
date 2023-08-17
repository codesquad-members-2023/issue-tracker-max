package com.issuetracker.account.application.dto;

import com.issuetracker.account.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginInputData {

	private String email;
	private String password;

	public Account toAccount() {
		return Account.builder()
			.email(email)
			.password(password)
			.build();
	}
}
