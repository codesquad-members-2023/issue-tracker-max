package codesquad.issueTracker.milestone.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.global.common.ApiResponse;
import codesquad.issueTracker.milestone.dto.MileStoneStatusDto;
import codesquad.issueTracker.milestone.dto.MilestoneResponseDto;
import codesquad.issueTracker.milestone.dto.ModifyMilestoneRequestDto;
import codesquad.issueTracker.milestone.service.MilestoneService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MilestoneController {

	private final MilestoneService milestoneService;

	@PostMapping("/milestones")
	public ApiResponse<String> postMilestones(@Valid @RequestBody ModifyMilestoneRequestDto request) {
		milestoneService.save(request);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@PatchMapping("/milestones/{id}")
	public ApiResponse<String> patchMilestone(@Valid @RequestBody ModifyMilestoneRequestDto request,
		@PathVariable Long id) {
		milestoneService.update(id, request);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@DeleteMapping("/milestones/{id}")
	public ApiResponse<String> deleteMilestone(@PathVariable Long id) {
		milestoneService.delete(id);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@PatchMapping("/milestones/status/{id}")
	public ApiResponse<String> patchMilestoneStatus(@RequestBody MileStoneStatusDto request, @PathVariable Long id) {
		milestoneService.updateStatus(id, request);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@GetMapping("/milestones")
	public ApiResponse<MilestoneResponseDto> getMilestones(@RequestBody MileStoneStatusDto request) {
		MilestoneResponseDto milestoneResponse = milestoneService.findAll(request);
		return ApiResponse.success(SUCCESS.getStatus(), milestoneResponse);
	}

}