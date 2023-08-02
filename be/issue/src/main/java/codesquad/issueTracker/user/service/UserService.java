package codesquad.issueTracker.user.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.jwt.domain.Jwt;
import codesquad.issueTracker.jwt.domain.Token;
import codesquad.issueTracker.jwt.dto.RequestRefreshTokenDto;
import codesquad.issueTracker.jwt.service.JwtProvider;
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
	private final JwtProvider jwtProvider;

	public Long save(SignUpRequestDto userSignUpRequestDto) {
		validateDuplicatedEmail(userSignUpRequestDto);
		String encodedPassword = BCrypt.hashpw(userSignUpRequestDto.getPassword(), BCrypt.gensalt());
		User user = SignUpRequestDto.toEntity(userSignUpRequestDto, encodedPassword);
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

	/**
	 * 1. 만료된 리프레시 토큰이면 예외처리
	 * 2. DB에 없는 리프레시 토큰이면 예
	 */
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
