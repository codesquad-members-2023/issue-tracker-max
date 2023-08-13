package codesquad.issueTracker.user.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.global.common.ApiResponse;
import codesquad.issueTracker.jwt.dto.RequestRefreshTokenDto;
import codesquad.issueTracker.jwt.dto.ResponseAccessToken;
import codesquad.issueTracker.oauth.service.OAuthService;
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
	private final OAuthService oAuthService;

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
	public ApiResponse<ResponseAccessToken> reissueToken(@RequestBody RequestRefreshTokenDto requestRefreshTokenDto) {
		ResponseAccessToken accessToken = userService.reissueAccessToken(requestRefreshTokenDto);
		return ApiResponse.success(SUCCESS.getStatus(), accessToken);
	}

	@PostMapping("/logout")
	public ApiResponse<String> logout(HttpServletRequest request) {
		userService.logout(request);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@GetMapping("/login/{provider}")
	public ApiResponse<LoginResponseDto> oauthLogin(@PathVariable String provider, @RequestParam String code) {
		LoginResponseDto loginResponseDto = oAuthService.oauthLogin(provider, code);
		return ApiResponse.success(SUCCESS.getStatus(), loginResponseDto);
	}
}
