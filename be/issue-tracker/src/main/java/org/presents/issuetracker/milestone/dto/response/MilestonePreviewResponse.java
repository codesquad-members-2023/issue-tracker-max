package org.presents.issuetracker.milestone.dto.response;

import org.presents.issuetracker.milestone.entity.vo.MilestonePreview;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MilestonePreviewResponse {
	private static final Long NO_MILESTONE_ID = 0L;
	private static final String NO_MILESTONE_NAME = "none";
	private static final int NO_MILESTONE_PROGRESS = 0;

	private Long id;
	private String name;
	private int progress;

	private static final MilestonePreviewResponse MILESTONE_NOT_ASSIGNED_RESPONSE = MilestonePreviewResponse.builder()
		.id(NO_MILESTONE_ID)
		.name(NO_MILESTONE_NAME)
		.progress(NO_MILESTONE_PROGRESS)
		.build();

	public static MilestonePreviewResponse getMilestoneNotAssignedResponse() {
		return MILESTONE_NOT_ASSIGNED_RESPONSE;
	}

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
