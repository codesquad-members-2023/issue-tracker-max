package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.JwtProvider;
import codesquad.kr.gyeonggidoidle.issuetracker.exception.JwtExceptionType;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;

public class JwtAuthorizationFilter implements Filter {

    private final String[] whiteListUris = new String[]{"/api/login", "/api/signup", "/api/auth/reissue"};
    private final JwtProvider jwtProvider = new JwtProvider();
    private final ObjectMapper objectMapper;

    public JwtAuthorizationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (httpServletRequest.getMethod().equals("OPTIONS")) {
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
            request.setAttribute("memberId", claims.get("memberId")); // TODO: email으로 수정 고려
            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            sendErrorApiResponse(response, e);
        }

    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return authorization.substring(7).replace("\"","");
    }

    private boolean whiteListCheck(String uri) {
        return PatternMatchUtils.simpleMatch(whiteListUris, uri);
    }

    private boolean isContainToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return authorization != null && authorization.startsWith("Bearer ");
    }

    private void sendErrorApiResponse(ServletResponse response, RuntimeException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());

        response.getWriter().write(
                objectMapper.writeValueAsString(
                    generateErrorApiResponse(e))
        );
    }

    private ApiResponse generateErrorApiResponse(RuntimeException e) {
        JwtExceptionType jwtExceptionType = JwtExceptionType.from(e);
        return ApiResponse.exception(jwtExceptionType.getHttpstatus(), jwtExceptionType.getMessage());
    }

}
