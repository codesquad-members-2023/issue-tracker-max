package org.presents.issuetracker.milestone.dto.response;

import org.presents.issuetracker.milestone.entity.vo.MilestonePreview;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MilestonePreviewResponse {
	private Long id;
	private String name;
	private int progress;

	public static MilestonePreviewResponse from(MilestonePreview milestonePreview) {
		if (milestonePreview == null) {
			return null;
		}
		return MilestonePreviewResponse.builder()
			.id(milestonePreview.getId())
			.name(milestonePreview.getName())
			.progress(milestonePreview.getProgress())
			.build();
	}
}
