package codesquard.app.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.errors.errorcode.UserErrorCode;
import codesquard.app.errors.exception.RestApiException;
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
		// 비밀번호와 비밀번호 확인이 서로 일치하는지 검증
		validatePasswordMatching(userSaveServiceRequest);
		validateDuplicatedLoginId(userSaveServiceRequest);
		validateDuplicatedEmail(userSaveServiceRequest);
		userRepository.save(userSaveServiceRequest.toEntity());
		return UserSaveResponse.success();
	}

	private void validatePasswordMatching(UserSaveServiceRequest userSaveServiceRequest) {
		if (!userSaveServiceRequest.matchPasswordAndPasswordConfirm()) {
			throw new RestApiException(UserErrorCode.NOT_MATCH_PASSWORD);
		}
	}

	private void validateDuplicatedLoginId(UserSaveServiceRequest userSaveServiceRequest) {
		if (userRepository.existLoginId(userSaveServiceRequest.toEntity())) {
			throw new RestApiException(UserErrorCode.ALREADY_EXIST_LOGINID);
		}
	}

	private void validateDuplicatedEmail(UserSaveServiceRequest userSaveServiceRequest) {
		if (userRepository.existEmail(userSaveServiceRequest.toEntity())) {
			throw new RestApiException(UserErrorCode.ALREADY_EXIST_EMAIL);
		}
	}
}
