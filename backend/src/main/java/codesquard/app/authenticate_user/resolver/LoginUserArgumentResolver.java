package codesquard.app.authenticate_user.resolver;

import static codesquard.app.jwt.filter.VerifyUserFilter.*;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.user.annotation.Login;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
	private static final Logger logger = LoggerFactory.getLogger(LoginUserArgumentResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		logger.info("supportParameter 실행");
		boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
		boolean hasUserType = AuthenticateUser.class.isAssignableFrom(parameter.getParameterType());

		return hasLoginAnnotation && hasUserType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		AuthenticateUser authenticateUser = (AuthenticateUser)request.getAttribute(AUTHENTICATE_USER);
		if (authenticateUser == null) {
			throw new RestApiException(LoginErrorCode.UNAUTHORIZED);
		}
		logger.info("resolveArgument, authenticateUser : {}", authenticateUser);
		return authenticateUser;
	}
}
