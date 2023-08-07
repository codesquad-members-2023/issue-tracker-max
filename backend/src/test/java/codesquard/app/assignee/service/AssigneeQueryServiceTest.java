package codesquard.app.assignee.service;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.assignee.service.response.AssigneeReadResponse;
import codesquard.app.user.service.UserService;
import codesquard.app.user.service.request.UserSaveServiceRequest;

class AssigneeQueryServiceTest extends IntegrationTestSupport {

	@Autowired
	private AssigneeQueryService assigneeQueryService;

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
		jdbcTemplate.update("TRUNCATE TABLE authenticate_user");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@Transactional
	@Test
	@DisplayName("모든 할당인을 조회합니다.")
	public void findAll() {
		// sample
		userService.signUp(new UserSaveServiceRequest("hong1234", "hong1234@gmail.com", "hong1234", "hong1234", null));
		// when
		List<AssigneeReadResponse> assignees = assigneeQueryService.findAll();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(assignees.size()).isEqualTo(1);
			softAssertions.assertAll();
		});
	}
}
