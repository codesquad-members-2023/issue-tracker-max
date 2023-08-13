package codesquad.issueTracker.comment.dto;

import java.time.LocalDateTime;

import codesquad.issueTracker.comment.vo.CommentUserVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {

	private Long id;
	private LocalDateTime createdAt;
	private String content;
	private CommentUserVo writer;

	@Builder
	public CommentResponseDto(Long id, LocalDateTime createdAt, String content, CommentUserVo writer) {
		this.id = id;
		this.createdAt = createdAt;
		this.content = content;
		this.writer = writer;
	}
}
