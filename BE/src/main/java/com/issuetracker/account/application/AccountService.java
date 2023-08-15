package com.issuetracker.account.application;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.account.application.dto.AccountInformation;
import com.issuetracker.account.application.dto.JwtTokenInformation;
import com.issuetracker.account.domain.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

	private final AccountRepository accountRepository;
	private final JwtTokenGenerator jwtTokenGenerator;

	public JwtTokenInformation issueJwtToken(long id) {
		Map<String, Object> claims = Map.of("id", id);
		return JwtTokenInformation.from(jwtTokenGenerator.createJwt(claims));
	}

	public AccountInformation findByEmail(String email) {
		return AccountInformation.from(accountRepository.findByEmail(email));
	}
}
