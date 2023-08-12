package codesquard.app.jwt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.errorcode.ErrorCode;
import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.response.ApiResponse;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.user.controller.request.UserLoginRequest;
import codesquard.app.user.service.UserQueryService;

public class VerifyUserFilter implements Filter {
	public static final String AUTHENTICATE_USER = "authenticateUser";
	private static final Logger logger = LoggerFactory.getLogger(VerifyUserFilter.class);
	private final ObjectMapper objectMapper;
	private final UserQueryService userQueryService;

	public VerifyUserFilter(ObjectMapper objectMapper, UserQueryService userQueryService) {
		this.objectMapper = objectMapper;
		this.userQueryService = userQueryService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;

		logger.info("doFilter : {}", httpServletRequest.getRequestURI());

		if ((httpServletRequest.getMethod().equals("POST"))) {
			// 입력받은 json을 UserLoginRequest으로 변환
			UserLoginRequest userLoginRequest = objectMapper.readValue(request.getReader(), UserLoginRequest.class);
			logger.info("userLoginRequest : {}", userLoginRequest);
			try {
				// 로그인 아이디에 따른 회원이 있는지 확인
				AuthenticateUser authenticateUser =
					userQueryService.verifyUser(userLoginRequest.toUserLoginServiceRequest());
				logger.info("authenticateUser : {}", authenticateUser);
				// request 객체에 인증 유저 설정
				request.setAttribute(AUTHENTICATE_USER, authenticateUser);
				chain.doFilter(request, response);
			} catch (RestApiException e) {
				ErrorCode errorCode = LoginErrorCode.NOT_MATCH_LOGIN;
				httpServletResponse.setStatus(errorCode.getHttpStatus().value());
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				ApiResponse<ErrorCode> apiResponse = ApiResponse.error(errorCode);
				String errorJson = objectMapper.writeValueAsString(apiResponse);
				response.getWriter().write(errorJson);
			}
		}
	}
}
