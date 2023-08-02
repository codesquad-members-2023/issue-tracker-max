package codesquard.app.milestone.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
import codesquard.app.milestone.dto.request.MilestoneUpdateRequest;
import codesquard.app.milestone.dto.response.MilestoneDeleteResponse;
import codesquard.app.milestone.dto.response.MilestoneSaveResponse;
import codesquard.app.milestone.dto.response.MilestoneUpdateResponse;
import codesquard.app.milestone.dto.response.MilestoneSaveResponse;
import codesquard.app.milestone.service.MilestoneService;

@RestController
public class MilestoneController {
	private final MilestoneService milestoneService;

	public MilestoneController(MilestoneService milestoneService) {
		this.milestoneService = milestoneService;
	}

	@PostMapping("/api/milestones")
	public ResponseEntity<MilestoneSaveResponse> save(@Valid @RequestBody MilestoneSaveRequest milestoneSaveRequest) {
		Long milestoneId = milestoneService.saveMilestone(milestoneSaveRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(MilestoneSaveResponse.success(true, milestoneId));
	}

	@PutMapping("/api/milestones/{milestoneId}")
	public ResponseEntity<MilestoneUpdateResponse> update(@PathVariable final Long milestoneId,
		@Valid @RequestBody MilestoneUpdateRequest milestoneUpdateRequest) {
		milestoneService.updateMilestone(milestoneId, milestoneUpdateRequest);

		return ResponseEntity.status(HttpStatus.OK)
			.body(MilestoneUpdateResponse.success(true));
	}

	@DeleteMapping("/api/milestones/{milestoneId}")
	public ResponseEntity<MilestoneDeleteResponse> delete(@PathVariable final Long milestoneId) {
		milestoneService.deleteMilestone(milestoneId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(MilestoneDeleteResponse.success(true));
	}
}
