package org.presents.issuetracker.global.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.presents.issuetracker.global.error.handler.GlobalExceptionHandler;
import org.presents.issuetracker.global.error.response.CommonApiResponse;
import org.presents.issuetracker.global.error.statuscode.JwtErrorCode;
import org.presents.issuetracker.global.error.statuscode.StatusCode;
import org.presents.issuetracker.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthorizationFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORIZATION_PREFIX = "Bearer ";
	private static final String[] excludeUris = new String[] {
		"/users/login", "/users/signup", "/reissue-access-token", "/login/oauth", "/login/oauth/callback"
	};

	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;

		log.info("[request] : {} {}", req.getMethod(),
			req.getRequestURI());

		if (HttpMethod.OPTIONS.matches(req.getMethod())) {
			chain.doFilter(request, response);
			return;
		}

		if (matchesExcludeUris(req.getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}

		String token = getToken(req);
		if (token == null) {
			sendErrorResponse(res, JwtErrorCode.INVALID_JWT_TOKEN);
			return;
		}

		try {
			Map<String, Object> claims = jwtProvider.getClaims(token);
			Long userId = ((Number)claims.get("userId")).longValue();
			request.setAttribute("userId", userId);
			chain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			sendErrorResponse(res, JwtErrorCode.EXPIRED_JWT_TOKEN);
		} catch (Exception e) {
			sendErrorResponse(res, JwtErrorCode.MALFORMED_JWT_TOKEN);
		}
	}

	private String getToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTHORIZATION_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}

	private boolean matchesExcludeUris(String requestUri) {
		return PatternMatchUtils.simpleMatch(excludeUris, requestUri);
	}

	private void sendErrorResponse(HttpServletResponse response, StatusCode statusCode) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setStatus(statusCode.getHttpStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(objectMapper.writeValueAsString(
			CommonApiResponse.fail(statusCode.getHttpStatus(), statusCode.getMessage()))
		);
	}
}
