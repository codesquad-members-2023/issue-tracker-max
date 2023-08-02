package org.presents.issuetracker.issue.dto.response;

import org.presents.issuetracker.milestone.entity.Milestone;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MilestoneResponseDto {
	private Long id;
	private String name;

	public static MilestoneResponseDto fromEntity(Milestone milestone) {
		return MilestoneResponseDto.builder()
			.id(milestone.getMilestoneId())
			.name(milestone.getName()).build();
	}
}
