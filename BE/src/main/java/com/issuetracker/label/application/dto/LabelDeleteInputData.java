package com.issuetracker.label.application.dto;

import com.issuetracker.label.domain.Label;

import lombok.Getter;

@Getter
public class LabelDeleteInputData {

	private Long id;

	public LabelDeleteInputData(Long id) {
		this.id = id;
	}

	public Label toLabelForDelete() {
		return Label.builder()
			.id(id)
			.build();
	}
}
