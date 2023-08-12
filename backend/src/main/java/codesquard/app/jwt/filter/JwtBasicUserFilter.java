package codesquard.app.jwt.filter;

import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.errorcode.ErrorCode;
import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.response.ApiResponse;
import codesquard.app.api.response.ResponseMessage;
import codesquard.app.authenticate_user.controller.response.AuthenticateUserLoginResponse;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.service.AuthenticateUserService;
import codesquard.app.authenticate_user.service.response.AuthenticateUserLoginServiceResponse;

public class JwtBasicUserFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(JwtBasicUserFilter.class);

	private final AuthenticateUserService authenticateUserService;
	private final ObjectMapper objectMapper;

	public JwtBasicUserFilter(AuthenticateUserService authenticateUserService, ObjectMapper objectMapper) {
		this.authenticateUserService = authenticateUserService;
		this.objectMapper = objectMapper;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		Object attribute = request.getAttribute(VerifyUserFilter.AUTHENTICATE_USER);
		// attribute가 AuthenticateUser 객체인지 검증
		if (!(attribute instanceof AuthenticateUser)) {
			responseErrorCode(httpServletResponse);
			return;
		}
		AuthenticateUser authenticateUser = (AuthenticateUser)attribute;
		logger.info("authenticateUser : {}", authenticateUser);
		try {
			AuthenticateUserLoginServiceResponse loginServiceResponse = authenticateUserService.login(authenticateUser);
			// Authroization 헤더에 액세스 토큰 저장
			httpServletResponse.addHeader("Authorization", loginServiceResponse.createAccessTokenHeaderValue());
			// 쿠키에 key=refreshToken, value=갱신토큰 값 저장
			httpServletResponse.addCookie(loginServiceResponse.createRefreshTokenCookie());
			ApiResponse<AuthenticateUserLoginResponse> apiResponse = new ApiResponse<>(OK,
				ResponseMessage.USER_LOGIN_SUCCESS,
				loginServiceResponse.toAuthenticateUserLoginResponse());
			String responseBody = objectMapper.writeValueAsString(apiResponse);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			httpServletResponse.getWriter().write(responseBody);
		} catch (RestApiException e) {
			responseErrorCode(httpServletResponse);
		}
	}

	private void responseErrorCode(HttpServletResponse response) throws IOException {
		ErrorCode errorCode = LoginErrorCode.NOT_MATCH_LOGIN;
		response.setStatus(errorCode.getHttpStatus().value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ApiResponse<ErrorCode> apiResponse = ApiResponse.error(errorCode);
		String errorJson = objectMapper.writeValueAsString(apiResponse);
		response.getWriter().write(errorJson);
	}
}
