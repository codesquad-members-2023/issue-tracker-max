package codesquad.issueTracker.milestone.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.global.ApiResponse;
import codesquad.issueTracker.milestone.dto.SaveMilestoneRequestDto;
import codesquad.issueTracker.milestone.service.MilestoneService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MilestoneController {

	private final MilestoneService milestoneService;

	@PostMapping("/milestones")
	public ApiResponse<Long> milestones(@Valid @RequestBody SaveMilestoneRequestDto request) {
		Long milestoneId = milestoneService.save(request);
		return ApiResponse.success(SUCCESS.getStatus(), milestoneId);
	}

}