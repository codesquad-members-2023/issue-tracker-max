package org.presents.issuetracker.label.entity.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LabelVo {
	private Long id;
	private String name;
	private String textColor;
	private String backgroundColor;
}
