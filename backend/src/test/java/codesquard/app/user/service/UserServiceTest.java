package codesquard.app.user.service;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.api.errors.errorcode.UserErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.user.fixture.FixedUserFactory;
import codesquard.app.user.service.request.UserLoginServiceRequest;
import codesquard.app.user.service.request.UserSaveServiceRequest;
import codesquard.app.user.service.response.UserSaveServiceResponse;

class UserServiceTest extends IntegrationTestSupport {
	@Autowired
	private UserService userService;

	@Autowired
	private UserQueryService userQueryService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
		jdbcTemplate.update("TRUNCATE TABLE comment");
		jdbcTemplate.update("TRUNCATE TABLE issue");
		jdbcTemplate.update("TRUNCATE TABLE milestone");
		jdbcTemplate.update("TRUNCATE TABLE user");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@Transactional
	@Test
	@DisplayName("회원가입 정보가 주어지고 회원가입을 요청할때 회원이 등록된다.")
	public void signUp() {
		// given
		UserSaveServiceRequest request = FixedUserFactory.userSaveServiceRequest();
		// when
		UserSaveServiceResponse response = userService.signUp(request);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response).isNotNull();
			softAssertions.assertThat(response).extracting("loginId").isEqualTo("hong1234");
		});

	}

	@Transactional
	@Test
	@DisplayName("서로 다른 비밀번호와 비밀번호확인이 주어지고 회원가입을 요청할때 예외가 발생합니다.")
	public void signUp_givenNotMatchPassword_whenSignUp_thenThrowException() {
		// given
		UserSaveServiceRequest request = FixedUserFactory.passwordNotMatchedUserSaveServiceRequest();
		// when & then
		// satisfies() 메서드는 assertThatThrownBy()로 발생한 예외를 검사하고, 해당 예외의 조건을 추가로 검증할 수 있도록 합니다
		assertThatThrownBy(() -> userService.signUp(request))
			.isInstanceOf(RestApiException.class)
			.satisfies(e -> assertThat(((RestApiException)e).getErrorCode())
				.isEqualTo(UserErrorCode.NOT_MATCH_PASSWORD));
	}

	@Transactional
	@Test
	@DisplayName("중복된 아이디가 주어지고 회원가입을 요청할때 예외가 발생합니다.")
	public void signUp_givenDuplicatedLoginId_whenSignUp_thenThrowException() {
		// sample
		userService.signUp(FixedUserFactory.userSaveServiceRequest());
		// given
		UserSaveServiceRequest request = FixedUserFactory.userSaveServiceRequest();
		// when & then
		assertThatThrownBy(() -> userService.signUp(request))
			.isInstanceOf(RestApiException.class)
			.satisfies(e -> assertThat(((RestApiException)e).getErrorCode())
				.isEqualTo(UserErrorCode.ALREADY_EXIST_LOGINID));
	}

	@Transactional
	@Test
	@DisplayName("중복된 이메일이 주어지고 회원가입을 요청할때 예외가 발생합니다.")
	public void signUp_givenDuplicatedEmail_whenSignUp_thenThrowException() {
		// sample
		userService.signUp(FixedUserFactory.userSaveServiceRequest());
		// given
		UserSaveServiceRequest request = FixedUserFactory.userSaveServiceRequest("lee1234");
		// when & then
		assertThatThrownBy(() -> userService.signUp(request))
			.isInstanceOf(RestApiException.class)
			.satisfies(e -> assertThat(((RestApiException)e).getErrorCode())
				.isEqualTo(UserErrorCode.ALREADY_EXIST_EMAIL));
	}

	@Test
	@DisplayName("유저 로그인 서비스 요청 객체가 주어지고 회원 확인을 요청할때 인증에 성공하여 인증 유저 객체를 반환합니다.")
	public void verifyUser_givenUserLoginServiceRequest_whenVerifyUser_thenReturnAuthenticateUser() {
		// sample
		userService.signUp(FixedUserFactory.userSaveServiceRequest());
		// given
		UserLoginServiceRequest request = FixedUserFactory.userLoginServiceRequest();
		// when
		AuthenticateUser authenticateUser = userQueryService.verifyUser(request);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(authenticateUser).isNotNull();
			softAssertions.assertThat(authenticateUser).extracting("loginId").isEqualTo("hong1234");
		});
	}
}
