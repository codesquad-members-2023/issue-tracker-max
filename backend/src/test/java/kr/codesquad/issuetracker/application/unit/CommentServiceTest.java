package kr.codesquad.issuetracker.application.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesquad.issuetracker.application.CommentService;
import kr.codesquad.issuetracker.domain.Comment;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.CommentRepository;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

	@Mock
	private CommentRepository commentRepository;

	@InjectMocks
	private CommentService commentService;

	@DisplayName("댓글을 작성에 성공한다.")
	@Test
	void register() {
		// given
		willDoNothing().given(commentRepository).save(any(Comment.class));

		// when & then
		assertThatCode(() -> commentService.register(1, "안녕하세요", 2))
			.doesNotThrowAnyException();
	}

	@DisplayName("댓글 수정에 성공한다.")
	@Test
	void modify() {
		// given
		Comment comment = new Comment("새로운 내용이지롱~", 1, 1);
		willDoNothing().given(commentRepository).update(comment);
		given(commentRepository.findById(1)).willReturn(Optional.of(comment));

		// when
		commentService.modify("진짜 새로운 내용이지롱~", 1, 1);

		// then
		assertThat(comment.getContent()).isEqualTo("진짜 새로운 내용이지롱~");
	}

	@DisplayName("본인이 쓴 댓글이 아닌 다른 사람이 작성한 댓글에는 접근할 수 없다.")
	@Test
	void givenAnotherUserId_thenThrowsException() {
		// given
		Comment comment = new Comment("새로운 내용이지롱~", 1, 1);
		given(commentRepository.findById(1)).willReturn(Optional.of(comment));

		// when & then
		assertThatThrownBy(() -> commentService.modify("진짜 새로운 내용이지롱~", 1, 2))
			.isInstanceOf(ApplicationException.class)
			.extracting("errorCode").isEqualTo(ErrorCode.NO_AUTHORIZATION);
	}
}
