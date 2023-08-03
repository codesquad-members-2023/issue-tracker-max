package codesquard.app.comment.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.comment.service.request.CommentModifyServiceRequest;
import codesquard.app.comment.service.request.CommentSaveServiceRequest;
import codesquard.app.comment.service.response.CommentDeleteResponse;
import codesquard.app.comment.service.response.CommentModifyResponse;
import codesquard.app.comment.service.response.CommentSaveResponse;
import codesquard.app.errors.exception.CommentMaxLengthExceededException;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.repository.IssueRepository;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;

class CommentServiceTest extends IntegrationTestSupport {

	@Autowired
	private CommentService commentService;

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
		createUserFixture();
		createIssueFixture();

		LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 16, 0);

		CommentSaveServiceRequest request = new CommentSaveServiceRequest(1L, 1L, "comment save service request");

		// when
		CommentSaveResponse savedComment = commentService.save(request, createdAt);

		// then
		assertThat(savedComment).extracting("success", "commentId")
			.isEqualTo(List.of(true, 1L));
	}

	@DisplayName("10000자를 초과하는 댓글을 등록하려는 경우 예외가 발생한다.")
	@Test
	void saveExceedingMaxLengthContent() {
		// given
		createUserFixture();
		createIssueFixture();

		LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 16, 0);

		String invalidContent = generateExceedingMaxLengthContent(10000);
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
		createUserFixture();
		createIssueFixture();
		CommentSaveResponse savedComment = createCommentFixture();

		LocalDateTime modifiedAt = LocalDateTime.of(2023, 8, 1, 17, 0);

		CommentModifyServiceRequest modifyRequest = new CommentModifyServiceRequest(savedComment.getCommentId(),
			"modified service content");

		// when
		CommentModifyResponse modifiedComment = commentService.modify(modifyRequest, modifiedAt);

		// then
		assertThat(modifiedComment).extracting("success", "commentId")
			.isEqualTo(List.of(true, savedComment.getCommentId()));
	}

	@DisplayName("댓글 수정 시 10000자를 초과하는 경우 예외가 발생한다.")
	@Test
	void modifyExceedingMaxLengthContent() {
		// given
		createUserFixture();
		createIssueFixture();
		CommentSaveResponse savedComment = createCommentFixture();

		LocalDateTime modifiedAt = LocalDateTime.of(2023, 8, 1, 17, 0);

		String invalidContent = generateExceedingMaxLengthContent(10000);
		CommentModifyServiceRequest modifyRequest = new CommentModifyServiceRequest(savedComment.getCommentId(),
			invalidContent);

		// when // then
		assertThatThrownBy(() -> commentService.modify(modifyRequest, modifiedAt))
			.isInstanceOf(CommentMaxLengthExceededException.class)
			.hasMessage("댓글은 1자 이상 10000자 이하여야 합니다.");
	}

	@DisplayName("등록된 댓글을 삭제한다.")
	@Test
	void delete() {
		// given
		createUserFixture();
		createIssueFixture();
		CommentSaveResponse savedComment = createCommentFixture();

		// when
		CommentDeleteResponse deletedComment = commentService.delete(savedComment.getCommentId());

		// then
		assertThat(deletedComment.isSuccess()).isTrue();
	}

	private void createUserFixture() {
		User user = new User(null, "yeon", "yeon@email.com", "password1000", "url path");
		userRepository.save(user);
	}

	private void createIssueFixture() {
		Issue issue = new Issue(null, 1L, "test issue", "hello");
		issueRepository.save(issue);
	}

	private CommentSaveResponse createCommentFixture() {
		LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 16, 0);
		CommentSaveServiceRequest saveRequest = new CommentSaveServiceRequest(1L, 1L, "comment save service request");
		return commentService.save(saveRequest, createdAt);
	}

	private String generateExceedingMaxLengthContent(int maxLength) {
		StringBuilder builder = new StringBuilder();
		while (builder.length() <= maxLength) {
			builder.append("The only way to do great work is to love what you do. ");
		}

		return builder.toString();
	}

}
