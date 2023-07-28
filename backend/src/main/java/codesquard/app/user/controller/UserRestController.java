package codesquard.app.user.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquard.app.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserRestController {

	private final UserService userService;

}
