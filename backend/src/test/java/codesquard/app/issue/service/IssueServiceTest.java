package codesquard.app.issue.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.issue.dto.request.IssueRegisterRequest;
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

	@DisplayName("이슈를 등록한다.")
	@Test
	void create() {
		// given
		String userId = "wisdom";
		String email = "wisdom@abcd.com";
		userService.signUp(FixtureFactory.createUserSIgnUpRequest(userId, email));
		milestoneService.create(FixtureFactory.createMilestoneCreateRequest("서비스"));
		Long loginId = 1L;
		IssueRegisterRequest issueRegisterRequest = FixtureFactory.createIssueRegisterRequest("Service");

		// when
		Long id = issueService.register(issueRegisterRequest, loginId);

		// then
		assertThat(id).isNotNull();
	}
}
