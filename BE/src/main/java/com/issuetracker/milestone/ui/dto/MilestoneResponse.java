package com.issuetracker.milestone.ui.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.issuetracker.milestone.application.dto.MilestoneInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MilestoneResponse {

	private Long id;
	private String title;
	private String description;
	private LocalDate deadline;
	@JsonProperty(value = "isOpen")
	private boolean open;
	private Double progress;

	public String makeStringDeadline() {
		return deadline.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
	}

	public static MilestoneResponse from(MilestoneInformation milestoneInformation) {
		return new MilestoneResponse(
			milestoneInformation.getId(),
			milestoneInformation.getTitle(),
			milestoneInformation.getDescription(),
			milestoneInformation.getDeadline(),
			milestoneInformation.isOpen(),
			milestoneInformation.getProgress()
		);
	}

	public static List<MilestoneResponse> from(List<MilestoneInformation> milestoneInformation) {
		return milestoneInformation.stream()
			.map(MilestoneResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}

}

