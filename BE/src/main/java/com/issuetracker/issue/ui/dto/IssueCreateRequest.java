package com.issuetracker.issue.ui.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.issuetracker.issue.application.dto.IssueCreateInputData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class IssueCreateRequest {

	@NotBlank(message = "제목은 필수 값입니다.")
	@Length(max = 100, message = "제목은 최대 100글자 입니다.")
	private String title;

	@Length(max = 3000, message = "내용은 최대 3000글자 입니다.")
	private String content;

	private List<Long> assigneeIds;

	private List<Long> labelIds;

	private Long milestoneId;

	public IssueCreateInputData toIssueCreateData() {
		return new IssueCreateInputData(
			title,
			content,
			assigneeIds,
			labelIds,
			milestoneId
		);
	}
}
