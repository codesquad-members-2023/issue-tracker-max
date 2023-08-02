package com.issuetrackermax.controller.auth.dto.response;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;

class MemberProfileResponseTest {

	@DisplayName("MemberProfileResponse에서 loginType이 Github인 Member를 만들어준다.")
	@Test
	void memberProfileResponseToMember() {
		// given
		MemberProfileResponse memberProfileResponse = MemberProfileResponse.builder().loginId("June").build();
		// when
		Member member = memberProfileResponse.toMember();
		// then
		assertThat(member.getLoginType()).isEqualTo(LoginType.GITHUB);
		assertThat(member.getLoginId()).isEqualTo("June");

	}

}