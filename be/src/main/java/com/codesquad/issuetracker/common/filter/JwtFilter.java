package com.codesquad.issuetracker.common.filter;

import static com.codesquad.issuetracker.common.util.RequestUtil.extractAccessToken;

import com.codesquad.issuetracker.api.jwt.service.JwtProvider;
import com.codesquad.issuetracker.common.exception.CustomRuntimeException;
import com.codesquad.issuetracker.common.exception.customexception.JwtException;
import com.codesquad.issuetracker.redis.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

@RequiredArgsConstructor
@Component
public class JwtFilter implements Filter {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String MEMBER_ID = "memberId";
    private static final String[] whiteListUris = {
            "/api/sign-up/**",
            "/api/oauth/**",
            "/api/sign-in",
            "/api/reissue-access-token",
            "/"
    };
    public static final String OPTIONS = "OPTIONS";

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final RedisUtil redisUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (httpServletRequest.getMethod().equals(OPTIONS)) {
            return;
        }

        if (whiteListCheck(httpServletRequest.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        if (!isContainToken(httpServletRequest)) {
            sendJwtExceptionResponse(response, new MalformedJwtException(""));
            return;
        }

        if (redisUtil.hasKeyBlackList(extractAccessToken(httpServletRequest))) {
            sendJwtExceptionResponse(response, new CustomRuntimeException(JwtException.BLACKLISTED_JWT_EXCEPTION));
            return;
        }

        try {
            String token = extractAccessToken(httpServletRequest);
            Claims claims = jwtProvider.getClaims(token);
            Long memberId = convertMemberIdToLong(claims);
            request.setAttribute(MEMBER_ID, memberId);
            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            sendJwtExceptionResponse(response, e);
        }
    }

    private Long convertMemberIdToLong(Claims claims) {
        Object memberIdObj = claims.get(MEMBER_ID);
        Long memberId = Long.valueOf(memberIdObj.toString());
        return memberId;
    }

    private boolean whiteListCheck(String uri) {
        return PatternMatchUtils.simpleMatch(whiteListUris, uri);
    }

    private boolean isContainToken(HttpServletRequest request) {
        String authorization = request.getHeader(HEADER_AUTHORIZATION);
        return authorization != null && authorization.startsWith(TOKEN_PREFIX);
    }

    private void sendJwtExceptionResponse(ServletResponse response, RuntimeException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());

        JwtException jwtException = JwtException.from(e);

        response.getWriter().write(
                objectMapper.writeValueAsString(
                        new CustomRuntimeException(jwtException).sendError().getBody()
                ));
    }
}
