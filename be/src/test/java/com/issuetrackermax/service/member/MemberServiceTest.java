package com.issuetrackermax.service.member;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.domain.member.Entity.Member;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.util.DatabaseCleaner;

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {

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
			.password("1234")
			.build();

		// when
		memberService.registerMember(signUpRequest);
		// then
		Optional<Member> member = memberRepository.findByMemberLoginId("June@codesquad.co.kr");
		assertThat(member.get().getLoginId()).isEqualTo("June@codesquad.co.kr");
	}
}
