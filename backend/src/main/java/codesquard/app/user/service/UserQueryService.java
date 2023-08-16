package codesquard.app.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;
import codesquard.app.user.service.request.UserLoginServiceRequest;
import codesquard.app.user.service.request.UserSaveServiceRequest;
import codesquard.app.user.service.response.UserSaveServiceResponse;

@Transactional(readOnly = true)
@Service
public class UserQueryService {

	private static final Logger logger = LoggerFactory.getLogger(UserQueryService.class);

	private final UserRepository userRepository;

	public UserQueryService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public AuthenticateUser verifyUser(UserLoginServiceRequest userLoginServiceRequest) {
		logger.info("사용자 확인 : {}", userLoginServiceRequest);
		UserLoginServiceRequest encryptServiceRequest = userLoginServiceRequest.encryptPassword();
		logger.debug("encryptServiceRequest : {}", encryptServiceRequest);
		User findUser = userRepository.findByLoginIdAndPassword(encryptServiceRequest.toEntity());
		return findUser.toAuthenticateUser();
	}

	public boolean verifyDuplicatedLoginId(UserSaveServiceRequest userSaveServiceRequest) {
		logger.info("사용자 아이디 중복 확인 : {}", userSaveServiceRequest);
		return userRepository.isExistLoginId(userSaveServiceRequest.toEntity());
	}

	public boolean verifyDuplicatedEmail(UserSaveServiceRequest userSaveServiceRequest) {
		logger.info("사용자 이메일 중복 확인 : {}", userSaveServiceRequest);
		return userRepository.isExistEmail(userSaveServiceRequest.toEntity());
	}

	public UserSaveServiceResponse findUserById(Long userId) {
		logger.info("사용자 아이디로 조회 : {}", userId);
		User findUser = userRepository.findById(userId);
		return UserSaveServiceResponse.from(findUser);
	}
}
