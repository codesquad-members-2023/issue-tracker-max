package codesquad.issueTracker.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import annotation.ServiceTest;
import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.jwt.domain.Jwt;
import codesquad.issueTracker.jwt.domain.Token;
import codesquad.issueTracker.jwt.util.JwtProvider;
import codesquad.issueTracker.user.domain.LoginType;
import codesquad.issueTracker.user.domain.User;
import codesquad.issueTracker.user.dto.LoginRequestDto;
import codesquad.issueTracker.user.dto.LoginResponseDto;
import codesquad.issueTracker.user.repository.UserRepository;

@ServiceTest
public class UserServiceTest {

	@InjectMocks
	UserService userService;
	@Mock
	UserRepository userRepository;
	@Mock
	UserValidator userValidator;
	@Mock
	JwtProvider jwtProvider;

	@DisplayName("로그인 성공")
	@Test
	void loginSuccess() {
		//given
		User mockUser = User.builder()
			.id(1L)
			.email("rkarbf@gmail.com")
			.password(BCrypt.hashpw("password", BCrypt.gensalt()))
			.loginType(LoginType.LOCAL)
			.build();

		Jwt mockJwt = new Jwt("accessToken", "refreshToken");

		given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));
		given(jwtProvider.createJwt(anyMap())).willReturn(mockJwt);

		LoginRequestDto loginRequestDto = new LoginRequestDto("rkarbf@gmail.com", "password");

		//when
		LoginResponseDto response = userService.login(loginRequestDto);

		//then
		assertAll(
			() -> assertEquals(mockUser.getId(), response.getUserId()),
			() -> assertEquals("accessToken", response.getAccessToken()),
			() -> assertEquals("refreshToken", response.getRefreshToken())
		);
	}

	@DisplayName("존재하지 않는 유저 로그인 실패")
	@Test
	void loginFailedByNotFoundUser() {
		given(userRepository.findByEmail(any())).willReturn(Optional.empty());
		LoginRequestDto loginRequestDto = new LoginRequestDto("rkarbf@gmail.com", "password");

		//when
		assertThrows(CustomException.class, () -> userService.login(loginRequestDto));
	}

	@DisplayName("비밀번호 불일치 로그인 실패")
	@Test
	void loginFailedByWrongPassword() {
		User mockUser = User.builder()
			.id(1L)
			.email("rkarbf@gmail.com")
			.password(BCrypt.hashpw("password", BCrypt.gensalt()))
			.loginType(LoginType.LOCAL)
			.build();

		given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));
		LoginRequestDto loginRequestDto = new LoginRequestDto("rkarbf@gmail.com", "passwor");

		//when
		assertThrows(CustomException.class, () -> userService.login(loginRequestDto));
	}

	@DisplayName("로그인타입 불일치 로그인 실패")
	@Test
	void loginFailedByLoginTypeDiff() {
		User mockUser = User.builder()
			.id(1L)
			.email("rkarbf@gmail.com")
			.password(BCrypt.hashpw("password", BCrypt.gensalt()))
			.loginType(LoginType.GITHUB)
			.build();

		given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));
		LoginRequestDto loginRequestDto = new LoginRequestDto("rkarbf@gmail.com", "password");

		//when
		assertThrows(CustomException.class, () -> userService.login(loginRequestDto));
	}

	@DisplayName("로그인 시 리프레쉬 토큰이 존재하지 않을 때 토큰을 생성하는지 테스트")
	@Test
	void loginWhenRefreshTokenIsNull() {
		User mockUser = User.builder()
			.id(1L)
			.email("rkarbf@gmail.com")
			.password(BCrypt.hashpw("password", BCrypt.gensalt()))
			.loginType(LoginType.LOCAL)
			.build();

		Jwt mockJwt = new Jwt("accessToken", "refreshToken");

		given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));
		given(jwtProvider.createJwt(anyMap())).willReturn(mockJwt);
		given(userRepository.findTokenByUserId(1L)).willReturn(Optional.empty());

		LoginRequestDto loginRequestDto = new LoginRequestDto("rkarbf@gmail.com", "password");
		userService.login(loginRequestDto);
		verify(userRepository, times(1)).insertRefreshToken(1L, mockJwt.getRefreshToken());
		verify(userRepository, times(0)).updateRefreshToken(1L, mockJwt.getRefreshToken());
	}

	@DisplayName("로그인 시 리프레쉬 토큰이 존재할 때 토큰을 업데이트하는지 테스트")
	@Test
	void loginWhenRefreshTokenIsExist() {
		User mockUser = User.builder()
			.id(1L)
			.email("rkarbf@gmail.com")
			.password(BCrypt.hashpw("password", BCrypt.gensalt()))
			.loginType(LoginType.LOCAL)
			.build();

		Jwt mockJwt = new Jwt("accessToken", "refreshToken");

		given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));
		given(jwtProvider.createJwt(anyMap())).willReturn(mockJwt);
		given(userRepository.findTokenByUserId(1L)).willReturn(Optional.of(new Token(1L, 1L, "dummy")));

		LoginRequestDto loginRequestDto = new LoginRequestDto("rkarbf@gmail.com", "password");
		userService.login(loginRequestDto);
		verify(userRepository, times(0)).insertRefreshToken(1L, mockJwt.getRefreshToken());
		verify(userRepository, times(1)).updateRefreshToken(1L, mockJwt.getRefreshToken());
	}
}
