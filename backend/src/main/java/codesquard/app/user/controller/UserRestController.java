package codesquard.app.user.controller;

import static org.springframework.http.HttpStatus.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.user.controller.request.UserSaveRequest;
import codesquard.app.user.service.UserService;
import codesquard.app.user.service.response.UserSaveResponse;

@RestController
public class UserRestController {

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/api/users")
	public ResponseEntity<UserSaveResponse> createUser(@Valid @RequestBody UserSaveRequest userSaveRequest) {
		UserSaveResponse userSaveResponse = userService.signUp(userSaveRequest.toUserSaveServiceRequest());
		return ResponseEntity.status(CREATED).body(userSaveResponse);
	}
}
