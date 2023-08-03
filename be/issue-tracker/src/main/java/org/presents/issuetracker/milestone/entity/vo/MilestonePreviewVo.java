package org.presents.issuetracker.milestone.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MilestonePreviewVo {
	private final Long id;
	private final String name;
	private final int progress;
}
