package com.issuetrackermax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuetrackermax.controller.auth.AuthController;
import com.issuetrackermax.controller.filter.FilterController;
import com.issuetrackermax.controller.label.LabelController;
import com.issuetrackermax.controller.member.MemberController;
import com.issuetrackermax.service.filter.FilterService;
import com.issuetrackermax.service.jwt.JwtService;
import com.issuetrackermax.service.label.LabelService;
import com.issuetrackermax.service.member.MemberService;
import com.issuetrackermax.service.milestone.MilestoneService;

@WebMvcTest(controllers = {
	AuthController.class,
	MemberController.class,
	FilterController.class,
	LabelController.class
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
	protected LabelService labelService;

	@MockBean
	protected MilestoneService milestoneService;

}
