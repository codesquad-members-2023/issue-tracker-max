package com.issuetracker.app.label.ui.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.app.label.application.dto.LabelSearchInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LabelSearchResponse {

	private String title;
	private String color;

	public static LabelSearchResponse from(LabelSearchInformation label) {
		return new LabelSearchResponse(
			label.getTitle(),
			label.getColor()
		);
	}

	public static List<LabelSearchResponse> from(List<LabelSearchInformation> labels) {
		return labels.stream()
			.map(LabelSearchResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
