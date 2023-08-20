package com.issuetrackermax.service.jwt;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.LoginException;
import com.issuetrackermax.controller.auth.dto.response.LoginResponse;
import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.jwt.JwtRepository;
import com.issuetrackermax.domain.jwt.entity.Jwt;
import com.issuetrackermax.service.member.MemberService;
import com.issuetrackermax.util.DatabaseCleaner;

class JwtServiceTest extends IntegrationTestSupport {

	@Autowired
	JwtService jwtService;

	@Autowired
	MemberService memberService;

	@Autowired
	JwtRepository jwtRepository;

	@Autowired
	DatabaseCleaner databaseCleaner;

	@Autowired
	JwtProvider jwtProvider;

	@AfterEach
	void setUp() {
		databaseCleaner.execute();
	}

	@DisplayName("JwtToken을 가지고 로그인에 성공한다.")
	@Test
	void login() {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();
		memberService.registerMember(signUpRequest);

		// when
		LoginResponse loginResponse = jwtService.login("June@codesquad.co.kr", "12345678");
		String accessToken = loginResponse.getAccessToken();
		String refreshToken = loginResponse.getRefreshToken();

		// then
		assertThat(jwtProvider.getClaims(accessToken).get("memberId")).isEqualTo(1);
		assertThat(jwtProvider.getClaims(refreshToken).get("memberId")).isNull();

	}

	@DisplayName("패스워드가 일치하지 않으면 오류를 일으킨다.")
	@Test
	void incorrectPasswordException() {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();
		// when
		memberService.registerMember(signUpRequest);

		// then
		assertThatThrownBy(() -> jwtService.login("June@codesquad.co.kr", "12345679"))
			.isInstanceOf(ApiException.class)
			.satisfies(exception -> {
				ApiException apiException = (ApiException)exception;
				assertThat(apiException.getCustomException()).isInstanceOf(LoginException.class);
			});
	}

	@DisplayName("이미 같은 아이디가 존재하면 오류를 일으킨다.")
	@Test
	void invalidLoginException() {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();
		memberService.registerMember(signUpRequest);
		SignUpRequest invalidSignUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();

		// when & then
		assertThatThrownBy(() -> memberService.registerMember(invalidSignUpRequest))
			.isInstanceOf(ApiException.class)
			.satisfies(exception -> {
				ApiException apiException = (ApiException)exception;
				assertThat(apiException.getCustomException()).isInstanceOf(LoginException.class);
			});
	}

	@DisplayName("accessToken의 만료기간이 지나 refreshToken을 통해 accessToken을 재발행받는다.")
	@Test
	void reissueAccessToken() {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();
		memberService.registerMember(signUpRequest);
		LoginResponse loginResponse = jwtService.login("June@codesquad.co.kr", "12345678");
		// when
		Jwt newJwt = jwtService.reissueAccessToken(loginResponse.getRefreshToken());
		String accessToken = newJwt.getAccessToken();
		String refreshToken = newJwt.getRefreshToken();

		// then
		assertThat(jwtProvider.getClaims(accessToken).get("memberId")).isEqualTo(1);
		assertThat(jwtProvider.getClaims(refreshToken).get("memberId")).isNull();

	}

	@DisplayName("logout을 하면 저장된 refreshToken이 삭제된다.")
	@Test
	void logout() {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();
		memberService.registerMember(signUpRequest);
		LoginResponse loginResponse = jwtService.login("June@codesquad.co.kr", "12345678");
		String refreshToken = loginResponse.getRefreshToken();
		// when
		jwtService.logout(refreshToken);

		// then
		Boolean exist = jwtRepository.existsRefreshToken(refreshToken);
		assertThat(exist).isFalse();

	}
}
