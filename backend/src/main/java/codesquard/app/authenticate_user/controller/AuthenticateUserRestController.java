package codesquard.app.authenticate_user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.authenticate_user.controller.request.RefreshTokenRequest;
import codesquard.app.authenticate_user.controller.response.RefreshTokenResponse;
import codesquard.app.errors.errorcode.LoginErrorCode;
import codesquard.app.errors.exception.RestApiException;
import codesquard.app.jwt.Jwt;
import codesquard.app.user.service.AuthenticateUserService;

@RestController
public class AuthenticateUserRestController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticateUserRestController.class);

	private final AuthenticateUserService authenticateUserService;

	public AuthenticateUserRestController(AuthenticateUserService authenticateUserService) {
		this.authenticateUserService = authenticateUserService;
	}

	@PostMapping("/api/auth/refresh/token")
	public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest,
		HttpServletRequest request, HttpServletResponse response) {
		logger.info("refreshTokenRequest : {}", refreshTokenRequest);
		Jwt jwt = authenticateUserService.refreshToken(refreshTokenRequest.toRefreshTokenServiceRequest());
		if (jwt == null) {
			throw new RestApiException(LoginErrorCode.FAIL_REFRESHTOKEN);
		}
		response.addHeader("Authorization", jwt.createAccessTokenHeaderValue());
		response.addCookie(jwt.createRefreshTokenCookie());
		return ResponseEntity.ok(new RefreshTokenResponse(jwt));
	}
}
