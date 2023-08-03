package codesquard.app.comment.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.comment.entity.Comment;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.repository.IssueRepository;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;

class CommentRepositoryTest extends IntegrationTestSupport {

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
		createUserFixture();
		createIssueFixture();

		LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 16, 0);
		Comment comment = new Comment(1L, 1L, "Create New Comment", createdAt);

		// when
		Long savedCommentId = commentRepository.save(comment);

		// then
		assertThat(savedCommentId).isEqualTo(1L);
	}

	@DisplayName("등록된 댓글을 수정한다.")
	@Test
	void modify() {
		// given
		createUserFixture();
		createIssueFixture();
		Long savedCommentId = createCommentFixture();

		LocalDateTime modifiedAt = LocalDateTime.of(2023, 8, 1, 17, 0);
		Comment commentForModify = new Comment(savedCommentId, "modified repository content", modifiedAt);

		// when
		Long modifiedCommentId = commentRepository.modify(commentForModify);

		// then
		assertThat(modifiedCommentId).isEqualTo(savedCommentId);
	}

	@DisplayName("등록된 댓글을 삭제한다.")
	@Test
	void test() {
		// given
		createUserFixture();
		createIssueFixture();
		Long savedCommentId = createCommentFixture();

		// when
		Long deletedCommentId = commentRepository.deleteById(savedCommentId);

		// then
		assertThat(savedCommentId).isEqualTo(deletedCommentId);
	}

	private void createUserFixture() {
		User user = new User(null, "yeon", "yeon@email.com", "password1000", "url path");
		userRepository.save(user);
	}

	private void createIssueFixture() {
		Issue issue = new Issue(null, 1L, "test issue", "hello");
		issueRepository.save(issue);
	}

	private Long createCommentFixture() {
		LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 16, 0);
		Comment comment = new Comment(1L, 1L, "Create New Comment", createdAt);
		return commentRepository.save(comment);
	}

}
