package org.presents.issuetracker.user.controller;

import java.util.List;

import org.presents.issuetracker.user.dto.response.UserResponse;
import org.presents.issuetracker.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/previews")
	public ResponseEntity<List<UserResponse>> getUserPreviews() {
		return ResponseEntity.ok().body(userService.getUserPreviews());
	}
}
