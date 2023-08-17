package com.issuetrackermax.controller.milestone;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.filter.dto.response.MilestoneResponse;
import com.issuetrackermax.controller.milestone.dto.request.MilestoneModifyRequest;
import com.issuetrackermax.controller.milestone.dto.request.MilestonePostRequest;
import com.issuetrackermax.controller.milestone.dto.response.MilestoneDetailResponse;
import com.issuetrackermax.controller.milestone.dto.response.MilestonePostResponse;
import com.issuetrackermax.controller.milestone.dto.response.MilestonesResponse;
import com.issuetrackermax.service.label.LabelService;
import com.issuetrackermax.service.milestone.MilestoneService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {
	private final MilestoneService milestoneService;

	private final LabelService labelService;

	@GetMapping("/open")
	public ApiResponse<MilestonesResponse> getOpenMilestone() {

		Long labelSize = (long)labelService.getLabelList().size();
		List<MilestoneDetailResponse> milestones = milestoneService.getOpenMilestone();
		Long closedMilestoneCount = (long)milestoneService.getClosedMilestone().size();

		MilestonesResponse response = MilestonesResponse.builder()
			.labelCount(labelSize)
			.oppositeCount(closedMilestoneCount)
			.milestones(milestones)
			.build();
		return ApiResponse.success(response);
	}

	@GetMapping("/closed")
	public ApiResponse<MilestonesResponse> getClosedMilestone() {

		Long labelSize = (long)labelService.getLabelList().size();
		List<MilestoneDetailResponse> milestones = milestoneService.getClosedMilestone();
		Long openMilestoneCount = (long)milestoneService.getOpenMilestone().size();

		MilestonesResponse response = MilestonesResponse.builder()
			.labelCount(labelSize)
			.oppositeCount(openMilestoneCount)
			.milestones(milestones)
			.build();
		return ApiResponse.success(response);
	}

	@PostMapping
	public ApiResponse<MilestonePostResponse> post(
		@RequestBody
		@Valid MilestonePostRequest milestonePostRequest) {
		Long id = milestoneService.save(milestonePostRequest);
		MilestonePostResponse response = MilestonePostResponse
			.builder()
			.id(id)
			.build();
		return ApiResponse.success(response);
	}

	@PutMapping("/{id}")
	public ApiResponse<Void> modify(@PathVariable Long id,
		@RequestBody
		@Valid MilestoneModifyRequest milestoneModifyRequest) {
		milestoneService.update(id, milestoneModifyRequest);
		return ApiResponse.success();
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable Long id) {
		milestoneService.delete(id);
		return ApiResponse.success();
	}

	@PatchMapping("/status/{id}")
	public ApiResponse<Void> updateStatus(@PathVariable Long id) {
		milestoneService.updateStatus(id);
		return ApiResponse.success();

	}

	@GetMapping("/show-content")
	public ApiResponse<List<MilestoneResponse>> showMilestoneList() {
		return ApiResponse.success(milestoneService.findMilestoneList());
	}
}
