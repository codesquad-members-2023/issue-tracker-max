package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request.LoginRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request.RefreshTokenRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.response.JwtLoginResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.response.JwtResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.JwtService;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.information.JwtLoginInformation;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final JwtService jwtService;

    @PostMapping("/api/login")
    public JwtLoginResponse login(@RequestBody @Valid LoginRequest request, HttpServletResponse response) {
        JwtLoginInformation information = jwtService.login(LoginRequest.to(request));

        Cookie cookie =  new Cookie("refreshToken", information.getJwt().getRefreshToken());
        cookie.setMaxAge(1000 * 60 * 60 * 24 * 60); // 60일
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        return JwtLoginResponse.from(information);
    }

    @PostMapping("/api/auth/reissue")
    public JwtResponse reissueAccessToken(@RequestBody RefreshTokenRequest request) {
        return JwtResponse.from(jwtService.reissueAccessToken(request.getRefreshToken()));
    }

    @PostMapping("/api/logout")
    public ApiResponse logout(HttpServletRequest request) {
        jwtService.logout(Long.parseLong(String.valueOf(request.getAttribute("memberId"))));
        return ApiResponse.success(HttpStatus.OK);
    }
}
