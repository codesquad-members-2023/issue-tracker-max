package codesquard.app.user.controller;

import static codesquard.app.api.response.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.user.controller.request.UserSaveRequest;
import codesquard.app.user.service.UserService;
import codesquard.app.user.service.response.UserSaveResponse;
import codesquard.app.user.service.response.UserSaveServiceResponse;

@RequestMapping(path = "/api")
@RestController
public class UserRestController {

	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/users")
	public ApiResponse<UserSaveResponse> createUser(@Valid @RequestBody UserSaveRequest userSaveRequest) {
		logger.info("일반 회원가입 매핑: {}", userSaveRequest);
		UserSaveServiceResponse userSaveServiceResponse =
			userService.signUp(userSaveRequest.toUserSaveServiceRequest());
		return ApiResponse.of(CREATED, USER_SIGNUP_SUCCESS, userSaveServiceResponse.toUserSaveResponse());
	}
}
