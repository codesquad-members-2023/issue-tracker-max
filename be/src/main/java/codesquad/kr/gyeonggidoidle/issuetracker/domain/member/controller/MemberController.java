package codesquad.kr.gyeonggidoidle.issuetracker.domain.member.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.controller.request.SignUpRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/signup")
    public ApiResponse signUp(@RequestBody @Valid SignUpRequest request) {
        memberService.signUp(SignUpRequest.to(request));
        return ApiResponse.success(HttpStatus.OK);
    }
}
