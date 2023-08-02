package codesquard.app.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.user.repository.UserRepository;
import codesquard.app.user.service.request.UserSaveServiceRequest;
import codesquard.app.user.service.response.UserSaveResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UserSaveResponse signUp(UserSaveServiceRequest userSaveServiceRequest) {
		userRepository.save(userSaveServiceRequest.toEntity());
		return UserSaveResponse.success();
	}
}
