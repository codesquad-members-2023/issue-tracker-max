package org.presents.issuetracker.issue.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssigneeImage {
	private String loginId;
	private String image;

	@Builder
	public AssigneeImage(String loginId, String image) {
		this.loginId = loginId;
		this.image = image;
	}
}
