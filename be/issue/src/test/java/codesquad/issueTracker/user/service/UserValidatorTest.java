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
import codesquad.issueTracker.user.domain.LoginType;
import codesquad.issueTracker.user.domain.User;
import codesquad.issueTracker.user.dto.LoginRequestDto;
import codesquad.issueTracker.user.dto.SignUpRequestDto;
import codesquad.issueTracker.user.repository.UserRepository;

@ServiceTest
public class UserValidatorTest {
	@InjectMocks
	UserValidator userValidator;
	@Mock
	UserRepository userRepository;

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

		assertDoesNotThrow(() -> findUser.validateLoginUser(loginRequestDto));
	}

	@DisplayName("로그인 실패 : request 의 email이 db에 findUser의 이메일과 일치하지 않거나 request의 pw 가 복호화된 findUser의 pw가 같지 않을때 예외발생   ")
	@Test
	void validateLoginUserFailed() {

		User user = User.builder()
			.id(1L)
			.email("asdfff123@ddd.com")
			.password(BCrypt.hashpw("12345678", BCrypt.gensalt()))
			.loginType(LoginType.LOCAL)
			.build();

		given(userRepository.findByEmail("asd123@dddd.com")).willReturn(Optional.of(user));
		User findUser = userRepository.findByEmail("asd123@dddd.com").orElseThrow();
		LoginRequestDto loginRequestDto = new LoginRequestDto("asd123@dddd.com", "123dd45678");

		assertThrows(CustomException.class, () -> {
			findUser.validateLoginUser(loginRequestDto);
		});
	}

	@DisplayName("로그인 실패 : 로그인 타입이 같으면 로그인 실패 예외발생하지 않음")
	@Test
	public void validateLoginTypeSuccess() {
		// given
		LoginType input = LoginType.LOCAL;
		User existUser = User.builder()
			.id(1L)
			.email("asd123@ddd.com")
			.password(BCrypt.hashpw("12345678", BCrypt.gensalt()))
			.loginType(LoginType.LOCAL)
			.build();
		given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(existUser));
		LoginType existUserLoginType = userRepository.findByEmail(existUser.getEmail()).get().getLoginType();
		assertDoesNotThrow(() -> {
				userValidator.validateLoginType(input, existUserLoginType);
		});

	}

	@DisplayName("로그인 실패 : 로그인 타입이 다르면 로그인 실패 예외 발생")
	@Test
	public void validateLoginTypeFailed() {
		// given
		LoginType input = LoginType.GITHUB;
		User existUser = User.builder()
			.id(1L)
			.email("asd123@ddd.com")
			.password(BCrypt.hashpw("12345678", BCrypt.gensalt()))
			.loginType(LoginType.LOCAL)
			.build();
		given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(existUser));
		LoginType existUserLoginType = userRepository.findByEmail(existUser.getEmail()).get().getLoginType();
		assertThrows(CustomException.class, () -> {
				userValidator.validateLoginType(input, existUserLoginType);
		});

	}

	@DisplayName("회원가입 실패 : email로 user 를 찾을 때 반환값이 없는 경우 예외발생하지 않음 ")
	@Test
	public void validateDuplicatedEmailSuccess() {
		SignUpRequestDto signUpRequestDto = new SignUpRequestDto("asdd1d23@ddd.com", "12345678", "감귤감귤감귤");
		User existUser = User.builder()
			.id(1L)
			.email("asd1d2dfsa3@ddd.com")
			.password(BCrypt.hashpw(signUpRequestDto.getPassword(), BCrypt.gensalt()))
			.build();
		given(userRepository.findByEmail(any())).willReturn(Optional.empty());

		assertDoesNotThrow(() -> {
				userValidator.validateDuplicatedEmail(signUpRequestDto);
		});
	}

	@DisplayName("회원가입 실패 : 중복된 이메일 입력할 경우 예외발생 ")
	@Test
	public void validateDuplicatedEmailFailed() {
		SignUpRequestDto signUpRequestDto = new SignUpRequestDto("asd123@ddd.com", "12345678", "감귤감귤감귤");
		User existUser = User.builder()
			.id(1L)
			.email(signUpRequestDto.getEmail())
			.password(BCrypt.hashpw(signUpRequestDto.getPassword(), BCrypt.gensalt()))
			.build();
		given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(existUser));

		assertThrows(CustomException.class, () -> {
				userValidator.validateDuplicatedEmail(signUpRequestDto);
		});
	}

}
