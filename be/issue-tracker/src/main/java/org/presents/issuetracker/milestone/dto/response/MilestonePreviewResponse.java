package org.presents.issuetracker.milestone.dto.response;

import org.presents.issuetracker.milestone.entity.Milestone;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MilestonePreviewResponse {
	private Long milestoneId;
	private String name;
	private int progress;

	public static MilestonePreviewResponse fromEntity(Milestone milestone) {
		return MilestonePreviewResponse.builder()
			.milestoneId(milestone.getMilestoneId())
			.name(milestone.getName())
			.progress(milestone.getProgress())
			.build();
	}
}
