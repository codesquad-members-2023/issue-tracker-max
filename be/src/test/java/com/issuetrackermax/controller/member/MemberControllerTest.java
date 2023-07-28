package com.issuetrackermax.controller.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.service.member.MemberService;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MemberService memberService;

	@DisplayName("이메일, 페스워드, 닉네임을 입력하여 회원가입을 성공한다.")
	@Test
	void signup() throws Exception {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.loginId("June@codesquad.co.kr")
			.nickName("June")
			.password("1234")
			.build();
		// when&then
		mockMvc.perform(
				post("/signup")
					.content(objectMapper.writeValueAsString(signUpRequest))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"));

	}
}