package com.issuetrackermax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuetrackermax.controller.auth.AuthController;
import com.issuetrackermax.controller.filter.FilterController;
import com.issuetrackermax.controller.member.MemberController;
import com.issuetrackermax.domain.filter.FilterMapper;
import com.issuetrackermax.service.filter.FilterService;
import com.issuetrackermax.service.jwt.JwtService;
import com.issuetrackermax.service.member.MemberService;

@WebMvcTest(controllers = {
	AuthController.class,
	MemberController.class,
	FilterController.class
})
public abstract class ControllerTestSupport {
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected MemberService memberService;

	@MockBean
	protected JwtService jwtService;

	@MockBean
	protected FilterService filterService;

	@MockBean
	protected FilterMapper filterMapper;
}
