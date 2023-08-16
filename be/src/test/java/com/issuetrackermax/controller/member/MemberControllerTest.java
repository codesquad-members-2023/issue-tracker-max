package com.issuetrackermax.controller.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.issuetrackermax.controller.ControllerTestSupport;
import com.issuetrackermax.controller.member.dto.request.SignUpRequest;

class MemberControllerTest extends ControllerTestSupport {

	@DisplayName("이메일, 패스워드, 닉네임을 입력하여 회원가입을 성공한다.")
	@Test
	void signup() throws Exception {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("12345678")
			.build();
		// when&then
		mockMvc.perform(
				post("/api/signup")
					.content(objectMapper.writeValueAsString(signUpRequest))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"));
	}
}
