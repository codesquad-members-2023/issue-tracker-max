package org.presents.issuetracker.milestone.entity.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MilestonePreviewVo {
	private Long id;
	private String name;
	private int progress;
}
