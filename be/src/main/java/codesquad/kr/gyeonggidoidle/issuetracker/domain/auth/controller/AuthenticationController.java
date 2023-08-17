package codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.controller.request.LoginRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.controller.response.JwtLoginResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.controller.response.JwtResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.controller.response.JwtTokenType;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service.JwtService;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service.OauthService;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service.information.JwtLoginInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.exception.IllegalJwtTokenException;
import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final OauthService oauthService;

    @PostMapping("/api/login")
    public JwtLoginResponse login(@RequestBody @Valid LoginRequest request, HttpServletResponse response) {
        JwtLoginInformation information = jwtService.login(LoginRequest.to(request));

        return getJwtLoginResponse(response, information);
    }

    @GetMapping("/api/auth/reissue")
    public JwtResponse reissueAccessToken(HttpServletRequest request) {
        return JwtResponse.from(jwtService.reissueAccessToken(getRefreshToken(request)));
    }

    @PostMapping("/api/logout")
    public ApiResponse logout(HttpServletRequest request) {
        jwtService.logout(Long.parseLong(String.valueOf(request.getAttribute("memberId"))));
        return ApiResponse.success(HttpStatus.OK);
    }

    @GetMapping("/api/login/oauth/{provider}")
    public JwtLoginResponse oauthLogin(@PathVariable String provider, @RequestParam String code, HttpServletResponse response) {
        JwtLoginInformation information = oauthService.login(provider, code);

        return getJwtLoginResponse(response, information);
    }

    private JwtLoginResponse getJwtLoginResponse(HttpServletResponse response, JwtLoginInformation information) {
        Cookie cookie =  new Cookie("refreshToken", information.getJwt().getRefreshToken());
        cookie.setMaxAge(60 * 60 * 24 * 60); // 60일
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        return JwtLoginResponse.from(information);
    }

    private String getRefreshToken(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> Objects.equals(cookie.getName(), "refreshToken"))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalJwtTokenException(JwtTokenType.REFRESH));
    }
}
