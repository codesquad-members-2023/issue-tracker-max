package org.presents.issuetracker.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.user.dto.response.UserResponse;
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
}
