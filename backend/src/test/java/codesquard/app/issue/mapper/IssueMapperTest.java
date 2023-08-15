package codesquard.app.issue.mapper;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.comment.entity.Comment;
import codesquard.app.comment.repository.CommentRepository;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.mapper.request.IssueFilterRequest;
import codesquard.app.issue.mapper.response.IssuesResponse;
import codesquard.app.issue.repository.IssueRepository;
import codesquard.app.label.entity.Label;
import codesquard.app.label.repository.LabelRepository;
import codesquard.app.milestone.entity.Milestone;
import codesquard.app.milestone.repository.MilestoneRepository;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;

class IssueMapperTest extends IntegrationTestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private IssueMapper issueMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IssueRepository issueRepository;

	@Autowired
	private MilestoneRepository milestoneRepository;

	@Autowired
	private LabelRepository labelRepository;

	@BeforeEach
	void setUp() {
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
		jdbcTemplate.update("TRUNCATE TABLE comment");
		jdbcTemplate.update("TRUNCATE TABLE issue_assignee");
		jdbcTemplate.update("TRUNCATE TABLE issue_label");
		jdbcTemplate.update("TRUNCATE TABLE label");
		jdbcTemplate.update("TRUNCATE TABLE issue");
		jdbcTemplate.update("TRUNCATE TABLE milestone");
		jdbcTemplate.update("TRUNCATE TABLE user");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@DisplayName("요청 파라미터가 없는 경우 'is:opened' 조건에 맞는 목록을 반환한다.")
	@Test
	void getIssuesWithNoParameter() {
		// given
		createUsersFixture();
		createMilestonesFixture();
		createIssuesFixture();

		IssueFilterRequest issueFilterRequest = new IssueFilterRequest();

		// when
		final List<IssuesResponse> issues = issueMapper.getIssues(issueFilterRequest);

		// then
		assertThat(issues).hasSize(3);
	}

	@DisplayName("'author:user' 조건에 맞는 목록을 반환한다.")
	@Test
	void getIssuesWithAuthorFilter() {
		// given
		createUsersFixture();
		createMilestonesFixture();
		createIssuesFixture();

		IssueFilterRequest issueFilterRequest = new IssueFilterRequest();
		issueFilterRequest.setAuthor("user1");

		// when
		final List<IssuesResponse> issues = issueMapper.getIssues(issueFilterRequest);

		// then
		assertThat(issues).hasSize(1);
	}

	@DisplayName("'assignee:user' 조건에 맞는 목록을 반환한다.")
	@CsvSource(value = {"user1,1", "user2,2", "user3,3"})
	@ParameterizedTest
	void getIssuesWithAssigneeFilter(String loginId, int count) {
		// given
		createUsersFixture();
		createMilestonesFixture();
		createIssuesFixture();
		createIssuesAssigneesFixture();

		IssueFilterRequest issueFilterRequest = new IssueFilterRequest();
		issueFilterRequest.setAssignee(loginId);

		// when
		final List<IssuesResponse> issues = issueMapper.getIssues(issueFilterRequest);

		// then
		assertThat(issues).hasSize(count);
	}

	@DisplayName("'label:name' 조건에 맞는 목록을 반환한다.")
	@CsvSource(value = {"feature,1", "docs,2", "bug,3"})
	@ParameterizedTest
	void getIssuesWithLabelFilter(String label, int count) {
		// given
		createUsersFixture();
		createMilestonesFixture();
		createIssuesFixture();
		createLabelsFixture();
		createIssuesLabelsFixture();

		IssueFilterRequest issueFilterRequest = new IssueFilterRequest();
		issueFilterRequest.setLabel(List.of(label));

		// when
		final List<IssuesResponse> issues = issueMapper.getIssues(issueFilterRequest);

		// then
		assertThat(issues).hasSize(count);
	}

	@DisplayName("'label:name' 다중 조건에 맞는 목록을 반환한다.")
	@Test
	void getIssuesWithMultiLabel() {
		// given
		createUsersFixture();
		createMilestonesFixture();
		createIssuesFixture();
		createLabelsFixture();
		createIssuesLabelsFixture();

		IssueFilterRequest issueFilterRequest = new IssueFilterRequest();
		issueFilterRequest.setLabel(List.of("docs", "bug"));

		// when
		final List<IssuesResponse> issues = issueMapper.getIssues(issueFilterRequest);

		// then
		assertThat(issues).hasSize(2);
	}

	@DisplayName("'milestone:name' 조건에 맞는 목록을 반환한다.")
	@CsvSource(value = {"milestone1,1", "milestone2,2"})
	@ParameterizedTest
	void getIssuesWithMilestoneFilter(String milestone, int count) {
		// given
		createUsersFixture();
		createMilestonesFixture();
		createIssuesFixture();

		IssueFilterRequest issueFilterRequest = new IssueFilterRequest();
		issueFilterRequest.setMilestone(milestone);

		// when
		final List<IssuesResponse> issues = issueMapper.getIssues(issueFilterRequest);

		// then
		assertThat(issues).hasSize(count);
	}

	@DisplayName("'mentions:name' 조건에 맞는 목록을 반환한다.")
	@CsvSource(value = {"user1,2", "user2,2", "user3,1"})
	@ParameterizedTest
	void getIssuesWithMention(String loginId, int count) {
		// given
		createUsersFixture();
		createMilestonesFixture();
		createIssuesFixture();
		createCommentsFixture();

		IssueFilterRequest issueFilterRequest = new IssueFilterRequest();
		issueFilterRequest.setMentions(loginId);

		// when
		final List<IssuesResponse> issues = issueMapper.getIssues(issueFilterRequest);

		// then
		assertThat(issues).hasSize(count);
	}

	private void createUsersFixture() {
		User user1 = new User(null, "user1", "user1@email.com", "password1000", "url path");
		userRepository.save(user1);

		User user2 = new User(null, "user2", "user2@email.com", "password1000", "url path");
		userRepository.save(user2);

		User user3 = new User(null, "user3", "user3@email.com", "password1000", "url path");
		userRepository.save(user3);
	}

	private void createMilestonesFixture() {
		Milestone milestone1 = new Milestone("milestone1", "week 1", null);
		milestoneRepository.save(milestone1);

		Milestone milestone2 = new Milestone("milestone2", "week 2", null);
		milestoneRepository.save(milestone2);
	}

	private void createIssuesFixture() {
		Issue issue1 = new Issue(1L, 1L, "test issue", "hello");
		issueRepository.save(issue1);

		Issue issue2 = new Issue(2L, 2L, "test issue", "hello");
		issueRepository.save(issue2);

		Issue issue3 = new Issue(2L, 3L, "test issue", "hello");
		issueRepository.save(issue3);
	}

	private void createLabelsFixture() {
		Label label1 = new Label("feature", "light", "#000000", "feature label");
		labelRepository.save(label1);

		Label label2 = new Label("docs", "light", "#000000", "docs label");
		labelRepository.save(label2);

		Label label3 = new Label("bug", "light", "#000000", "bugs label");
		labelRepository.save(label3);
	}

	private void createIssuesLabelsFixture() {
		issueRepository.saveIssueLabel(1L, List.of(1L, 2L, 3L));
		issueRepository.saveIssueLabel(2L, List.of(2L, 3L));
		issueRepository.saveIssueLabel(3L, List.of(3L));
	}

	private void createIssuesAssigneesFixture() {
		issueRepository.saveIssueAssignee(1L, List.of(1L, 2L, 3L));
		issueRepository.saveIssueAssignee(2L, List.of(2L, 3L));
		issueRepository.saveIssueAssignee(3L, List.of(3L));
	}

	private void createCommentsFixture() {
		LocalDateTime createdAt = LocalDateTime.of(2023, 9, 1, 16, 0);

		Comment comment1 = new Comment(1L, 1L, "Create New Comment", createdAt);
		commentRepository.save(comment1);

		Comment comment2 = new Comment(1L, 2L, "Create New Comment", createdAt);
		commentRepository.save(comment2);

		Comment comment3 = new Comment(1L, 3L, "Create New Comment", createdAt);
		commentRepository.save(comment3);

		Comment comment4 = new Comment(2L, 1L, "Create New Comment", createdAt);
		commentRepository.save(comment4);

		Comment comment5 = new Comment(3L, 2L, "Create New Comment", createdAt);
		commentRepository.save(comment5);
	}

}
