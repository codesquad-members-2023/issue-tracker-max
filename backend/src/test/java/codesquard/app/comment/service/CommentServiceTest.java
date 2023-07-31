package codesquard.app.comment.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.comment.repository.CommentRepository;
import codesquard.app.comment.service.request.CommentSaveServiceRequest;
import codesquard.app.comment.service.response.CommentResponse;
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

	@DisplayName("새로운 댓글을 등록한다.")
	@Test
	void save() {
		// given
		LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 16, 0);

		createUser("yeon", "yeon@email.com", "password1000", "url path");
		createIssue(null, 1L, "test issue", "hello", IssueStatus.OPENED, createdAt);

		CommentSaveServiceRequest request = new CommentSaveServiceRequest(1L, 1L, "comment save service request");

		// when
		CommentResponse savedComment = commentService.save(request, createdAt);

		// then
		assertThat(savedComment).extracting("id")
			.isEqualTo(1L);
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

}
