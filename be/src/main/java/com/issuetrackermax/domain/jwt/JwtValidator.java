package com.issuetrackermax.domain.jwt;

import org.springframework.stereotype.Component;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.LoginException;
import com.issuetrackermax.domain.member.entity.Member;

@Component
public class JwtValidator {

	public void verifyPassword(Member member, String password) {
		if (member == null || !password.equals(member.getPassword())) {
			throw new ApiException(LoginException.INCORRECT_LOGIN_INFORMATION);
		}
	}
}
