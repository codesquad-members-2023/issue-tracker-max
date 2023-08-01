package codesquard.app.comment.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.comment.repository.CommentRepository;
import codesquard.app.comment.service.request.CommentModifyServiceRequest;
import codesquard.app.comment.service.request.CommentSaveServiceRequest;
import codesquard.app.comment.service.response.CommentModifyResponse;
import codesquard.app.comment.service.response.CommentSaveResponse;
import codesquard.app.errors.exception.CommentMaxLengthExceededException;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.issue.repository.IssueRepository;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;

class CommentServiceTest extends IntegrationTestSupport {

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IssueRepository issueRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
		jdbcTemplate.update("TRUNCATE TABLE comment");
		jdbcTemplate.update("TRUNCATE TABLE issue");
		jdbcTemplate.update("TRUNCATE TABLE milestone");
		jdbcTemplate.update("TRUNCATE TABLE user");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@DisplayName("새로운 댓글을 등록한다.")
	@Test
	void save() {
		// given
		LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 16, 0);

		createUser("yeon", "yeon@email.com", "password1000", "url path");
		createIssue(null, 1L, "test issue", "hello", IssueStatus.OPENED, createdAt);

		CommentSaveServiceRequest request = new CommentSaveServiceRequest(1L, 1L, "comment save service request");

		// when
		CommentSaveResponse savedComment = commentService.save(request, createdAt);

		// then
		assertThat(savedComment).extracting("commentId")
			.isEqualTo(1L);
	}

	@DisplayName("10000자를 초과하는 댓글을 등록하려는 경우 예외가 발생한다.")
	@Test
	void saveExceedingMaxLengthContent() {
		// given
		LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 16, 0);
		String invalidContent = generateExceedingMaxLengthContent(10000);

		createUser("yeon", "yeon@email.com", "password1000", "url path");
		createIssue(null, 1L, "test issue", "hello", IssueStatus.OPENED, createdAt);

		CommentSaveServiceRequest request = new CommentSaveServiceRequest(1L, 1L, invalidContent);

		// when // then
		assertThatThrownBy(() -> commentService.save(request, createdAt))
			.isInstanceOf(CommentMaxLengthExceededException.class)
			.hasMessage("댓글은 1자 이상 10000자 이하여야 합니다.");
	}

	@DisplayName("등록된 댓글을 수정한다.")
	@Test
	void modify() {
		// given
		LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 16, 0);
		LocalDateTime modifiedAt = LocalDateTime.of(2023, 8, 1, 17, 0);

		createUser("yeon", "yeon@email.com", "password1000", "url path");
		createIssue(null, 1L, "test issue", "hello", IssueStatus.OPENED, createdAt);

		CommentSaveServiceRequest saveRequest = new CommentSaveServiceRequest(1L, 1L, "comment save service request");
		CommentSaveResponse savedComment = commentService.save(saveRequest, createdAt);

		CommentModifyServiceRequest modifyRequest = new CommentModifyServiceRequest(savedComment.getCommentId(),
			"modified service content");

		// when
		CommentModifyResponse modifiedComment = commentService.modify(modifyRequest, modifiedAt);

		// then
		assertThat(modifiedComment).extracting("commentId").isEqualTo(savedComment.getCommentId());
	}

	@DisplayName("댓글 수정 시 10000자를 초과하는 경우 예외가 발생한다.")
	@Test
	void modifyExceedingMaxLengthContent() {
		// given
		LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 16, 0);
		LocalDateTime modifiedAt = LocalDateTime.of(2023, 8, 1, 17, 0);

		createUser("yeon", "yeon@email.com", "password1000", "url path");
		createIssue(null, 1L, "test issue", "hello", IssueStatus.OPENED, createdAt);

		CommentSaveServiceRequest saveRequest = new CommentSaveServiceRequest(1L, 1L, "comment save service request");
		CommentSaveResponse savedComment = commentService.save(saveRequest, createdAt);

		CommentModifyServiceRequest modifyRequest = new CommentModifyServiceRequest(savedComment.getCommentId(),
			generateExceedingMaxLengthContent(10000));

		// when // then
		assertThatThrownBy(() -> commentService.modify(modifyRequest, modifiedAt))
			.isInstanceOf(CommentMaxLengthExceededException.class)
			.hasMessage("댓글은 1자 이상 10000자 이하여야 합니다.");
	}

	private void createUser(String loginId, String email, String password, String avatarUrl) {
		User user = new User(loginId, email, password, avatarUrl);
		userRepository.save(user);
	}

	private void createIssue(Long milestoneId, Long userId, String title, String content, IssueStatus status,
		LocalDateTime createdAt) {
		Issue issue = new Issue(milestoneId, userId, title, content, status, createdAt);
		issueRepository.save(issue);
	}

	private String generateExceedingMaxLengthContent(int maxLength) {
		StringBuilder builder = new StringBuilder();
		while (builder.length() <= maxLength) {
			builder.append("The only way to do great work is to love what you do. ");
		}

		return builder.toString();
	}

}
