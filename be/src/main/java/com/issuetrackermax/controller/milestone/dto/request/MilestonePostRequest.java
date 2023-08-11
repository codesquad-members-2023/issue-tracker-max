package com.issuetrackermax.controller.milestone.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestonePostRequest {
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;
	private LocalDateTime dueDate;
	private String description;

	@Builder
	public MilestonePostRequest(String name, LocalDateTime dueDate, String description) {
		this.name = name;
		this.dueDate = dueDate;
		this.description = description;
	}
}
