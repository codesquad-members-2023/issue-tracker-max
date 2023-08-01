package codesquard.app.issue.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.fixture.FixtureFactory;
import codesquard.app.issue.repository.IssueRepository;
import codesquard.app.milestone.service.MilestoneService;
import codesquard.app.user.service.UserService;

class IssueServiceTest extends IntegrationTestSupport {

	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private IssueService issueService;
	@Autowired
	private UserService userService;
	@Autowired
	private MilestoneService milestoneService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
		jdbcTemplate.update("TRUNCATE TABLE issue_assignee");
		jdbcTemplate.update("TRUNCATE TABLE issue_label");
		jdbcTemplate.update("TRUNCATE TABLE issue");
		jdbcTemplate.update("TRUNCATE TABLE milestone");
		jdbcTemplate.update("TRUNCATE TABLE user");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@DisplayName("이슈를 등록한다.")
	@Test
	void create() {
		// given
		Long loginId = userService.save(FixtureFactory.createUserSaveRequest());
		Long milestoneId = milestoneService.save(FixtureFactory.createMilestoneCreateRequest("서비스"));
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", milestoneId);

		// when
		Long id = issueService.save(issueSaveRequest, loginId);

		// then
		assertThat(id).isNotNull();
	}
}
