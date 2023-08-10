package codesquard.app.user_reaction.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.comment.entity.Comment;
import codesquard.app.comment.repository.CommentRepository;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.fixture.FixtureFactory;
import codesquard.app.issue.repository.IssueRepository;
import codesquard.app.user.repository.UserRepository;

class JdbcUserReactionRepositoryTest extends IntegrationTestSupport {

	@Autowired
	private UserReactionRepository userReactionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private CommentRepository commentRepository;
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
		jdbcTemplate.update("TRUNCATE TABLE user_reaction");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@DisplayName("이슈 사용자 반응을 등록하고 그 등록 번호를 반환한다.")
	@Test
	void saveIssue() {
		// given
		Long loginId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Repository", "내용", null,
			loginId);
		Issue issue = issueSaveRequest.toEntity(loginId);
		Long issueId = issueRepository.save(issue);
		issueRepository.saveIssueLabel(issueId, issueSaveRequest.getLabels());
		issueRepository.saveIssueAssignee(issueId, issueSaveRequest.getAssignees());
		Long reactionId = 1L;

		// when
		Long id = userReactionRepository.saveIssueReaction(reactionId, loginId, issueId);

		// then
		assertThat(id).isNotNull();
	}

	@DisplayName("댓글 사용자 반응을 등록하고 그 등록 번호를 반환한다.")
	@Test
	void saveComment() {
		// given
		Long loginId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Repository", "내용", null,
			loginId);
		Issue issue = issueSaveRequest.toEntity(loginId);
		Long issueId = issueRepository.save(issue);
		issueRepository.saveIssueLabel(issueId, issueSaveRequest.getLabels());
		issueRepository.saveIssueAssignee(issueId, issueSaveRequest.getAssignees());
		Long reactionId = 1L;
		Long commentId = commentRepository.save(new Comment(issueId, loginId, "댓글", LocalDateTime.now()));

		// when
		Long id = userReactionRepository.saveCommentReaction(reactionId, loginId, commentId);

		// then
		assertThat(id).isNotNull();
	}
}