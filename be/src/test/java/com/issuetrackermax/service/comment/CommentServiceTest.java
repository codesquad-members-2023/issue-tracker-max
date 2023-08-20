package com.issuetrackermax.service.comment;

import static com.issuetrackermax.fixture.EntityFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.CommentException;
import com.issuetrackermax.controller.comment.dto.request.CommentCreateRequest;
import com.issuetrackermax.controller.comment.dto.request.CommentModifyRequest;
import com.issuetrackermax.controller.comment.dto.response.CommentResponse;
import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.comment.CommentRepository;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.comment.entity.CommentMemberVO;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;
import com.issuetrackermax.util.DatabaseCleaner;

public class CommentServiceTest extends IntegrationTestSupport {
	@Autowired
	CommentService commentService;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	DatabaseCleaner databaseCleaner;

	@AfterEach
	void setUp() {
		databaseCleaner.execute();
	}

	@DisplayName("새로운 댓글 저장시 작성한 댓글을 반환한다.")
	@Test
	void returnWrittenCommentWhenSaving() {
		// given
		Member member = makeMember("loginId", "123456", "name1", LoginType.LOCAL);
		Long writerId = memberRepository.save(member);
		CommentCreateRequest request = CommentCreateRequest.builder().content("content1").build();

		// when
		CommentResponse commentResponse = commentService.save(request, 1L, writerId);

		// then
		assertAll(() -> assertThat(commentResponse.getContent()).isEqualTo(request.getContent()),
			() -> assertThat(commentResponse.getWriter().getId()).isEqualTo(writerId));
	}

	@DisplayName("특정한 issueId로 댓글을 찾으면 해당하는 모든 댓글을 반환한다")
	@Test
	void returnCommentListWhenFindByIssueId() {
		// given
		Long issueId = 1L;
		Long issueId2 = 2L;
		CommentCreateRequest request1 = CommentCreateRequest.builder().content("content1").build();
		CommentCreateRequest request2 = CommentCreateRequest.builder().content("content1").build();
		CommentCreateRequest request3 = CommentCreateRequest.builder().content("content1").build();
		Member member = makeMember("loginId", "123456", "name1", LoginType.LOCAL);
		Long writerId1 = memberRepository.save(member);
		Member member2 = makeMember("loginId2", "123456", "name2", LoginType.LOCAL);
		Long writerId2 = memberRepository.save(member);

		commentService.save(request1, issueId, writerId1);
		commentService.save(request2, issueId, writerId2);
		commentService.save(request3, issueId2, writerId1);

		// when
		List<CommentMemberVO> comments = commentService.findByIssueId(issueId);

		// then
		assertThat(comments.size()).isEqualTo(2);
	}

	@DisplayName("수정하는 사용자가 작성자와 같을 경우 수정을 한다")
	@Test
	void modifyCommentBySameWriter() {
		// given
		Member member = makeMember("loginId", "123456", "name1", LoginType.LOCAL);
		Long writerId = memberRepository.save(member);
		CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder().content("content1").build();
		CommentResponse commentResponse = commentService.save(commentCreateRequest, 1L, writerId);

		CommentModifyRequest request = CommentModifyRequest.builder().content("Modified content").build();

		// when
		commentService.modifyComment(request, commentResponse.getId(), writerId);

		// then
		Comment modifiedComment = commentRepository.findById(commentResponse.getId());
		assertThat(modifiedComment.getContent()).isEqualTo(request.getContent());
	}

	@DisplayName("수정하는 사용자가 작성자와 같지 않을 경우 예외를 반환한다")
	@Test
	void modifyCommentByDifferentWriter() {
		// given
		Member member = makeMember("loginId", "123456", "name1", LoginType.LOCAL);
		Long writerId1 = memberRepository.save(member);
		Member member2 = makeMember("loginId2", "123456", "name2", LoginType.LOCAL);
		Long writerId2 = memberRepository.save(member);

		CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder().content("content1").build();
		CommentResponse commentResponse = commentService.save(commentCreateRequest, 1L, writerId1);

		CommentModifyRequest request = CommentModifyRequest.builder().content("Modified content").build();

		// when, then
		assertThatThrownBy(
			() -> commentService.modifyComment(request, commentResponse.getId(), writerId2)).isInstanceOf(
			ApiException.class).hasMessage(CommentException.NOT_AUTHORIZED.getMessage());
	}
}
