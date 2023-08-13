package codesquad.issueTracker.user.service;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.user.domain.LoginType;
import codesquad.issueTracker.user.dto.SignUpRequestDto;
import codesquad.issueTracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserValidator {
	private final UserRepository userRepository;

	public void validateLoginType(LoginType inputLoginType, LoginType existLoginType) {
		if (!inputLoginType.equals(existLoginType)) {
			throw new CustomException(ErrorCode.FAILED_LOGIN_USER);
		}
	}

	public void validateDuplicatedEmail(SignUpRequestDto signUpRequestDto) {
		if (!userRepository.findByEmail(signUpRequestDto.getEmail()).isEmpty()) {
			throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
		}
	}

}
