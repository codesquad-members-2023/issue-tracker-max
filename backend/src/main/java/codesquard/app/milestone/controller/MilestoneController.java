package codesquard.app.milestone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.milestone.dto.request.MilestoneSavedRequest;
import codesquard.app.milestone.dto.response.MilestoneSavedResponse;
import codesquard.app.milestone.service.MilestoneService;

@RestController
public class MilestoneController {
	private final MilestoneService milestoneService;

	public MilestoneController(MilestoneService milestoneService) {
		this.milestoneService = milestoneService;
	}

	@PostMapping("/api/milestones")
	public ResponseEntity<MilestoneSavedResponse> save(@RequestBody MilestoneSavedRequest milestoneSavedRequest) {
		Long milestoneId = milestoneService.saveMilestone(milestoneSavedRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(MilestoneSavedResponse.success(true, milestoneId));
	}
}
