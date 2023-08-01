package codesquard.app.milestone.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
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
}
