package com.issuetracker.app.issue.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IssueLabelMapping {

	private Long issueId;
	private Long labelId;

	@Builder(builderClassName = "IssueLabelMappingBuilder")
	private IssueLabelMapping(Long issueId, Long labelId) {
		this.issueId = issueId;
		this.labelId = labelId;
	}
}
