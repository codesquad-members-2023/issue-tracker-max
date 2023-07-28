package com.issuetrackermax.controller.auth;

import com.issuetrackermax.controller.auth.dto.response.JwtResponse;
import com.issuetrackermax.domain.oAuth.service.OauthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {

    private final OauthService oauthService;

    public OAuthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping("/oauth/{provider}")
    public ResponseEntity<JwtResponse> login(@PathVariable String provider, @RequestParam String code) {
        JwtResponse jwtResponse = oauthService.login(provider, code);
        return ResponseEntity.ok().body(jwtResponse);
    }
}
