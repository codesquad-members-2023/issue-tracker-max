package codesquard.app.issue.comment.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Comment {

	private Long id; // 등록번호
	private Long issueId; // 이슈 등록번호
	private Long userId; // 사용자 등록번호
	private String content; // 댓글 내용
	private LocalDateTime createdAt; // 생성시간
	private LocalDateTime modifiedAt; // 수정시간
	@JsonProperty("isDeleted")
	private boolean isDeleted; // 삭제여부

}
