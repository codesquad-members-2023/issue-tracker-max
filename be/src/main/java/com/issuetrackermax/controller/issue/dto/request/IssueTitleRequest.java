package com.issuetrackermax.controller.issue.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueTitleRequest {
	@NotEmpty(message = "이슈 제목을 입력해주세요.")
	private String title;

	@Builder
	public IssueTitleRequest(String title) {
		this.title = title;
	}
}
