package codesquard.app.user.service;

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

	private final UserRepository userRepository;

	public UserQueryService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public AuthenticateUser verifyUser(UserLoginServiceRequest userLoginServiceRequest) {
		UserLoginServiceRequest encryptServiceRequest = userLoginServiceRequest.encryptPassword();
		User findUser = userRepository.findByLoginIdAndPassword(encryptServiceRequest.toEntity());
		return findUser.toAuthenticateUser();
	}

	public boolean verifyDuplicatedLoginId(UserSaveServiceRequest userSaveServiceRequest) {
		return userRepository.isExistLoginId(userSaveServiceRequest.toEntity());
	}

	public boolean verifyDuplicatedEmail(UserSaveServiceRequest userSaveServiceRequest) {
		return userRepository.isExistEmail(userSaveServiceRequest.toEntity());
	}

	public UserSaveServiceResponse findUserById(Long userId) {
		User findUser = userRepository.findById(userId);
		return UserSaveServiceResponse.from(findUser);
	}
}
