package codesquard.app.issue.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.api.errors.exception.IllegalIssueStatusException;
import codesquard.app.api.errors.exception.NoSuchIssueException;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.issue.dto.request.IssueModifyAssigneesRequest;
import codesquard.app.issue.dto.request.IssueModifyContentRequest;
import codesquard.app.issue.dto.request.IssueModifyLabelsRequest;
import codesquard.app.issue.dto.request.IssueModifyMilestoneRequest;
import codesquard.app.issue.dto.request.IssueModifyStatusesRequest;
import codesquard.app.issue.dto.request.IssueModifyTitleRequest;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.dto.response.IssueLabelResponse;
import codesquard.app.issue.dto.response.IssueReadResponse;
import codesquard.app.issue.fixture.FixtureFactory;
import codesquard.app.issue.repository.IssueRepository;
import codesquard.app.label.dto.request.LabelSaveRequest;
import codesquard.app.label.service.LabelService;
import codesquard.app.milestone.service.MilestoneService;
import codesquard.app.user.repository.UserRepository;
import codesquard.app.user.service.UserService;

class IssueServiceTest extends IntegrationTestSupport {

	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private IssueService issueService;
	@Autowired
	private IssueQueryService issueQueryService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MilestoneService milestoneService;
	@Autowired
	private LabelService labelService;
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
		jdbcTemplate.update("TRUNCATE TABLE label");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@DisplayName("이슈의 상세 내용을 조회한다.")
	@Test
	void getDetail() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id = createIssue(userId);

		// when
		IssueReadResponse issueReadResponse = issueQueryService.get(id, userId);

