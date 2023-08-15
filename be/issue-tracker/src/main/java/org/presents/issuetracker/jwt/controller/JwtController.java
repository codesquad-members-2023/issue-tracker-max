package org.presents.issuetracker.jwt.controller;

import org.presents.issuetracker.jwt.dto.Jwt;
import org.presents.issuetracker.jwt.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtController {
	private final JwtService jwtService;

	@PostMapping("/reissue-access-token")
	public ResponseEntity<Jwt> reissueAccessToken(@RequestBody Jwt jwt) {
		return ResponseEntity.ok().body(jwtService.reissueAccessToken(jwt.getRefreshToken()));
	}
}
