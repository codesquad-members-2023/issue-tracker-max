package com.issuetracker.milestone.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetracker.milestone.application.MilestoneService;
import com.issuetracker.milestone.ui.dto.MilestoneCreateRequest;
import com.issuetracker.milestone.ui.dto.MilestoneCreateResponse;
import com.issuetracker.milestone.ui.dto.MilestoneDeleteRequest;
import com.issuetracker.milestone.ui.dto.MilestoneSearchByOpenStatusRequest;
import com.issuetracker.milestone.ui.dto.MilestoneUpdateOpenStatusRequest;
import com.issuetracker.milestone.ui.dto.MilestoneUpdateRequest;
import com.issuetracker.milestone.ui.dto.MilestonesResponse;

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

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateMilestone(@PathVariable Long id,
		@RequestBody @Valid MilestoneUpdateRequest milestoneUpdateRequest) {

		milestoneService.update(milestoneUpdateRequest.toMilestoneUpdateInputData(id));
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/open")
	public ResponseEntity<Void> updateMilestoneOpenStatus(
		@RequestBody @Valid MilestoneUpdateOpenStatusRequest milestoneUpdateOpenStatusRequest, @PathVariable Long id) {

		milestoneService.updateOpenStatus(milestoneUpdateOpenStatusRequest.toMilestoneUpdateOpenStatusInputData(id));
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {

		milestoneService.delete(MilestoneDeleteRequest.toMilestoneDeleteInputData(id));
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<MilestonesResponse> showMilestones(
		MilestoneSearchByOpenStatusRequest milestoneSearchByOpenStatusRequest) {

		return ResponseEntity.ok()
			.body(MilestonesResponse.from(milestoneService.search(
				milestoneSearchByOpenStatusRequest.toMilestoneSearchByOpenStatusInputData())));
	}
}
