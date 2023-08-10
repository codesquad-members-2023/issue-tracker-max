package codesquard.app.user.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.service.response.AuthenticateUserLoginServiceResponse;
import codesquard.app.jwt.filter.VerifyUserFilter;

public class UserLoginInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);

	private final ObjectMapper objectMapper;

	public UserLoginInterceptor(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		Object attribute = request.getAttribute(VerifyUserFilter.AUTHENTICATE_USER);
		// attribute가 AuthenticateUser 객체인지 검증
		if (!(attribute instanceof AuthenticateUser)) {
			throw new RestApiException(LoginErrorCode.NOT_MATCH_LOGIN);
		}
		logger.info("authenticateUser : {}", (AuthenticateUser)attribute);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		AuthenticateUserLoginServiceResponse loginServiceResponse = (AuthenticateUserLoginServiceResponse)request.getAttribute(
			"loginServiceResponse");
		// Authroization 헤더에 액세스 토큰 저장
		response.addHeader("Authorization", loginServiceResponse.createAccessTokenHeaderValue());
		// 쿠키에 key=refreshToken, value=갱신토큰 값 저장
		response.addCookie(loginServiceResponse.createRefreshTokenCookie());
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
}
