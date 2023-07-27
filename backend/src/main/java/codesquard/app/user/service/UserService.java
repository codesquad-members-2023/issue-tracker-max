package codesquard.app.user.service;

import org.springframework.stereotype.Service;

import codesquard.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

}
