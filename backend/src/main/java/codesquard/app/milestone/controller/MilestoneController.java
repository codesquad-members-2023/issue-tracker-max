package codesquard.app.milestone.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
import codesquard.app.milestone.dto.request.MilestoneStatusRequest;
import codesquard.app.milestone.dto.request.MilestoneUpdateRequest;
import codesquard.app.milestone.dto.response.MilestoneDeleteResponse;
import codesquard.app.milestone.dto.response.MilestoneReadResponse;
import codesquard.app.milestone.dto.response.MilestoneSaveResponse;
import codesquard.app.milestone.dto.response.MilestoneStatusUpdateResponse;
import codesquard.app.milestone.dto.response.MilestoneUpdateResponse;
import codesquard.app.milestone.entity.MilestoneStatus;
import codesquard.app.milestone.service.MilestoneService;

@RestController
public class MilestoneController {
	private final MilestoneService milestoneService;

	public MilestoneController(final MilestoneService milestoneService) {
		this.milestoneService = milestoneService;
	}

	@GetMapping("/api/milestones")
	public ResponseEntity<MilestoneReadResponse> get(
		@RequestParam(name = "state", defaultValue = "opened") final String openedString,
		@RequestParam(name = "state", defaultValue = "closed") final String closedString) {
		MilestoneReadResponse milestoneReadResponse = milestoneService.makeMilestoneResponse(
			MilestoneStatus.chooseStatus(openedString, closedString));

		return ResponseEntity.status(HttpStatus.OK)
			.body(milestoneReadResponse.success());
	}

	@PostMapping("/api/milestones")
	public ResponseEntity<MilestoneSaveResponse> save(
		@Valid @RequestBody final MilestoneSaveRequest milestoneSaveRequest) {
		Long milestoneId = milestoneService.saveMilestone(milestoneSaveRequest);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(MilestoneSaveResponse.success(milestoneId));
	}

	@PutMapping("/api/milestones/{milestoneId}")
	public ResponseEntity<MilestoneUpdateResponse> update(@PathVariable final Long milestoneId,
		@Valid @RequestBody final MilestoneUpdateRequest milestoneUpdateRequest) {
		milestoneService.updateMilestone(milestoneId, milestoneUpdateRequest);

		return ResponseEntity.status(HttpStatus.OK)
			.body(MilestoneUpdateResponse.success());
	}

	@PatchMapping("/api/milestones/{milestoneId}/status")
	public ResponseEntity<MilestoneStatusUpdateResponse> updateStatus(@PathVariable final Long milestoneId,
		@Valid @RequestBody final MilestoneStatusRequest milestoneStatusRequest) {
		milestoneService.updateMilestoneStatus(milestoneId, milestoneStatusRequest);

		return ResponseEntity.status(HttpStatus.OK)
			.body(MilestoneStatusUpdateResponse.success());
	}

	@DeleteMapping("/api/milestones/{milestoneId}")
	public ResponseEntity<MilestoneDeleteResponse> delete(@PathVariable final Long milestoneId) {
		milestoneService.deleteMilestone(milestoneId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(MilestoneDeleteResponse.success());
	}
}
