package codesquad.issueTracker.user.service;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
}
