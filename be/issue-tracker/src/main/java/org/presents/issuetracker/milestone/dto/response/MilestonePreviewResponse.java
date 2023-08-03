package org.presents.issuetracker.milestone.dto.response;

import org.presents.issuetracker.milestone.entity.vo.MilestonePreviewVo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestonePreviewResponse {
	private Long id;
	private String name;
	private int progress;

	@Builder
	private MilestonePreviewResponse(Long id, String name, int progress) {
		this.id = id;
		this.name = name;
		this.progress = progress;
	}

	public static MilestonePreviewResponse fromVo(MilestonePreviewVo milestonePreviewVo) {
		return MilestonePreviewResponse.builder()
			.id(milestonePreviewVo.getId())
			.name(milestonePreviewVo.getName())
			.progress(milestonePreviewVo.getProgress())
			.build();
	}
}
