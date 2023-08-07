package codesquard.app.user.controller;

import static org.springframework.http.HttpStatus.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.user.controller.request.UserSaveRequest;
import codesquard.app.user.service.UserService;
import codesquard.app.user.service.response.UserSaveResponse;

@RequestMapping(path = "/api")
@RestController
public class UserRestController {

	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public ResponseEntity<UserSaveResponse> createUser(@Valid @RequestBody UserSaveRequest userSaveRequest) {
		UserSaveResponse userSaveResponse = userService.signUp(userSaveRequest.toUserSaveServiceRequest());
		return ResponseEntity.status(CREATED).body(userSaveResponse);
	}
}
