package codesquard.app.user_reaction.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.api.errors.exception.NoSuchCommentException;
import codesquard.app.api.errors.exception.NoSuchIssueException;
import codesquard.app.api.errors.exception.NoSuchReactionException;
import codesquard.app.comment.service.CommentService;
import codesquard.app.comment.service.request.CommentSaveServiceRequest;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.fixture.FixtureFactory;
import codesquard.app.issue.service.IssueService;
import codesquard.app.user.repository.UserRepository;

class UserReactionServiceTest extends IntegrationTestSupport {

	@Autowired
	private UserReactionService userReactionService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IssueService issueService;
	@Autowired
	private CommentService commentService;
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
		jdbcTemplate.update("TRUNCATE TABLE reaction");
		jdbcTemplate.update(
			"INSERT INTO reaction(unicode) VALUE('&#128077'), ('&#128078'), ('&#128522'), ('&#128569'), ('&#128149'), ('&#128035')");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@DisplayName("이슈 사용자 반응을 등록한다.")
	@Test
	void createIssue() {
		// given
		Long loginId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", null, loginId);
		Long issueId = issueService.save(issueSaveRequest, loginId);
		Long reactionId = 1L;

		// when
		Long id = userReactionService.saveIssueReaction(reactionId, loginId, issueId);

		// then
		assertThat(id).isNotNull();
	}

	@DisplayName("반응이 존재하지 않아 이슈 사용자 반응 등록에 실패한다.")
	@Test
	void createIssue_NoSuchReactionException() {
		// given
		Long loginId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", null, loginId);
		Long issueId = issueService.save(issueSaveRequest, loginId);
		Long reactionId = 10000L;

		// when & then
		assertThatThrownBy(() -> userReactionService.saveIssueReaction(reactionId, loginId, issueId)).isInstanceOf(
			NoSuchReactionException.class);
	}

	@DisplayName("이슈가 존재하지 않아 이슈 사용자 반응 등록에 실패한다.")
	@Test
	void createIssue_NoSuchIssueException() {
		// given
		Long loginId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long issueId = 10000L;
		Long reactionId = 1L;

		// when & then
		assertThatThrownBy(() -> userReactionService.saveIssueReaction(reactionId, loginId, issueId)).isInstanceOf(
			NoSuchIssueException.class);
	}

	@DisplayName("댓글 사용자 반응을 등록한다.")
	@Test
	void createComment() {
		// given
		Long loginId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", null, loginId);
		Long issueId = issueService.save(issueSaveRequest, loginId);
		CommentSaveServiceRequest commentSaveServiceRequest = new CommentSaveServiceRequest(issueId, loginId, "댓글");
		Long commentId = commentService.save(commentSaveServiceRequest, LocalDateTime.now()).getSavedCommentId();
		Long reactionId = 1L;

		// when
		Long id = userReactionService.saveCommentReaction(reactionId, loginId, commentId);

		// then
		assertThat(id).isNotNull();
	}

	@DisplayName("반응이 존재하지 않아 댓글 사용자 반응 등록에 실패한다.")
	@Test
	void createComment_NoSuchReactionException() {
		// given
		Long loginId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Service", "내용", null, loginId);
		Long issueId = issueService.save(issueSaveRequest, loginId);
		CommentSaveServiceRequest commentSaveServiceRequest = new CommentSaveServiceRequest(issueId, loginId, "댓글");
		Long commentId = commentService.save(commentSaveServiceRequest, LocalDateTime.now()).getSavedCommentId();
		Long reactionId = 10000L;

		// when & then
		assertThatThrownBy(() -> userReactionService.saveCommentReaction(reactionId, loginId, commentId)).isInstanceOf(
			NoSuchReactionException.class);
	}

	@DisplayName("반응이 존재하지 않아 댓글 사용자 반응 등록에 실패한다.")
	@Test
	void createComment_NoSuchCommentException() {
		// given
		Long loginId = userRepository.save(FixtureFactory.createUserSaveServiceRequest().toEntity());
		Long commentId = 10000L;
		Long reactionId = 1L;

		// when & then
		assertThatThrownBy(() -> userReactionService.saveCommentReaction(reactionId, loginId, commentId)).isInstanceOf(
			NoSuchCommentException.class);
	}
}