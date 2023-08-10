package codesquard.app.user.controller;

import static codesquard.app.api.response.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.authenticate_user.controller.response.AuthenticateUserLoginResponse;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.service.AuthenticateUserService;
import codesquard.app.authenticate_user.service.response.AuthenticateUserLoginServiceResponse;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.user.annotation.Login;

@RequestMapping(path = "/api")
@RestController
public class UserLoginRestController {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginRestController.class);

	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;
	private final AuthenticateUserService authenticateUserService;

	public UserLoginRestController(JwtProvider jwtProvider, ObjectMapper objectMapper,
		AuthenticateUserService authenticateUserService) {
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
		this.authenticateUserService = authenticateUserService;
	}

	@PostMapping("/login")
	public ApiResponse<AuthenticateUserLoginResponse> login(HttpServletRequest request,
		@Login AuthenticateUser user) throws
		IOException {
		AuthenticateUserLoginServiceResponse loginServiceResponse = authenticateUserService.login(user);
		request.setAttribute("loginServiceResponse", loginServiceResponse);

		return ApiResponse.of(OK, USER_LOGIN_SUCCESS, loginServiceResponse.toAuthenticateUserLoginResponse());
	}
}
