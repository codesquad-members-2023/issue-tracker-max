package com.issuetrackermax.service.jwt;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.domain.jwt.entity.Jwt;
import com.issuetrackermax.domain.jwt.service.JwtProvider;
import com.issuetrackermax.service.member.MemberService;
import com.issuetrackermax.util.DatabaseCleaner;

@SpringBootTest
@ActiveProfiles("test")
class JwtServiceTest {

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
			.password("1234")
			.build();
		memberService.registerMember(signUpRequest);

		// when
		Jwt jwt = jwtService.login("June@codesquad.co.kr", "1234");
		String accessToken = jwt.getAccessToken();
		String refreshToken = jwt.getRefreshToken();

		// then
		assertThat(jwtProvider.getClaims(accessToken).get("memberId")).isEqualTo(1);
		assertThat(jwtProvider.getClaims(refreshToken).get("memberId")).isNull();

	}

	@DisplayName("패스워드가 일치하지 않으면 오류를 일으킨다.")
	@Test
	void login2() throws Exception {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("1234")
			.build();
		// when
		memberService.registerMember(signUpRequest);

		// then
		assertThatThrownBy(() -> jwtService.login("June@codesquad.co.kr", "123")).isInstanceOf(Exception.class);
	}

	@DisplayName("accessToken의 만료기간이 지나 refreshToken을 통해 accessToken을 재발행받는다.")
	@Test
	void reissueAccessToken() throws Exception {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("1234")
			.build();
		memberService.registerMember(signUpRequest);
		Jwt jwt = jwtService.login("June@codesquad.co.kr", "1234");
		// when
		Jwt newJwt = jwtService.reissueAccessToken(jwt.getRefreshToken());
		String accessToken = newJwt.getAccessToken();
		String refreshToken = newJwt.getRefreshToken();

		// then
		assertThat(jwtProvider.getClaims(accessToken).get("memberId")).isEqualTo(1);
		assertThat(jwtProvider.getClaims(refreshToken).get("memberId")).isNull();

	}
}
