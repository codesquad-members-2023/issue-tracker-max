package com.issuetracker.issue.ui.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.issuetracker.issue.application.dto.IssueCommentUpdateData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueCommentUpdateRequest {

	@NotBlank(message = "댓글 작성 시 공백일 수가 없습니다.")
	@Length(min = 1, max = 3000, message = "댓글 내용은 1글자 이상 3000자 이하로 입력해 주세요.")
	private String content;

	public IssueCommentUpdateData toIssueCommentUpdateData(Long issueId, Long issueCommentId) {
		return new IssueCommentUpdateData(
			issueId,
			issueCommentId,
			content
		);
	}
}
