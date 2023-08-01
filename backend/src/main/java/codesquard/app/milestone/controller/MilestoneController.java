package codesquard.app.milestone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.milestone.dto.request.MilestoneRequestDto;
import codesquard.app.milestone.dto.response.MilestoneResponseDto;
import codesquard.app.milestone.service.MilestoneService;

@RestController
public class MilestoneController {
	private final MilestoneService milestoneService;

	public MilestoneController(MilestoneService milestoneService) {
		this.milestoneService = milestoneService;
	}

	@PostMapping("/api/milestones")
	public ResponseEntity<MilestoneResponseDto> save(@RequestBody MilestoneRequestDto milestoneRequestDto) {
		Long milestoneId = milestoneService.saveMilestone(milestoneRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(MilestoneResponseDto.success(true, milestoneId));
	}
}
