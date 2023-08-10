package codesquard.app.oauth.controller;

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
	public ApiResponse<OauthLoginResponse> login(@PathVariable String provider, @RequestParam String code) {
		OauthLoginServiceResponse serviceResponse = oauthService.login(provider, code);
		return ApiResponse.ok(serviceResponse.toOauthLoginResponse());
	}
}
