package org.presents.issuetracker.label.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Label {
	private Long labelId;
	private String name;
	private String description;
	private String backgroundColor;
	private String textColor;
	private Boolean isDeleted;
}
