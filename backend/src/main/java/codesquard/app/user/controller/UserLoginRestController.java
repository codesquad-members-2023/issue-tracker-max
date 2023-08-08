package codesquard.app.user.controller;

import static codesquard.app.api.response.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.response.ApiResponse;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
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
	public ApiResponse<UserLoginResponse> login(HttpServletRequest request, HttpServletResponse response) throws
		IOException {
		Object attribute = request.getAttribute(VerifyUserFilter.AUTHENTICATE_USER);
		// attribute가 AuthenticateUser 객체인지 검증
		if (!(attribute instanceof AuthenticateUser)) {
			throw new RestApiException(LoginErrorCode.NOT_MATCH_LOGIN);
		}
		Map<String, Object> claims = new HashMap<>();
		AuthenticateUser authenticateUser = (AuthenticateUser)attribute;
		// 1. AuthenticateUser 객체를 json으로 변환
		String authenticateUserJson = objectMapper.writeValueAsString(authenticateUser);
		// 2. key="authenticateUser" value=인증 객체의 json 데이터를 claims 맵에 저장
		claims.put(VerifyUserFilter.AUTHENTICATE_USER, authenticateUserJson);
		// 3. claims을 기반으로 JWT 객체(액세스 토큰, 갱신 토큰) 생성
		Jwt jwt = jwtProvider.createJwt(claims);
		// 4. 갱신 토큰 최신 업데이트
		authenticateUserService.updateRefreshToken(authenticateUser, jwt);
		// 5. Authroization 헤더에 액세스 토큰 저장
		response.addHeader("Authorization", jwt.createAccessTokenHeaderValue());
		// 6. 쿠키에 key=refreshToken, value=갱신토큰 값 저장
		response.addCookie(jwt.createRefreshTokenCookie());
		return ApiResponse.of(OK, USER_LOGIN_SUCCESS, new UserLoginResponse(authenticateUser, jwt));
	}
}
