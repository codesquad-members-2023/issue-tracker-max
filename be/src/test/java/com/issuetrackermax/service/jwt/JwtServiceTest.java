package com.issuetrackermax.service.jwt;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.common.exception.InCorrectPasswordException;
import com.issuetrackermax.common.exception.InvalidLoginIdException;
import com.issuetrackermax.common.exception.InvalidPasswordException;
import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.jwt.entity.Jwt;
import com.issuetrackermax.service.member.MemberService;
import com.issuetrackermax.util.DatabaseCleaner;

class JwtServiceTest extends IntegrationTestSupport {

	@Autowired
	JwtService jwtService;

	@Autowired
	MemberService memberService;

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
	void login() throws Exception {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();
		memberService.registerMember(signUpRequest);

		// when
		Jwt jwt = jwtService.login("June@codesquad.co.kr", "12345678");
		String accessToken = jwt.getAccessToken();
		String refreshToken = jwt.getRefreshToken();

		// then
		assertThat(jwtProvider.getClaims(accessToken).get("memberId")).isEqualTo(1);
		assertThat(jwtProvider.getClaims(refreshToken).get("memberId")).isNull();

	}

	@DisplayName("패스워드가 일치하지 않으면 오류를 일으킨다.")
	@Test
	void incoreectPasswordException() throws Exception {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();
		// when
		memberService.registerMember(signUpRequest);

		// then
		assertThatThrownBy(() -> jwtService.login("June@codesquad.co.kr", "12345679")).isInstanceOf(
			InCorrectPasswordException.class);
	}

	@DisplayName("패스워드가 8글자 이하면 오류를 일으킨다.")
	@Test
	void invalidPasswordException() throws Exception {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("1234")
			.build();
		// when & then
		assertThatThrownBy(() -> memberService.registerMember(signUpRequest)).isInstanceOf(
			InvalidPasswordException.class);

	}

	@DisplayName("이미 같은 아이디가 존재하면 오류를 일으킨다.")
	@Test
	void invalidLoginException() throws Exception {
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
		assertThatThrownBy(() -> memberService.registerMember(invalidSignUpRequest)).isInstanceOf(
			InvalidLoginIdException.class);
	}

	@DisplayName("accessToken의 만료기간이 지나 refreshToken을 통해 accessToken을 재발행받는다.")
	@Test
	void reissueAccessToken() throws Exception {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();
		memberService.registerMember(signUpRequest);
		Jwt jwt = jwtService.login("June@codesquad.co.kr", "12345678");
		// when
		Jwt newJwt = jwtService.reissueAccessToken(jwt.getRefreshToken());
		String accessToken = newJwt.getAccessToken();
		String refreshToken = newJwt.getRefreshToken();

		// then
		assertThat(jwtProvider.getClaims(accessToken).get("memberId")).isEqualTo(1);
		assertThat(jwtProvider.getClaims(refreshToken).get("memberId")).isNull();

	}
}
