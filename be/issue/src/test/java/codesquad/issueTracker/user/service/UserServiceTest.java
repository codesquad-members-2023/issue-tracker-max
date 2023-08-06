package codesquad.issueTracker.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Map;
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
import codesquad.issueTracker.user.domain.User;
import codesquad.issueTracker.user.dto.LoginRequestDto;
import codesquad.issueTracker.user.repository.UserRepository;

@ServiceTest
class UserServiceTest {
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	// private final JwtProvider jwtProvider = new JwtProvider(new JwtProperties());

	@Mock
	private JwtProvider jwtProvider;

	@Test
	void save() {

	}

	@Test
	void login() {
		// given
		User user = User.builder()
			.id(1L)
			.email("asd123@ddd.com")
			.password(BCrypt.hashpw("12345678", BCrypt.gensalt()))
			.build();
		given(userRepository.findByEmail("asd123@ddd.com")).willReturn(Optional.of(user));

		Token token = Token.builder()
			.id(1L)
			.userId(1L)
			.refreshToken("abc.def.ghi")
			.build();
		given(userRepository.findTokenByUserId(1L)).willReturn(Optional.of(token));

		given(userRepository.updateRefreshToken(any(), any())).willReturn(1);

		User findUser = userRepository.findByEmail("asd123@ddd.com").orElseThrow();
		Token findToken = userRepository.findTokenByUserId(1L).orElseThrow();

		given(jwtProvider.createJwt(any())).willReturn(new Jwt("accessToken", "abc.ddd.ggg"));
		Jwt jwt = jwtProvider.createJwt(Map.of("userId", findUser.getId()));

		LoginRequestDto loginRequestDto = new LoginRequestDto("asd123@ddd.com", "12345678");
		userService.validateLoginUser(loginRequestDto, findUser);
		int result = userRepository.updateRefreshToken(1L, jwt.getRefreshToken());

		assertThat(result).isEqualTo(1);

	}

	@DisplayName("로그인 성공 : request 의 email이 db에 findUser의 이메일과 일치하고 request의 pw 가 복호화된 findUser의 pw가 같을때   ")
	@Test
	void validateLoginUserSuccess() {
		// given
		User user = User.builder()
			.id(1L)
			.email("asd123@ddd.com")
			.password(BCrypt.hashpw("12345678", BCrypt.gensalt()))
			.build();

		given(userRepository.findByEmail("asd123@ddd.com")).willReturn(Optional.of(user));
		User findUser = userRepository.findByEmail("asd123@ddd.com").orElseThrow();
		LoginRequestDto loginRequestDto = new LoginRequestDto("asd123@ddd.com", "12345678");
		// when

		assertDoesNotThrow(() -> userService.validateLoginUser(loginRequestDto, findUser));
	}

	@DisplayName("로그인 실패 : request 의 email이 db에 findUser의 이메일과 일치하지 않거나 request의 pw 가 복호화된 findUser의 pw가 같지 않을때 예외발생   ")
	@Test
	void validateLoginUserFailed() {

		User user = User.builder()
			.id(1L)
			.email("asdfff123@ddd.com")
			.password(BCrypt.hashpw("12345678", BCrypt.gensalt()))
			.build();

		given(userRepository.findByEmail("asd123@ddd.com")).willReturn(Optional.of(user));
		User findUser = userRepository.findByEmail("asd123@ddd.com").orElseThrow();
		LoginRequestDto loginRequestDto = new LoginRequestDto("asd123@ddd.com", "123dd45678");

		assertThrows(CustomException.class, () -> {
			userService.validateLoginUser(loginRequestDto, findUser);
		});
	}

	@Test
	void validateDuplicatedEmail() {

	}
}