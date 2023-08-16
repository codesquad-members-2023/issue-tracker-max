package com.issuetrackermax.domain.comment;

import static com.issuetrackermax.fixture.EntityFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.comment.entity.CommentMemberVO;
import com.issuetrackermax.util.DatabaseCleaner;

public class CommentRepositoryTest extends IntegrationTestSupport {
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	DatabaseCleaner databaseCleaner;

	@AfterEach
	void setUp() {
		databaseCleaner.execute();
	}

	@DisplayName("댓글 저장 시 id를 반환한다")
	@Test
	void returnIdWhenSave() {
		// given
		Long issueId = 1L;
		Long writerId = 1L;
		Comment comment = makeComment("content", issueId, writerId);

		// when
		Long commentId = commentRepository.save(comment);

		// then
		Comment savedComment = commentRepository.findById(commentId);
		assertAll(
			() -> assertThat(savedComment.getId()).isEqualTo(commentId),
			() -> assertThat(savedComment.getContent()).isEqualTo(comment.getContent()),
			() -> assertThat(savedComment.getIssueId()).isEqualTo(issueId),
			() -> assertThat(savedComment.getWriterId()).isEqualTo(writerId)
		);
	}

	@Test
	void updateComment() {
		// given
		Long issueId = 1L;
		Long writerId = 1L;
		Comment comment = makeComment("content", issueId, writerId);
		Long commentId = commentRepository.save(comment);

		String newContent = "Updated content";
		Comment updatedComment = makeComment(newContent, issueId, writerId);

		// when
		commentRepository.updateComment(updatedComment, commentId);

		// then
		Comment modifiedComment = commentRepository.findById(commentId);
		assertThat(modifiedComment.getContent()).isEqualTo(newContent);
	}

	@DisplayName("이슈id로 댓글을 찾으면 해당하는 모든 댓글을 가져온다")
	@Test
	void findCommentsByIssueId() {
		// given
		Long issueId = 1L;
		Long writerId = 1L;
		Comment comment = makeComment("content", issueId, writerId);
		Comment comment2 = makeComment("content2", issueId, writerId);
		Long commentId = commentRepository.save(comment);
		Long commentId2 = commentRepository.save(comment2);

		// when
		List<CommentMemberVO> comments = commentRepository.findByIssueId(issueId);

		// then
		assertThat(comments.size()).isEqualTo(2);
	}

}
