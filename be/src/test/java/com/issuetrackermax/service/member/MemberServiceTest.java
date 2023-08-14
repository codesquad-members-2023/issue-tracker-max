package com.issuetrackermax.service.member;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.entity.Member;
import com.issuetrackermax.util.DatabaseCleaner;

class MemberServiceTest extends IntegrationTestSupport {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseCleaner databaseCleaner;

	@AfterEach
	void setUp() {
		databaseCleaner.execute();
	}

	@Test
	void registerMember() {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();

		// when
		memberService.registerMember(signUpRequest);
		// then
		Member member = memberRepository.findByMemberLoginId("June@codesquad.co.kr");
		assertThat(member.getLoginId()).isEqualTo("June@codesquad.co.kr");
	}
}
