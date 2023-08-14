package org.presents.issuetracker.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.global.error.exception.CustomException;
import org.presents.issuetracker.global.error.statuscode.UserErrorCode;
import org.presents.issuetracker.user.dto.request.UserRequest;
import org.presents.issuetracker.user.dto.response.UserResponse;
import org.presents.issuetracker.user.entity.User;
import org.presents.issuetracker.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public List<UserResponse> getUserPreviews() {
		return userRepository.findAll().stream()
			.map(UserResponse::from)
			.collect(Collectors.toList());
	}

	public void createUser(UserRequest userRequest) {
		userRepository.findByLoginId(userRequest.getLoginId())
			.ifPresent(user -> {
				throw new CustomException(UserErrorCode.DUPLICATED_LOGIN_ID);
			});

		userRepository.save(User.builder()
			.loginId(userRequest.getLoginId())
			.password(userRequest.getPassword())
			.build());
	}
}
