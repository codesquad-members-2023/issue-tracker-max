package com.issuetracker.account.application;

import java.util.Map;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.account.application.dto.AccountInformation;
import com.issuetracker.account.application.dto.AccountInputData;
import com.issuetracker.account.application.dto.JwtTokenInformation;
import com.issuetracker.account.application.dto.LoginInputData;
import com.issuetracker.account.application.dto.OauthAccountInputData;
import com.issuetracker.account.application.dto.SignUpInputData;
import com.issuetracker.account.domain.Account;
import com.issuetracker.account.domain.AccountRepository;
import com.issuetracker.account.domain.JwtRefreshToken;
import com.issuetracker.account.infrastructure.JwtTokenGenerator;
import com.issuetracker.account.infrastructure.MemoryJwtRepository;
import com.issuetracker.common.config.exception.CustomHttpException;
import com.issuetracker.common.config.exception.ErrorType;
import com.issuetracker.file.application.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

	private String DEFAULT_PROFILE_IMAGE_URL = "https://issue-mandu.s3.ap-northeast-2.amazonaws.com/images/user.png";

	private final AccountRepository accountRepository;
	private final MemoryJwtRepository memoryJwtRepository;
	private final JwtTokenGenerator jwtTokenGenerator;
	private final FileService fileService;

	@Transactional
	public JwtTokenInformation proceedToOauthLogin(OauthAccountInputData oauthAccountInputData) {

		Account account = accountRepository.findByEmail(oauthAccountInputData.getEmail());

		// [1] 이메일이 존재하지 않는 경우
		if (!account.verify()) {
			// 회원 가입
			Long memberId = signUp(SignUpInputData.from(oauthAccountInputData.toAccount()));
			mappingGitMember(memberId, oauthAccountInputData.getOauthId());

			// 토큰 발급
			return issueJwtTokenByEmail(oauthAccountInputData.getEmail());
		}

		// [2] 이메일이 존재하는 경우 1 - 이미 OAuth로 연동된 경우
		if (account.isOauthMember()) {
			// 토큰 발급
			return issueJwtTokenByMemberId(account.getId());
		}

		// [3] 이메일이 존재하는 경우 2 - OAuth 연동이 안된 경우
		// 계정 통합
		mappingGitMember(account.getId(), oauthAccountInputData.getOauthId());

		// 토큰 발급
		return issueJwtTokenByMemberId(account.getId());
	}

	@Transactional
	public JwtTokenInformation issueJwtToken(LoginInputData loginInputData) {
		return issueJwtToken(findForLogin(loginInputData).toAccountInputData());
	}

	@Transactional
	public JwtTokenInformation issueJwtTokenByEmail(String email) {
		return issueJwtToken(findByEmail(email).toAccountInputData());
	}

	@Transactional
	public JwtTokenInformation issueJwtTokenByMemberId(Long memberId) {
		return issueJwtToken(findByMemberId(memberId).toAccountInputData());
	}

	@Transactional
	public JwtTokenInformation issueJwtToken(AccountInputData accountInputData) {

		Map<String, Object> claims = Map.of(
			"memberId", accountInputData.getId(),
			"email", accountInputData.getEmail(),
			"nickname", accountInputData.getNickname(),
			"profileImageUri", Optional.ofNullable(accountInputData.getProfileImageUrl()).orElse("")
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
		return issueJwtTokenByMemberId(memberId);
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
		if (accountRepository.existByNickname(signUpInputData.getNickname())) {
			throw new CustomHttpException(ErrorType.ACCOUNT_NICKNAME_DUPLICATION);
		}
		MultipartFile multipartFile = signUpInputData.getMultipartFile();
		if (multipartFile != null && multipartFile.getSize() != 0) {
			String profileUrl = fileService.upload(multipartFile).getUrl();
			signUpInputData.setProfileImageUrl(profileUrl);
		} else {
			signUpInputData.setProfileImageUrl(DEFAULT_PROFILE_IMAGE_URL);
		}
		return accountRepository.save(signUpInputData.toAccount());
	}

	@Transactional
	public void mappingGitMember(Long memberId, Long oauthId) {
		accountRepository.saveGitMember(memberId, oauthId);
	}

	public void logout(Long memberId) {
		memoryJwtRepository.removeRefreshToken(memberId);
	}

	@Scheduled(initialDelay = 1000 * 60 * 60, fixedDelay = 1000 * 60 * 60)
	public void removeAllExpiredRefreshTokens() {
		memoryJwtRepository.removeAllExpiredRefreshTokens();
	}
}
