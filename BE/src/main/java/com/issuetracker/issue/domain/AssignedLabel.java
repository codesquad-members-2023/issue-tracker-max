package com.issuetracker.issue.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AssignedLabel {

	private Long issueId;
	private Long labelId;

	@Builder
	private AssignedLabel(Long issueId, Long labelId) {
		this.issueId = issueId;
		this.labelId = labelId;
	}
}
