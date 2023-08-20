package com.issuetrackermax.controller.label;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.label.dto.request.LabelModifyRequest;
import com.issuetrackermax.controller.label.dto.request.LabelPostRequest;
import com.issuetrackermax.controller.label.dto.response.LabelContentResponse;
import com.issuetrackermax.controller.label.dto.response.LabelDetailResponse;
import com.issuetrackermax.controller.label.dto.response.LabelPostResponse;
import com.issuetrackermax.controller.label.dto.response.LabelsResponse;
import com.issuetrackermax.service.label.LabelService;
import com.issuetrackermax.service.milestone.MilestoneService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/labels")
public class LabelController {
	private final LabelService labelService;
	private final MilestoneService milestoneService;

	@GetMapping
	public ApiResponse<LabelsResponse> show() {
		Long milestoneCount = milestoneService.getMilestoneCount();
		List<LabelDetailResponse> labelList = labelService.getLabelList();
		LabelsResponse response = LabelsResponse
			.builder()
			.milestoneCount(milestoneCount)
			.labels(labelList)
			.build();
		return ApiResponse.success(response);
	}

	@GetMapping("/show-content")
	ApiResponse<List<LabelContentResponse>> showContent() {
		return ApiResponse.success(labelService.getLabelContent());
	}

	@PostMapping
	public ApiResponse<LabelPostResponse> post(
		@RequestBody
		@Valid LabelPostRequest labelPostRequest) {
		Long id = labelService.save(labelPostRequest);
		LabelPostResponse response = LabelPostResponse.builder().id(id).build();
		return ApiResponse.success(response);
	}

	@PutMapping("/{id}")
	public ApiResponse<Void> modify(@PathVariable Long id,
		@RequestBody
		@Valid LabelModifyRequest labelModifyRequest) {
		labelService.update(id, labelModifyRequest);
		return ApiResponse.success();
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable Long id) {
		labelService.delete(id);
		return ApiResponse.success();
	}

}
