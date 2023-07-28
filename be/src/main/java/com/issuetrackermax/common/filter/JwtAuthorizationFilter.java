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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuetrackermax.common.exception.ErrorCode;
import com.issuetrackermax.common.exception.ErrorResponse;
import com.issuetrackermax.common.exception.JwtExceptionType;
import com.issuetrackermax.domain.jwt.service.JwtProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;

public class JwtAuthorizationFilter implements Filter {
	private final String[] whiteListUris = new String[] {"/h2-console/**", "/login", "/signup",
		"/reissue-access-token"};
	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;
	private final String TOKEN_PREFIX = "Bearer ";
	private final String HEADER_AUTHORIZATION = "Authorization";

	public JwtAuthorizationFilter(ObjectMapper objectMapper, JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		HttpServletRequest httpServletRequest = (HttpServletRequest)request;

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
			request.setAttribute("memberId",
				claims.get("memberId")); // TODO: 닉네임을 사용한다면 nickname을 추가로 보내 주거나 멤버 response 객체를 하나 만들면 좋을 듯
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

	private void sendErrorApiResponse(ServletResponse response, RuntimeException e) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		((HttpServletResponse)response).setStatus(HttpStatus.UNAUTHORIZED.value());

		response.getWriter().write(
			objectMapper.writeValueAsString(
				generateErrorApiResponse(e))
		);
	}

	private ErrorResponse<ErrorCode> generateErrorApiResponse(RuntimeException e) {
		JwtExceptionType jwtExceptionType = JwtExceptionType.from(e);
		ErrorCode errorCode = new ErrorCode(jwtExceptionType.getHttpStatus().value(), jwtExceptionType.getMessage());
		return ErrorResponse.exception(errorCode);
	}
}
