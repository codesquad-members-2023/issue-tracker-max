package org.presents.issuetracker.issue.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssigneeImage {
	private String image;

	@Builder
	public AssigneeImage(String image) {
		this.image = image;
	}
}
