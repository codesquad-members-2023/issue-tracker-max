package codesquard.app.jwt.filter;

import static codesquard.app.jwt.filter.VerifyUserFilter.*;

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
import org.springframework.util.PatternMatchUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.errorcode.ErrorCode;
import codesquard.app.api.errors.errorcode.JwtTokenErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.response.ApiResponse;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

public class JwtAuthorizationFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

	private static final String[] whiteListUris = {"/api/login", "/api/users", "/api/auth/refresh/token"};

	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;

	public JwtAuthorizationFilter(JwtProvider jwtProvider, ObjectMapper objectMapper) {
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;

		logger.info("doFilter : {}", httpServletRequest.getRequestURI());

		if (whiteListCheck(httpServletRequest.getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}
		if (!isContainToken(httpServletRequest)) {
			setErrorResponse(httpServletResponse, JwtTokenErrorCode.FAIL_AUTHENTICATION);
			return;
		}
		try {
			// 1. 액세스 토큰값 추출
			String token = parseToken(httpServletRequest);
			// 2. 액세스 토큰 복호화하여 AuthenticateUser 객체로 읽기
			AuthenticateUser authenticateUser = getAuthenticateUser(token);
			logger.info("authenticateUser : {}", authenticateUser);
			// 3. Request user 속성에 AuthenticateUser 객체 저장
			httpServletRequest.setAttribute(AUTHENTICATE_USER, authenticateUser);
			chain.doFilter(request, response);
		} catch (JsonParseException e) {
			logger.error("JsonParseException");
			setErrorResponse(httpServletResponse, JwtTokenErrorCode.FAIL_PARSE_JSON);
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException e) {
			logger.error("JwtException");
			setErrorResponse(httpServletResponse, JwtTokenErrorCode.FAIL_AUTHENTICATION);
		} catch (ExpiredJwtException e) {
			logger.error("JwtTokenExpired");
			setErrorResponse(httpServletResponse, JwtTokenErrorCode.EXPIRE_TOKEN);
		} catch (RestApiException e) {
			logger.error("AuthorizationException");
			setErrorResponse(httpServletResponse, JwtTokenErrorCode.NOT_HAVE_AUTHORIZED);
		}
	}

	private void setErrorResponse(HttpServletResponse httpServletResponse, ErrorCode errorCode) throws IOException {
		httpServletResponse.setStatus(errorCode.getHttpStatus().value());
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setCharacterEncoding("UTF-8");
		ApiResponse<Object> body = ApiResponse.of(errorCode.getHttpStatus(), errorCode.getMessage(), null);
		httpServletResponse.getWriter().write(objectMapper.writeValueAsString(body));
	}

	// 요청한 URI가 화이트리스트에 있는지 확인합니다. 화이트 리스트에 있으면 인가처리합니다.
	private boolean whiteListCheck(String requestURI) {
		return PatternMatchUtils.simpleMatch(whiteListUris, requestURI);
	}

	// 요청 헤더에 Authorization 헤더가 있고 "Bearer "로 시작하는지 확인합니다.
	private boolean isContainToken(HttpServletRequest httpServletRequest) {
		String authorization = httpServletRequest.getHeader("Authorization");
		return authorization != null && authorization.startsWith("Bearer ");
	}

	// Authorization 헤더에서 액세스 토큰 값을 추출하여 반환합니다.
	private String parseToken(HttpServletRequest httpServletRequest) {
		String authorization = httpServletRequest.getHeader("Authorization");
		return authorization.substring(7);
	}

	// 토큰을 비밀키로 복호화하고 복호화한 데이터를 기반으로 AuthenticateUser 객체로 읽습니다.
	private AuthenticateUser getAuthenticateUser(String token) throws JsonProcessingException {
		Claims claims = jwtProvider.getClaims(token);
		String authenticateUserJson = claims.get(AUTHENTICATE_USER, String.class);
		return objectMapper.readValue(authenticateUserJson, AuthenticateUser.class);
	}
}
