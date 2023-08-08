package com.issuetracker.label.ui.dto;

import java.util.List;

import com.issuetracker.label.application.dto.LabelInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LabelsResponse {

	private List<LabelResponse> labels;

	public static LabelsResponse from(List<LabelInformation> labelInformations) {
		return new LabelsResponse(LabelResponse.from(labelInformations));
	}
}
