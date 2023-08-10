package codesquard.app.user.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.jwt.filter.VerifyUserFilter;

public class UserLoginInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		Object attribute = request.getAttribute(VerifyUserFilter.AUTHENTICATE_USER);
		// attribute가 AuthenticateUser 객체인지 검증
		if (!(attribute instanceof AuthenticateUser)) {
			throw new RestApiException(LoginErrorCode.NOT_MATCH_LOGIN);
		}
		logger.info("authenticateUser : {}", attribute);
		return true;
	}
}
