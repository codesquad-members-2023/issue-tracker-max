package com.issuetrackermax.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.cors.CorsUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuetrackermax.common.exception.domain.JwtException;
import com.issuetrackermax.common.exception.response.ErrorResponse;
import com.issuetrackermax.service.jwt.JwtProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;

public class JwtAuthorizationFilter implements Filter {
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String MEMBER_ID = "memberId";
	private static final String[] whiteListUris = {"/h2-console/**", "/api/signin", "/api/signup/**",
		"/api/reissue-access-token", "/api/oauth/**", "/api/redirect/**"};

	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;

	public JwtAuthorizationFilter(ObjectMapper objectMapper, JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		if (CorsUtils.isPreFlightRequest(httpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		if (whiteListCheck(httpServletRequest.getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}

		if (!isContainToken(httpServletRequest)) {
			sendErrorApiResponse(response, new MalformedJwtException(""));
			return;
		}

		try {
			String token = getToken(httpServletRequest);
			Claims claims = jwtProvider.getClaims(token);
			request.setAttribute(MEMBER_ID,
				claims.get(MEMBER_ID));
			chain.doFilter(request, response);
		} catch (RuntimeException e) {
			sendErrorApiResponse(response, e);
		}
	}

	private boolean whiteListCheck(String uri) {
		return PatternMatchUtils.simpleMatch(whiteListUris, uri);
	}

	private boolean isContainToken(HttpServletRequest request) {
		String authorization = request.getHeader(HEADER_AUTHORIZATION);
		return authorization != null && authorization.startsWith(TOKEN_PREFIX);
	}

	private String getToken(HttpServletRequest request) {
		String authorization = request.getHeader(HEADER_AUTHORIZATION);
		return authorization.substring(7).replace("\"", "");
	}

	private void sendErrorApiResponse(ServletResponse response, RuntimeException ex) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		((HttpServletResponse)response).setStatus(HttpStatus.UNAUTHORIZED.value());

		response.getWriter().write(
			objectMapper.writeValueAsString(
				generateErrorApiResponse(ex))
		);
	}

	private ErrorResponse generateErrorApiResponse(RuntimeException ex) {
		JwtException jwtException = JwtException.from(ex);
		return new ErrorResponse(jwtException.getHttpStatus().value(), jwtException.getMessage());
	}
}
