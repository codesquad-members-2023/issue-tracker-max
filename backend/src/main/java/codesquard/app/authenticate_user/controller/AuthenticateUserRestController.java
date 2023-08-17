package codesquard.app.authenticate_user.controller;

import static codesquard.app.api.response.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.errorcode.JwtTokenErrorCode;
import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.response.ApiResponse;
import codesquard.app.authenticate_user.controller.response.RefreshTokenResponse;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.service.AuthenticateUserService;
import codesquard.app.authenticate_user.service.request.RefreshTokenServiceRequest;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.jwt.filter.VerifyUserFilter;
import codesquard.app.user.annotation.Login;

@RestController
public class AuthenticateUserRestController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticateUserRestController.class);

	private final AuthenticateUserService authenticateUserService;
	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;

	public AuthenticateUserRestController(AuthenticateUserService authenticateUserService, JwtProvider jwtProvider,
		ObjectMapper objectMapper) {
		this.authenticateUserService = authenticateUserService;
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
	}

	@PostMapping("/api/auth/refresh/token")
	public ApiResponse<RefreshTokenResponse> refreshToken(@CookieValue String refreshToken,
		HttpServletResponse response) {
		logger.info("refreshToken : {}", refreshToken);
		Jwt jwt = authenticateUserService.refreshToken(new RefreshTokenServiceRequest(refreshToken));
		if (jwt == null) {
			throw new RestApiException(LoginErrorCode.FAIL_REFRESHTOKEN);
		}
		response.addHeader("Authorization", jwt.createAccessTokenHeaderValue());
		response.addCookie(jwt.createRefreshTokenCookie());
		return ApiResponse.of(OK, REFRESHTOKEN_UPDATE_SUCCESS, new RefreshTokenResponse(jwt));
	}

	@PostMapping("/api/auth/logout")
	public ApiResponse<AuthenticateUser> logout(@Login AuthenticateUser user) {
		Jwt jwt = getJwt(user);
		authenticateUserService.logout(user, jwt);
		return ApiResponse.of(OK, LOGOUT_SUCCESS, user);
	}

	private Jwt getJwt(AuthenticateUser user) {
		Map<String, Object> claims = new HashMap<>();
		try {
			claims.put(VerifyUserFilter.AUTHENTICATE_USER, objectMapper.writeValueAsString(user));
			return jwtProvider.createJwt(claims);
		} catch (JsonProcessingException e) {
			logger.error("AuthenticateUser 객체를 json으로 변환중 오류 : {}", e.getMessage());
			throw new RestApiException(JwtTokenErrorCode.FAIL_PARSE_JSON);
		}
	}
}

