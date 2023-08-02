package codesquad.issueTracker.user.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import codesquad.issueTracker.global.CustomException;
import codesquad.issueTracker.global.ErrorCode;
import codesquad.issueTracker.user.domain.User;
import codesquad.issueTracker.user.dto.LoginRequestDto;
import codesquad.issueTracker.user.dto.LoginResponseDto;
import codesquad.issueTracker.user.dto.SignUpRequestDto;
import codesquad.issueTracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;

	public Long save(SignUpRequestDto userSignUpRequestDto) {
		validateDuplicatedEmail(userSignUpRequestDto);
		String encodedPassword = BCrypt.hashpw(userSignUpRequestDto.getPassword(), BCrypt.gensalt());
		User user = SignUpRequestDto.toEntity(userSignUpRequestDto, encodedPassword);
		return userRepository.insert(user);
	}

	/**
	 * 1. 이미 등록된 유저인지 확인
	 * 2. 로그인 성공 여부 체크
	 * 3. 재로그인 하는 유저라면 access token 재발급
	 * 4. 처음 로그인 하는 유저라면 jwt 생성 후 db 저장
	 * 5. user 정보와 토큰 두 개 응답
	 */
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		User user = userRepository.findByEmail(loginRequestDto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
		validateLoginUser(loginRequestDto, user);
		Token token = userRepository.findTokenByUserId(user.getId()).orElse(null);
		Jwt jwt = jwtProvider.createJwt(Map.of("userId", user.getId()));
		if (token == null) {
			userRepository.insertRefreshToken(user.getId(), jwt.getRefreshToken());
		} else {
			userRepository.updateRefreshToken(user.getId(), jwt.getRefreshToken());
		}
		return LoginResponseDto.builder()
			.userId(user.getId())
			.profileImgUrl(user.getProfileImg())
			.accessToken(jwt.getAccessToken())
			.refreshToken(jwt.getRefreshToken())
			.build();

	}

	public void validateLoginUser(LoginRequestDto loginRequestDto, User user) {
		if (!loginRequestDto.getEmail().equals(user.getEmail())
			|| !BCrypt.checkpw(loginRequestDto.getPassword(), user.getPassword()))
			throw new CustomException(ErrorCode.FAILED_LOGIN_USER);
	}

	public void validateDuplicatedEmail(SignUpRequestDto signUpRequestDto) {
		if (!userRepository.findByEmail(signUpRequestDto.getEmail()).isEmpty()) {
			throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
		}
	}
}
