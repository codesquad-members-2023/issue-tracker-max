package codesquad.issueTracker.user.controller;

import codesquad.issueTracker.global.ApiResponse;
import codesquad.issueTracker.user.dto.SignUpRequestDto;
import codesquad.issueTracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static codesquad.issueTracker.global.SuccessCode.SUCCESS;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<String> signup(@Valid @RequestBody SignUpRequestDto userSignUpRequestDto) {
        userService.save(userSignUpRequestDto);

        return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
    }
}