		// then
		assertThat(issueReadResponse.getTitle()).isEqualTo("Service");
		assertThat(issueReadResponse.getContent()).isEqualTo("내용");
	}

	@DisplayName("이슈를 등록한다.")
	@Test
	void create() {
		// given
		Long loginId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long milestoneId = milestoneService.saveMilestone(FixtureFactory.createMilestoneCreateRequest("서비스"));
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", milestoneId,
			loginId);

		// when
		Long id = issueService.save(issueSaveRequest, loginId);

		// then
		assertThat(id).isNotNull();
	}

	@DisplayName("이슈들의 상태를 수정한다.")
	@Test
	void modifyStatuses() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id1 = createIssue(userId);
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", null,
			userId);
		Long id2 = issueService.save(issueSaveRequest, userId);
		List<Long> issueId = List.of(id1, id2);

		String issueStatus = "CLOSED";
		IssueModifyStatusesRequest issueModifyStatusesRequest = new IssueModifyStatusesRequest(issueId, issueStatus);

		// when
		issueService.modifyStatuses(issueModifyStatusesRequest, userId);

		// then
		assertThat(issueQueryService.findById(id1).getStatus().name()).isEqualTo(issueStatus);
		assertThat(issueQueryService.findById(id2).getStatus().name()).isEqualTo(issueStatus);
	}

	@DisplayName("이슈들의 상태를 수정시 이슈가 없다면 400 에러를 반환한다.")
	@Test
	void modifyStatuses_IfNoIssue_Response400() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		List<Long> issueId = List.of(0L);

		String issueStatus = "CLOSED";
		IssueModifyStatusesRequest issueModifyStatusesRequest = new IssueModifyStatusesRequest(issueId, issueStatus);

		// when & then
		assertThatThrownBy(
			() -> issueService.modifyStatuses(issueModifyStatusesRequest, userId)).isInstanceOf(
			NoSuchIssueException.class);
	}

	@DisplayName("이슈들의 상태 수정시 유효하지 않은 status 값이 오면 400 에러를 반환한다.")
	@Test
	void modifyInvalidStatuses_Response400() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id1 = createIssue(userId);
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", null,
			userId);
		Long id2 = issueService.save(issueSaveRequest, userId);
		List<Long> issueId = List.of(id1, id2);

		String invalidIssueStatus = "OPEN";
		IssueModifyStatusesRequest issueModifyStatusesRequest = new IssueModifyStatusesRequest(issueId,
			invalidIssueStatus);

		// when & then
		assertThatThrownBy(
			() -> issueService.modifyStatuses(issueModifyStatusesRequest, userId)).isInstanceOf(
			IllegalIssueStatusException.class);
	}

	@DisplayName("이슈들의 상태를 수정할 때 하나의 이슈 작성자와 유저가 다를 시 403에러를 반환한다.")
	@Test
	void modifyStatuses_IfNotSameAuthor_Response403() {
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id1 = createIssue(userId);
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", null,
			userId);
		Long id2 = issueService.save(issueSaveRequest, userId);
		List<Long> issueId = List.of(id1, id2);

		String issueStatus = "CLOSED";
		IssueModifyStatusesRequest issueModifyStatusesRequest = new IssueModifyStatusesRequest(issueId, issueStatus);

		// when & then
		assertThatThrownBy(
			() -> issueService.modifyStatuses(issueModifyStatusesRequest, 0L)).isInstanceOf(
			RestApiException.class);
	}

	@DisplayName("이슈 제목을 수정한다.")
	@Test
	void modifyTitle() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id = createIssue(userId);

		String title = "modified Service title";
		IssueModifyTitleRequest issueModifyTitleRequest = new IssueModifyTitleRequest(title);

		// when
		issueService.modifyTitle(issueModifyTitleRequest, id, userId);

		// then
		assertThat(issueQueryService.findById(id).getTitle()).isEqualTo(title);
	}

	@DisplayName("이슈 내용을 수정한다.")
	@Test
	void modifyContent() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id = createIssue(userId);

		String content = "modified Service content";
		IssueModifyContentRequest issueModifyContentRequest = new IssueModifyContentRequest(content);

		// when
		issueService.modifyContent(issueModifyContentRequest, id, userId);

		// then
		assertThat(issueQueryService.findById(id).getContent()).isEqualTo(content);
	}

	@DisplayName("이슈 마일스톤을 수정한다.")
	@Test
	void modifyMilestone() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long milestoneId1 = milestoneService.saveMilestone(FixtureFactory.createMilestoneCreateRequest("서비스"));
		Long milestoneId2 = milestoneService.saveMilestone(FixtureFactory.createMilestoneCreateRequest("수정된 마일스톤"));
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", milestoneId1,
			userId);
		Long id = issueService.save(issueSaveRequest, userId);

		IssueModifyMilestoneRequest issueModifyMilestoneRequest = new IssueModifyMilestoneRequest(milestoneId2);

		// when
		issueService.modifyMilestone(issueModifyMilestoneRequest, id, userId);

		// then
		assertThat(issueQueryService.findById(id).getMilestoneId()).isEqualTo(milestoneId2);
	}

	@DisplayName("이슈 책임자를 수정한다.")
	@Test
	void modifyAssignees() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id = createIssue(userId);

		IssueModifyAssigneesRequest issueModifyAssigneesRequest = new IssueModifyAssigneesRequest(List.of());

		// when
		issueService.modifyAssignees(issueModifyAssigneesRequest, id, userId);

		// then
		assertThat(issueQueryService.findAssigneesById(id)).isEmpty();
	}

	@DisplayName("이슈 라벨을 수정한다.")
	@Test
	void modifyLabels() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long milestoneId = milestoneService.saveMilestone(FixtureFactory.createMilestoneCreateRequest("서비스"));
		String name = "feat";
		String color = "light";
		String background = "#111111";
		Long labelId = labelService.saveLabel(new LabelSaveRequest(name, color, background, "기능 구현"));
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", milestoneId,
			userId);
		Long id = issueService.save(issueSaveRequest, userId);

		IssueModifyLabelsRequest issueModifyLabelsRequest = new IssueModifyLabelsRequest(List.of(labelId));

		// when
		issueService.modifyLabels(issueModifyLabelsRequest, id, userId);

		// then
		List<IssueLabelResponse> label = issueQueryService.findLabelsById(id);
		assertThat(label.get(0).getName()).isEqualTo(name);
		assertThat(label.get(0).getBackground()).isEqualTo(background);
		assertThat(label.get(0).getColor().getNameToLowerCase()).isEqualTo(color);
	}

	@DisplayName("이슈 라벨이 없도록 수정한다.")
	@Test
	void modifyLabels_Null() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id = createIssue(userId);

		IssueModifyLabelsRequest issueModifyLabelsRequest = new IssueModifyLabelsRequest(List.of());

		// when
		issueService.modifyLabels(issueModifyLabelsRequest, id, userId);

		// then
		List<IssueLabelResponse> label = issueQueryService.findLabelsById(id);
		assertThat(label).isEmpty();
	}

	@DisplayName("이슈를 삭제하고 같은 이슈를 삭제하면 NoSuchIssueException 발생한다.")
	@Test
	void delete() {
		// given
		Long userId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long id = createIssue(userId);

		// when
		issueService.delete(id, userId);

		// then
		assertThatThrownBy(() -> issueService.delete(id, userId)).isInstanceOf(NoSuchIssueException.class);
	}

	@DisplayName("이슈를 삭제할 때 이슈 작성자와 유저가 다를 시 403에러를 반환한다.")
	@Test
	void delete_IfNotSameAuthor_Response403() {
		// given
		Long authorId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long userId = 0L;
		Long id = createIssue(authorId);

		// when & then
		assertThatThrownBy(() -> issueService.delete(id, userId)).isInstanceOf(RestApiException.class);
	}

	private Long createIssue(Long userId) {
		Long milestoneId = milestoneService.saveMilestone(FixtureFactory.createMilestoneCreateRequest("서비스"));
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", milestoneId,
			userId);
		return issueService.save(issueSaveRequest, userId);
	}
}
