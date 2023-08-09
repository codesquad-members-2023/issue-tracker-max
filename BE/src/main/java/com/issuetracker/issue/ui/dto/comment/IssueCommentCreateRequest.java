package com.issuetracker.issue.ui.dto.comment;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.issuetracker.issue.application.dto.comment.IssueCommentCreateData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueCommentCreateRequest {

	@NotBlank(message = "댓글 작성 시 공백일 수가 없습니다.")
	@Length(min = 1, max = 3000, message = "댓글 내용은 1글자 이상 3000자 이하로 입력해 주세요.")
	private String content;

	public IssueCommentCreateData toIssueCommentCreateData(Long id, Long authorId) {
		return new IssueCommentCreateData(
			id,
			content,
			authorId
		);
	}
}
