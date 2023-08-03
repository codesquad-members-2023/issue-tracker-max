package org.presents.issuetracker.milestone.dto.response;

import org.presents.issuetracker.milestone.entity.vo.MilestonePreviewVo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MilestonePreviewResponse {
	private Long milestoneId;
	private String name;
	private int progress;

	public static MilestonePreviewResponse fromVo(MilestonePreviewVo milestonePreviewVo) {
		return MilestonePreviewResponse.builder()
			.milestoneId(milestonePreviewVo.getId())
			.name(milestonePreviewVo.getName())
			.progress(milestonePreviewVo.getProgress())
			.build();
	}
}
