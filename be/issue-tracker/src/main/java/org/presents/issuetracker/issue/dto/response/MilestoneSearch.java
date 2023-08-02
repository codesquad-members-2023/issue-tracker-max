package org.presents.issuetracker.issue.dto.response;

import org.presents.issuetracker.milestone.entity.Milestone;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MilestoneSearch {
	private Long id;
	private String name;

	public static MilestoneSearch fromEntity(Milestone milestone) {
		return MilestoneSearch.builder()
			.id(milestone.getMilestoneId())
			.name(milestone.getName()).build();
	}
}
