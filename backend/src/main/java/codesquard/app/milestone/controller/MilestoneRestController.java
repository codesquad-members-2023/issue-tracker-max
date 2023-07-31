package codesquard.app.milestone.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.milestone.dto.request.MilestoneCreateRequest;
import codesquard.app.milestone.dto.response.MilestoneCreateResponse;
import codesquard.app.milestone.service.MilestoneService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MilestoneRestController {

	private final MilestoneService milestoneService;

	@PostMapping("/api/milestones")
	public ResponseEntity<MilestoneCreateResponse> register(
		@Valid @RequestBody MilestoneCreateRequest milestoneCreateRequest) {
		Long id = milestoneService.create(milestoneCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(new MilestoneCreateResponse(true, id));
	}
}
