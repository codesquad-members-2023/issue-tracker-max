package codesquard.app.user.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
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
			"hong1234", null);
		// when
		UserSaveResponse userSaveResponse = userService.signUp(userSaveServiceRequest);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(userSaveResponse).isNotNull();
			softAssertions.assertThat(userSaveResponse).extracting("success").isEqualTo(true);
			softAssertions.assertAll();
		});
	}

}
