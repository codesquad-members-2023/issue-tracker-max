package codesquard.app.user.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.errors.errorcode.UserErrorCode;
import codesquard.app.errors.exception.RestApiException;
import codesquard.app.user.service.request.UserSaveServiceRequest;
import codesquard.app.user.service.response.UserSaveResponse;

class UserServiceTest extends IntegrationTestSupport {
	@Autowired
	private UserService userService;

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
		UserSaveServiceRequest userSaveServiceRequest = new UserSaveServiceRequest("hong1234", "hong1234@gmail.com",
			"hong1234", "hong1234", null);
		// when
		UserSaveResponse userSaveResponse = userService.signUp(userSaveServiceRequest);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(userSaveResponse).isNotNull();
			softAssertions.assertThat(userSaveResponse).extracting("success").isEqualTo(true);
			softAssertions.assertAll();
		});
	}

	@Transactional
	@Test
	@DisplayName("서로 다른 비밀번호와 비밀번호확인이 주어지고 회원가입을 요청할때 예외가 발생합니다.")
	public void signUp_givenNotMatchPassword_whenSignUp_thenThrowException() {
		// given
		UserSaveServiceRequest userSaveServiceRequest = new UserSaveServiceRequest("hong1234", "hong1234@gmail.com",
			"hong1234", "hong4321", null);
		// when & then
		// satisfies() 메서드는 assertThatThrownBy()로 발생한 예외를 검사하고, 해당 예외의 조건을 추가로 검증할 수 있도록 합니다
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThatThrownBy(() -> userService.signUp(userSaveServiceRequest))
				.isInstanceOf(RestApiException.class)
				.satisfies(e -> {
					RestApiException restApiException = (RestApiException)e;
					softAssertions.assertThat(restApiException.getErrorCode())
						.isEqualTo(UserErrorCode.NOT_MATCH_PASSWORD);
				});
			softAssertions.assertAll();
		});
	}

	@Transactional
	@Test
	@DisplayName("중복된 아이디가 주어지고 회원가입을 요청할때 예외가 발생합니다.")
	public void signUp_givenDuplicatedLoginId_whenSignUp_thenThrowException() {
		// sample
		userService.signUp(new UserSaveServiceRequest("hong1234", "hong1234@gmail.com", "hong1234", "hong1234", null));
		// given
		UserSaveServiceRequest userSaveServiceRequest = new UserSaveServiceRequest("hong1234", "hong1234@gmail.com",
			"hong1234", "hong1234", null);
		// when & then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThatThrownBy(() -> userService.signUp(userSaveServiceRequest))
				.isInstanceOf(RestApiException.class)
				.satisfies(e -> {
					RestApiException restApiException = (RestApiException)e;
					softAssertions.assertThat(restApiException.getErrorCode())
						.isEqualTo(UserErrorCode.ALREADY_EXIST_LOGINID);
				});
			softAssertions.assertAll();
		});
	}

	@Transactional
	@Test
	@DisplayName("중복된 이메일이 주어지고 회원가입을 요청할때 예외가 발생합니다.")
	public void signUp_givenDuplicatedEmail_whenSignUp_thenThrowException() {
		// sample
		userService.signUp(new UserSaveServiceRequest("hong1234", "hong1234@gmail.com", "hong1234", "hong1234", null));
		// given
		UserSaveServiceRequest userSaveServiceRequest = new UserSaveServiceRequest("lee12345", "hong1234@gmail.com",
			"lee12345", "lee12345", null);
		// when & then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThatThrownBy(() -> userService.signUp(userSaveServiceRequest))
				.isInstanceOf(RestApiException.class)
				.satisfies(e -> {
					RestApiException restApiException = (RestApiException)e;
					softAssertions.assertThat(restApiException.getErrorCode())
						.isEqualTo(UserErrorCode.ALREADY_EXIST_EMAIL);
				});
			softAssertions.assertAll();
		});
	}

}
