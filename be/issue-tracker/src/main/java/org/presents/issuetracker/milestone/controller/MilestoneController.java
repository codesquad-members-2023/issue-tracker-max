package org.presents.issuetracker.milestone.controller;

import java.util.List;

import org.presents.issuetracker.global.dto.response.IdResponseDto;
import org.presents.issuetracker.milestone.dto.request.MilestoneRequest;
import org.presents.issuetracker.milestone.dto.request.MilestoneStatusUpdateRequest;
import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.milestone.entity.vo.MilestoneInfo;
import org.presents.issuetracker.milestone.service.MilestoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RestController
@RequestMapping("/milestones")
@RequiredArgsConstructor
public class MilestoneController {
	private final MilestoneService milestoneService;

	@GetMapping("/previews")
	public ResponseEntity<List<MilestonePreviewResponse>> getMilestonePreviews() {
		List<MilestonePreviewResponse> milestonePreviews = milestoneService.getMilestonePreviews();
		// 필터링 뷰에서 '마일스톤이 없는 이슈'를 표시하기 위해서 0번째에 객체 추가
		milestonePreviews.add(0, MilestonePreviewResponse.getMilestoneNotAssignedResponse());
		return ResponseEntity.ok().body(milestonePreviews);
	}

	@GetMapping
	public ResponseEntity<MilestoneInfo> getMilestoneDetails(@RequestParam(required = false, defaultValue = "open") String status) {
		MilestoneInfo milestoneDetails = milestoneService.getMilestoneDetails(status);
		return ResponseEntity.status(HttpStatus.OK).body(milestoneDetails);
	}

	@PostMapping
	public ResponseEntity<IdResponseDto> create(@RequestBody @Valid MilestoneRequest milestoneRequest) {
		IdResponseDto idResponseDto = milestoneService.create(milestoneRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(idResponseDto);
	}

	@PatchMapping
	public ResponseEntity<IdResponseDto> update(@RequestBody @Valid MilestoneRequest milestoneRequest) {
		IdResponseDto idResponseDto = milestoneService.update(milestoneRequest);
		return ResponseEntity.status(HttpStatus.OK).body(idResponseDto);
	}

	@PutMapping
	public ResponseEntity<Void> updateStatus(@RequestBody @Valid MilestoneStatusUpdateRequest milestoneStatusUpdateRequest) {
		milestoneService.updateStatus(milestoneStatusUpdateRequest);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		milestoneService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
