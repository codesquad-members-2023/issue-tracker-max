package com.issuetracker.milestone.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetracker.milestone.application.MilestoneService;
import com.issuetracker.milestone.ui.dto.MilestoneCreateRequest;
import com.issuetracker.milestone.ui.dto.MilestoneCreateResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/milestones")
@RequiredArgsConstructor
public class MilestoneController {

	private final MilestoneService milestoneService;

	@PostMapping
	public ResponseEntity<MilestoneCreateResponse> createMilestone(
		@RequestBody @Valid MilestoneCreateRequest milestoneCreateRequest) {
		MilestoneCreateResponse milestoneCreateResponse
			= MilestoneCreateResponse.from(
			milestoneService.create(milestoneCreateRequest.toMilestoneCreateInputData())
		);

		return ResponseEntity.created(
			URI.create("/api/milestones/" + milestoneCreateResponse.getId())
		).body(milestoneCreateResponse);
	}
}
