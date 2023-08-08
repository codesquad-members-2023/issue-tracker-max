package codesquad.issueTracker.user.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.jwt.domain.Jwt;
import codesquad.issueTracker.jwt.domain.Token;
import codesquad.issueTracker.jwt.dto.RequestRefreshTokenDto;
import codesquad.issueTracker.jwt.util.JwtProvider;
import codesquad.issueTracker.user.domain.LoginType;
import codesquad.issueTracker.user.domain.User;
import codesquad.issueTracker.user.dto.LoginRequestDto;
import codesquad.issueTracker.user.dto.LoginResponseDto;
import codesquad.issueTracker.user.dto.SignUpRequestDto;
import codesquad.issueTracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private final UserValidator userValidator;
	private final JwtProvider jwtProvider;
	private final String DEFAULT_PROFILE_IMG = "https://upload.wikimedia.org/wikipedia/commons/1/17/Enhydra_lutris_face.jpg";

	public Long save(SignUpRequestDto userSignUpRequestDto) {
		userValidator.validateDuplicatedEmail(userSignUpRequestDto);
		String encodedPassword = BCrypt.hashpw(userSignUpRequestDto.getPassword(), BCrypt.gensalt());
		User user = SignUpRequestDto.toEntity(userSignUpRequestDto, encodedPassword, DEFAULT_PROFILE_IMG);
		return userRepository.insert(user);
	}

	/**
	 * 1. 이미 등록된 유저인지 확인
	 * 2. 로그인 성공 여부 체크
	 * 3. access token, refresh token 발급
	 * 4. 처음 로그인 하는 유저라면 refresh token db insert
	 * 5. 재로그인하는 유저라면 refresh token db update
	 * 5. user 정보와 access token, refresh token 응답
	 */
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		User user = userRepository.findByEmail(loginRequestDto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
		user.validateLoginUser(loginRequestDto);

		Jwt jwt = jwtProvider.createJwt(Map.of("userId", user.getId()));

		insertOrUpdateToken(user.getId(), jwt);

		return LoginResponseDto.builder()
			.userId(user.getId())
			.profileImgUrl(user.getProfileImg())
			.accessToken(jwt.getAccessToken())
			.refreshToken(jwt.getRefreshToken())
			.build();
	}

	public void insertOrUpdateToken(Long userId, Jwt jwt) {
		if (userRepository.findTokenByUserId(userId).isEmpty()) {
			userRepository.insertRefreshToken(userId, jwt.getRefreshToken());
		} else {
			userRepository.updateRefreshToken(userId, jwt.getRefreshToken());
		}
	}

	private User insertOauthUser(User user) {
		userRepository.insert(user);
		return userRepository.findByEmail(user.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
	}

	public User findExistedOrInsertedUser(User user) {
		return userRepository.findByEmail(user.getEmail())
			.orElseGet(() -> insertOauthUser(user));
	}

	/**
	 * 1. 만료된 리프레시 토큰이면 예외처리
	 * 2. DB에 없는 리프레시 토큰이면 예외처리
	 */
	@Transactional(readOnly = true)
	public String reissueAccessToken(RequestRefreshTokenDto refreshTokenDto) {
		jwtProvider.getClaims(refreshTokenDto.getRefreshToken());

		Token token = userRepository.findTokenByUserToken(refreshTokenDto.getRefreshToken())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN));
		return jwtProvider.reissueAccessToken(Map.of("userId", token.getUserId()));
	}

	public void logout(HttpServletRequest request) {
		Long userId = Long.parseLong(String.valueOf(request.getAttribute("userId")));
		userRepository.deleteTokenByUserId(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.DELETE_FAIL));
	}

}
