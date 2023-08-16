package com.issuetracker.common.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuetracker.account.infrastructure.JwtTokenGenerator;
import com.issuetracker.common.config.ErrorResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
@Order(2)
public class JwtAuthorizationFilter implements Filter {

	@Value("${jwt.whitelist}")
	private final String[] whiteListUris;

	private final JwtTokenGenerator jwtTokenGenerator;
	private final ObjectMapper objectMapper;

	public JwtAuthorizationFilter(JwtTokenGenerator jwtTokenGenerator, ObjectMapper objectMapper,
		String[] whiteListUris) {
		this.jwtTokenGenerator = jwtTokenGenerator;
		this.objectMapper = objectMapper;
		this.whiteListUris = whiteListUris;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		HttpServletRequest httpServletRequest = (HttpServletRequest)request;

		if (httpServletRequest.getMethod().equals("OPTIONS")) {
			return;
		}

		if (whiteListCheck(httpServletRequest.getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}

		if (!isContainToken(httpServletRequest)) {
			sendMalformedJwtException(response);
			return;
		}

		try {
			String token = getToken(httpServletRequest);
			Claims claims = jwtTokenGenerator.getClaims(token);
			if (!isAccessToken(claims)) {
				sendMalformedJwtException(response);
				return;
			}
			request.setAttribute("memberId", claims.get("memberId"));

			chain.doFilter(request, response);
		} catch (ExpiredJwtException e1) {
			sendExpiredErrorApiResponse(response);
		} catch (RuntimeException e2) {
			sendErrorApiResponse(response, e2);
		}
	}

	private boolean whiteListCheck(String uri) {
		return PatternMatchUtils.simpleMatch(whiteListUris, uri);
	}

	private boolean isContainToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		return authorization != null && authorization.startsWith("Bearer ");
	}

	private String getToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		return authorization.substring(7).replace("\"", "");
	}

	private boolean isAccessToken(Claims claims) {
		return claims.get("memberId") != null;
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

	private void sendMalformedJwtException(ServletResponse response) throws IOException {
		sendErrorApiResponse(response, new MalformedJwtException("토큰이 없거나 잘못된 형식 입니다."));
	}

	private void sendExpiredErrorApiResponse(ServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		((HttpServletResponse)response).setStatus(HttpStatus.UNAUTHORIZED.value());

		response.getWriter().write(
			objectMapper.writeValueAsString(
				new ResponseEntity<ErrorResponse>(
					new ErrorResponse(HttpStatus.UNAUTHORIZED, "기한이 만료된 토큰입니다."),
					HttpStatus.UNAUTHORIZED
				)
			)
		);
	}

	private ResponseEntity<ErrorResponse> generateErrorApiResponse(RuntimeException e) {
		return ResponseEntity.badRequest().body(
			new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage())
		);
	}

}