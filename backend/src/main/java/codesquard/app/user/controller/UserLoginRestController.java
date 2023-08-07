package codesquard.app.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.errors.errorcode.LoginErrorCode;
import codesquard.app.errors.exception.RestApiException;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.jwt.filter.VerifyUserFilter;
import codesquard.app.user.controller.response.UserLoginResponse;
import codesquard.app.user.service.AuthenticateUserService;

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
	public ResponseEntity<UserLoginResponse> login(HttpServletRequest request, HttpServletResponse response) throws
		IOException {
		Object attribute = request.getAttribute(VerifyUserFilter.AUTHENTICATE_USER);
		if (!(attribute instanceof AuthenticateUser)) {
			throw new RestApiException(LoginErrorCode.NOT_MATCH_LOGIN);
		}
		Map<String, Object> claims = new HashMap<>();
		AuthenticateUser authenticateUser = (AuthenticateUser)attribute;
		String authenticateUserJson = objectMapper.writeValueAsString(authenticateUser);
		claims.put(VerifyUserFilter.AUTHENTICATE_USER, authenticateUserJson);
		Jwt jwt = jwtProvider.createJwt(claims);
		authenticateUserService.updateRefreshToken(authenticateUser, jwt);

		response.addHeader("Authorization", jwt.createAccessTokenHeaderValue());
		response.addCookie(jwt.createRefreshTokenCookie());
		return ResponseEntity.ok(new UserLoginResponse(authenticateUser, jwt));
	}
}
