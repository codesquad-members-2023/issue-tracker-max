package com.issuetracker.unit.infrastrucure;

import static com.issuetracker.util.fixture.IssueFixture.ISSUE1;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER1;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.issue.domain.comment.IssueComment;
import com.issuetracker.issue.domain.comment.IssueCommentRepository;
import com.issuetracker.issue.infrastrucure.JdbcIssueCommentRepository;
import com.issuetracker.util.JdbcRepositoryTest;

public class IssueCommentJdbcRepositoryTest extends JdbcRepositoryTest {

	private IssueCommentRepository issueCommentRepository;

	@Autowired
	public IssueCommentJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.issueCommentRepository = new JdbcIssueCommentRepository(jdbcTemplate);
	}

	@Test
	void 이슈_댓글을_작성한다() {
		// given
		IssueComment issueComment = IssueComment.builder()
			.issueId(ISSUE1.getId())
			.content("## 이슈 댓글 첫 작성!")
			.authorId(MEMBER1.getId())
			.createAt(LocalDateTime.now())
			.build();

		// when
		long actual = issueCommentRepository.save(issueComment);

		// then
		Assertions.assertThat(actual).isEqualTo(13L);
	}
}
