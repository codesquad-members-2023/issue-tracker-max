package codesquard.app.issue.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.issue.dto.request.IssueRegisterRequest;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.fixture.FixtureFactory;
import codesquard.app.milestone.repository.MilestoneRepository;
import codesquard.app.user.repository.UserRepository;

@Transactional
class JdbcIssueRepositoryTest extends IntegrationTestSupport {

	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MilestoneRepository milestoneRepository;

	@DisplayName("이슈를 등록하고 그 등록 번호를 반환한다.")
	@Test
	void save() {
		// given
		String userId = "wis";
		String email = "wis@abcd.com";
		userRepository.save(FixtureFactory.createUserSIgnUpRequest(userId, email).toEntity());
		milestoneRepository.save(FixtureFactory.createMilestoneCreateRequest("레포지토리").toEntity());
		Long loginId = 1L;
		IssueRegisterRequest issueRegisterRequest = FixtureFactory.createIssueRegisterRequest("Repository");
		Issue issue = issueRegisterRequest.toEntity(loginId);

		// when
		Long id = issueRepository.save(issue);
		for (Long labelId : issueRegisterRequest.getLabels()) {
			issueRepository.saveIssueLabel(id, labelId);
		}
		for (Long assigneeId : issueRegisterRequest.getAssignees()) {
			issueRepository.saveIssueAssignee(id, assigneeId);
		}

		// then
		assertThat(id).isNotNull();
	}
}