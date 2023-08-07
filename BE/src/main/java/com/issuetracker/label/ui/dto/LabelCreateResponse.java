package com.issuetracker.label.ui.dto;

import com.issuetracker.label.application.dto.LabelCreateInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LabelCreateResponse {

	private Long id;

	public static LabelCreateResponse from(LabelCreateInformation labelCreateInformation) {
		return new LabelCreateResponse(
			labelCreateInformation.getId()
		);
	}
}
