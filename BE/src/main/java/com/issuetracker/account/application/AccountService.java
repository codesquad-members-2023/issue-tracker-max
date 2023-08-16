package com.issuetracker.account.application;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.account.application.dto.AccountInformation;
import com.issuetracker.account.application.dto.AccountInputData;
import com.issuetracker.account.application.dto.JwtTokenInformation;
import com.issuetracker.account.application.dto.LoginInputData;
import com.issuetracker.account.application.dto.SignUpInputData;
import com.issuetracker.account.domain.AccountRepository;
import com.issuetracker.account.domain.JwtRefreshToken;
import com.issuetracker.account.infrastructure.JwtTokenGenerator;
import com.issuetracker.account.infrastructure.MemoryJwtRepository;
import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.config.exception.ErrorType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

	private final AccountRepository accountRepository;
	private final MemoryJwtRepository memoryJwtRepository;
	private final JwtTokenGenerator jwtTokenGenerator;

	@Transactional
	public JwtTokenInformation issueJwtToken(LoginInputData loginInputData) {
		return issueJwtToken(findForLogin(loginInputData).toAccountInputData());
	}

	@Transactional
	public JwtTokenInformation issueJwtToken(String email) {
		return issueJwtToken(findByEmail(email).toAccountInputData());
	}

	@Transactional
	public JwtTokenInformation issueJwtToken(Long memberId) {
		return issueJwtToken(findByMemberId(memberId).toAccountInputData());
	}

	@Transactional
	public JwtTokenInformation issueJwtToken(AccountInputData accountInputData) {

		Map<String, Object> claims = Map.of(
			"memberId", accountInputData.getId(),
			"email", accountInputData.getEmail(),
			"nickname", accountInputData.getNickname(),
			"profileImageUri", accountInputData.getProfileImageUrl()
		);
		JwtTokenInformation jwtTokenInformation = JwtTokenInformation.from(jwtTokenGenerator.createJwt(claims));
		storeRefreshToken(
			jwtTokenGenerator.convertFrom(
				accountInputData.getId(),
				jwtTokenInformation.getRefreshToken()
			)
		);

		return jwtTokenInformation;
	}

	@Transactional
	public JwtTokenInformation reissueJwtToken(String refreshToken) {
		Long memberId = memoryJwtRepository.getMemberId(refreshToken);
		return issueJwtToken(memberId);
	}

	private void storeRefreshToken(JwtRefreshToken jwtRefreshToken) {
		memoryJwtRepository.save(jwtRefreshToken);
	}

	public AccountInformation findByEmail(String email) {
		return AccountInformation.from(accountRepository.findByEmail(email));
	}

	public AccountInformation findForLogin(LoginInputData loginInputData) {
		return AccountInformation.from(
			accountRepository.findByEmailAndPassword(loginInputData.toAccount())
		);
	}

	public AccountInformation findByMemberId(Long memberId) {
		return AccountInformation.from(accountRepository.findByMemberId(memberId));
	}

	@Transactional
	public Long signUp(SignUpInputData signUpInputData) {
		if (accountRepository.existByEmail(signUpInputData.getEmail())) {
			throw new CustomHttpException(ErrorType.ACCOUNT_EMAIL_DUPLICATION);
		}
		return accountRepository.save(signUpInputData.toAccount());
	}
}
