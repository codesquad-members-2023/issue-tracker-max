package codesquad.issueTracker.user.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.global.ApiResponse;
import codesquad.issueTracker.jwt.dto.RequestRefreshTokenDto;
import codesquad.issueTracker.user.dto.LoginRequestDto;
import codesquad.issueTracker.user.dto.LoginResponseDto;
import codesquad.issueTracker.user.dto.SignUpRequestDto;
import codesquad.issueTracker.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public ApiResponse<String> signUp(@Valid @RequestBody SignUpRequestDto userSignUpRequestDto) {
		userService.save(userSignUpRequestDto);

		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@PostMapping("/login")
	public ApiResponse<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
		LoginResponseDto loginResponseDto = userService.login(loginRequestDto);

		return ApiResponse.success(SUCCESS.getStatus(), loginResponseDto);
	}

	@PostMapping("/reissue/token")
	public ApiResponse<String> reissueToken(@RequestBody RequestRefreshTokenDto requestRefreshTokenDto) {
		String accessToken = userService.reissueAccessToken(requestRefreshTokenDto);
		return ApiResponse.success(SUCCESS.getStatus(), accessToken);
	}
}
