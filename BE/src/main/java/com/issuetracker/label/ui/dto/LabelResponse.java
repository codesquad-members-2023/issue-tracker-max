package com.issuetracker.label.ui.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.label.application.dto.LabelInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LabelResponse {

	private Long id;
	private String title;
	private String color;
	private String description;

	public static LabelResponse from(LabelInformation labelInformation) {
		return new LabelResponse(
			labelInformation.getId(),
			labelInformation.getTitle(),
			labelInformation.getColor(),
			labelInformation.getDescription()
		);
	}

	public static List<LabelResponse> from(List<LabelInformation> labelInformation) {
		return labelInformation.stream()
			.map(LabelResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
