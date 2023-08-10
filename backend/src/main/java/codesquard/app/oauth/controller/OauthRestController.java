package codesquard.app.oauth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.oauth.controller.response.OauthLoginResponse;
import codesquard.app.oauth.service.OauthService;
import codesquard.app.oauth.service.response.OauthLoginServiceResponse;

@RequestMapping(path = "/api")
@RestController
public class OauthRestController {
	private final OauthService oauthService;

	public OauthRestController(OauthService oauthService) {
		this.oauthService = oauthService;
	}

	@PostMapping("/login/oauth/{provider}")
	public ApiResponse<OauthLoginResponse> login(@PathVariable String provider, @RequestParam String code,
		HttpServletResponse response) {
		OauthLoginServiceResponse serviceResponse = oauthService.login(provider, code);
		// Authroization 헤더에 액세스 토큰 저장
		response.addHeader("Authorization", serviceResponse.createAccessTokenHeaderValue());
		// 쿠키에 key=refreshToken, value=갱신토큰 값 저장
		response.addCookie(serviceResponse.createRefreshTokenCookie());
		return ApiResponse.ok(serviceResponse.toOauthLoginResponse());
	}
}
