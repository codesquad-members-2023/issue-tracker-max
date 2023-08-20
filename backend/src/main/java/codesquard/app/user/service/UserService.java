package codesquard.app.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.api.errors.errorcode.UserErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.user.repository.UserRepository;
import codesquard.app.user.service.request.UserSaveServiceRequest;
import codesquard.app.user.service.response.UserSaveServiceResponse;

@Transactional
@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserQueryService userQueryService;
	private final UserRepository userRepository;

	public UserService(UserQueryService userQueryService, UserRepository userRepository) {
		this.userQueryService = userQueryService;
		this.userRepository = userRepository;
	}

	public UserSaveServiceResponse signUp(UserSaveServiceRequest userSaveServiceRequest) {
		logger.info("일반 회원가입 서비스 요청: {}", userSaveServiceRequest);
		// 비밀번호와 비밀번호 확인이 서로 일치하는지 검증
		validatePasswordMatching(userSaveServiceRequest);
		// 로그인아이디가 이미 존재하는지 검증
		validateDuplicatedLoginId(userSaveServiceRequest);
		// 이메일이 이미 존재하는지 검증
		validateDuplicatedEmail(userSaveServiceRequest);
		// 비밀번호 암호화
		UserSaveServiceRequest encryptedRequest = userSaveServiceRequest.encryptPassword();
		logger.debug("encryptedRequest : {}", encryptedRequest);
		Long saveId = userRepository.save(encryptedRequest.toEntity());
		return userQueryService.findUserById(saveId);
	}

	private void validatePasswordMatching(UserSaveServiceRequest userSaveServiceRequest) {
		if (!userSaveServiceRequest.matchPasswordAndPasswordConfirm()) {
			throw new RestApiException(UserErrorCode.NOT_MATCH_PASSWORD);
		}
	}

	private void validateDuplicatedLoginId(UserSaveServiceRequest userSaveServiceRequest) {
		if (userQueryService.verifyDuplicatedLoginId(userSaveServiceRequest)) {
			throw new RestApiException(UserErrorCode.ALREADY_EXIST_LOGINID);
		}
	}

	private void validateDuplicatedEmail(UserSaveServiceRequest userSaveServiceRequest) {
		if (userQueryService.verifyDuplicatedEmail(userSaveServiceRequest)) {
			throw new RestApiException(UserErrorCode.ALREADY_EXIST_EMAIL);
		}
	}
}
