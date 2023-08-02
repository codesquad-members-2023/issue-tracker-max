package codesquard.app.issue.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.fixture.FixtureFactory;
import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
import codesquard.app.milestone.repository.MilestoneRepository;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;

@Transactional
class JdbcIssueRepositoryTest extends IntegrationTestSupport {

	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MilestoneRepository milestoneRepository;
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

	@DisplayName("이슈를 등록하고 그 등록 번호를 반환한다.")
	@Test
	void save() {
		// given
		User user = new User(null, "wis", "wis@abcd.com", "code1234", null);
		Long loginId = userRepository.save(user);
		MilestoneSaveRequest milestoneSaveRequest = FixtureFactory.createMilestoneCreateRequest("레포지토리");
		Long milestoneId = milestoneRepository.save(MilestoneSaveRequest.toEntity(milestoneSaveRequest)).orElseThrow();
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Repository", milestoneId);

		Issue issue = issueSaveRequest.toEntity(loginId);

		// when
		Long id = issueRepository.save(issue);
		for (Long labelId : issueSaveRequest.getLabels()) {
			issueRepository.saveIssueLabel(id, labelId);
		}
		for (Long assigneeId : issueSaveRequest.getAssignees()) {
			issueRepository.saveIssueAssignee(id, assigneeId);
		}

		// then
		assertThat(id).isNotNull();
	}
}
