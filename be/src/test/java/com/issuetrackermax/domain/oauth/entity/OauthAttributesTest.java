package com.issuetrackermax.domain.oauth.entity;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.issuetrackermax.controller.auth.dto.response.MemberProfileResponse;

class OauthAttributesTest {

	@Test
	void extract() {
		// given
		Map<String, Object> attributes = Map.of("login", "June");

		// when
		MemberProfileResponse memberProfileResponse = OauthAttributes.extract("github", attributes);

		// then
		assertThat(memberProfileResponse.getLoginId()).isEqualTo("June");
	}
}