package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request.LoginRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request.RefreshTokenRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.request.SignUpRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.response.JwtResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.JwtService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/api/login")
    public JwtResponse login(@RequestBody @Valid LoginRequest request) {
        return JwtResponse.from(jwtService.login(LoginRequest.to(request)));
    }

    @PostMapping("/api/signup")
    public ApiResponse signUp(@RequestBody @Valid SignUpRequest request) {
        jwtService.signUp(SignUpRequest.to(request));
        return ApiResponse.success(HttpStatus.OK);
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
