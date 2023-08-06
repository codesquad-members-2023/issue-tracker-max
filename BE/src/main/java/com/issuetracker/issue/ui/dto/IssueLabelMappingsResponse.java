package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.issue.application.dto.IssueLabelMappingInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueLabelMappingsResponse {

	private List<IssueLabelMappingResponse> labels;

	public static IssueLabelMappingsResponse from(List<IssueLabelMappingInformation> issueLabelMappingInformation) {
		return new IssueLabelMappingsResponse(IssueLabelMappingResponse.from(issueLabelMappingInformation));
	}
}
