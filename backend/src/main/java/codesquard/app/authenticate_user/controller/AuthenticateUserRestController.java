package codesquard.app.authenticate_user.controller;

import static codesquard.app.api.response.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.response.ApiResponse;
import codesquard.app.authenticate_user.controller.response.RefreshTokenResponse;
import codesquard.app.authenticate_user.service.AuthenticateUserService;
import codesquard.app.authenticate_user.service.request.RefreshTokenServiceRequest;
import codesquard.app.jwt.Jwt;

@RestController
public class AuthenticateUserRestController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticateUserRestController.class);

	private final AuthenticateUserService authenticateUserService;

	public AuthenticateUserRestController(AuthenticateUserService authenticateUserService) {
		this.authenticateUserService = authenticateUserService;
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
}
