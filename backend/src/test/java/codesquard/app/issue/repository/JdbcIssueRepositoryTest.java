package codesquard.app.issue.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.dto.response.IssueReadResponse;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.issue.fixture.FixtureFactory;
import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
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
		jdbcTemplate.update("TRUNCATE TABLE comment");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@DisplayName("이슈를 등록하고 그 등록 번호의 이슈를 조회한다.")
	@Test
	void getDetail() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long issueId = createIssue(userId);

		// when
		IssueReadResponse issueReadResponse = issueRepository.findBy(issueId);

		// then
		assertThat(issueReadResponse.getTitle()).isEqualTo("Repository");
		assertThat(issueRepository.findAssigneesBy(issueId).get(0).getLoginId()).isEqualTo("wis");
		assertThat(issueRepository.findLabelsBy(issueId)).isEmpty();
		assertThat(issueRepository.findCommentsBy(issueId, userId)).isEmpty();
	}

	@DisplayName("이슈를 등록하고 그 등록 번호를 반환한다.")
	@Test
	void save() {
		// given
		Long loginId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		MilestoneSaveRequest milestoneSaveRequest = FixtureFactory.createMilestoneCreateRequest("레포지토리");
		Long milestoneId = milestoneRepository.save(MilestoneSaveRequest.toEntity(milestoneSaveRequest)).orElseThrow();

		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Repository", "내용", milestoneId,
			loginId);
		Issue issue = issueSaveRequest.toEntity(loginId);

		// when
		Long id = issueRepository.save(issue);
		issueRepository.saveIssueLabel(id, issueSaveRequest.getLabels());
		issueRepository.saveIssueAssignee(id, issueSaveRequest.getAssignees());

		// then
		assertThat(id).isNotNull();
	}

	@DisplayName("이슈들을 등록하고 그 등록 번호의 이슈 상태들을 수정한다.")
	@Test
	void modifyStatuses() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id1 = createIssue(userId);
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", null,
			userId);
		Issue issue = issueSaveRequest.toEntity(userId);
		Long id2 = issueRepository.save(issue);
		List<Long> issueId = List.of(id1, id2);

		String issueStatus = "CLOSED";

		// when
		LocalDateTime status_modified_at = LocalDateTime.now().withNano(0);
		issueRepository.modifyStatuses(issueStatus, issueId, status_modified_at);

		// then
		assertThat(issueRepository.findBy(id1).getStatus()).isEqualTo(IssueStatus.valueOf(issueStatus));
		assertThat(issueRepository.findBy(id1).getStatusModifiedAt()).isEqualTo(status_modified_at);
		assertThat(issueRepository.findBy(id2).getStatus()).isEqualTo(IssueStatus.valueOf(issueStatus));
	}

	@DisplayName("이슈를 등록하고 그 등록 번호의 이슈 제목을 수정한다.")
	@Test
	void modifyTitle() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id = createIssue(userId);

		// when
		String title = "Modified Repository Title";
		LocalDateTime modified_at = LocalDateTime.now().withNano(0);
		issueRepository.modifyTitle(title, id, modified_at);

		// then
		assertThat(issueRepository.findBy(id).getTitle()).isEqualTo(title);
		assertThat(issueRepository.findBy(id).getModifiedAt()).isEqualTo(modified_at);
	}

	@DisplayName("이슈를 등록하고 그 등록 번호의 이슈 내용을 수정한다.")
	@Test
	void modifyContent() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id = createIssue(userId);

		// when
		String content = "Modified Repository Content";
		LocalDateTime modified_at = LocalDateTime.now().withNano(0);
		issueRepository.modifyContent(content, id, modified_at);

		// then
		assertThat(issueRepository.findBy(id).getContent()).isEqualTo(content);
		assertThat(issueRepository.findBy(id).getModifiedAt()).isEqualTo(modified_at);
	}

	@DisplayName("이슈를 등록하고 그 등록 번호의 마일스톤을 수정한다.")
	@Test
	void modifyMilestone() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id = createIssue(userId);

		// when
		issueRepository.modifyMilestone(null, id);

		// then
		assertThat(issueRepository.findBy(id).getMilestone()).isNull();
	}

	@DisplayName("이슈를 등록하고 삭제한다.")
	@Test
	void deleteIssue() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id = createIssue(userId);

		// when
		issueRepository.deleteBy(id);

		// then
		assertThat(issueRepository.isExist(id)).isFalse();
	}

	private Long createIssue(Long userId) {
		MilestoneSaveRequest milestoneSaveRequest = FixtureFactory.createMilestoneCreateRequest("레포지토리");
		Long milestoneId = milestoneRepository.save(MilestoneSaveRequest.toEntity(milestoneSaveRequest)).orElseThrow();
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Repository", "내용", milestoneId,
			userId);
		Issue issue = issueSaveRequest.toEntity(userId);

		Long id = issueRepository.save(issue);
		issueRepository.saveIssueLabel(id, issueSaveRequest.getLabels());
		issueRepository.saveIssueAssignee(id, issueSaveRequest.getAssignees());
		return id;
	}
}
