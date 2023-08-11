package org.presents.issuetracker.milestone.entity.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MilestonePreview {
	private Long id;
	private String name;
	private int progress;

	@Builder
	private MilestonePreview(Long id, String name, int progress) {
		this.id = id;
		this.name = name;
		this.progress = progress;
	}
}
