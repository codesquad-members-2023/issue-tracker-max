package com.issuetracker.issue.ui.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.application.dto.IssueLabelMappingInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueLabelMappingResponse {

	private Long id;
	private String title;
	private String color;

	public static IssueLabelMappingResponse from(IssueLabelMappingInformation issueLabelMappingInformation) {
		return new IssueLabelMappingResponse(
			issueLabelMappingInformation.getId(),
			issueLabelMappingInformation.getTitle(),
			issueLabelMappingInformation.getColor()
		);
	}

	public static List<IssueLabelMappingResponse> from(List<IssueLabelMappingInformation> issueLabelMappingInformation) {
		return issueLabelMappingInformation.stream()
			.map(IssueLabelMappingResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
