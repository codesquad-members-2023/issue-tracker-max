package org.presents.issuetracker.milestone.controller;

import java.util.List;

import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.milestone.service.MilestoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

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
}
