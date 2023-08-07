package com.issuetracker.label.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LabelCreateInformation {

	private Long id;

	public static LabelCreateInformation from(Long id) {
		return new LabelCreateInformation(id);
	}
}
