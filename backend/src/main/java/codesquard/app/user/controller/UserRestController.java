package codesquard.app.user.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.user.dto.request.UserSIgnUpRequest;
import codesquard.app.user.dto.response.UserSignUpResponse;
import codesquard.app.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserRestController {

	private final UserService userService;

	@PostMapping("/api/users")
	public ResponseEntity<UserSignUpResponse> signUp(
		@Valid @RequestBody UserSIgnUpRequest userSIgnUpRequest) {
		Long id = userService.signUp(userSIgnUpRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(new UserSignUpResponse(true, id));
	}
}
