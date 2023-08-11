package org.presents.issuetracker.label.controller;

import java.util.List;

import org.presents.issuetracker.global.dto.response.LabelIdResponse;
import org.presents.issuetracker.label.dto.request.LabelCreateRequest;
import org.presents.issuetracker.label.dto.request.LabelUpdateRequest;
import org.presents.issuetracker.label.dto.response.LabelDetailResponse;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
import org.presents.issuetracker.label.service.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/labels")
public class LabelController {

	private final LabelService labelService;

	public LabelController(LabelService labelService) {
		this.labelService = labelService;
	}

	@GetMapping
	public ResponseEntity<List<LabelDetailResponse>> getLabelDetails() {
		List<LabelDetailResponse> labelDetails = labelService.getLabelDetails();
		return ResponseEntity.ok().body(labelDetails);
	}

	@GetMapping("/previews")
	public ResponseEntity<List<LabelPreviewResponse>> getLabelPreviews() {
		List<LabelPreviewResponse> labelPreviews = labelService.getLabelPreviews();

		// 필터링 뷰에서 `레이블이 없는 이슈`를 표시하기 위해 index 0에 값이 없는 dto 인스턴스를 추가합니다.
		final int INDEX_LABEL_NOT_ASSIGNED = 0;
		labelPreviews.add(INDEX_LABEL_NOT_ASSIGNED, LabelPreviewResponse.getLabelNotAssignedResponse());

		return ResponseEntity.ok().body(labelPreviews);
	}

	@PostMapping
	public ResponseEntity<LabelIdResponse> create(@Valid @RequestBody LabelCreateRequest labelCreateRequest) {
		LabelIdResponse labelIdResponse = labelService.create(labelCreateRequest);
		return ResponseEntity.ok().body(labelIdResponse);
	}

	@PatchMapping
	public ResponseEntity<LabelIdResponse> update(@Valid @RequestBody LabelUpdateRequest dto) {
		LabelIdResponse labelIdResponse = labelService.update(dto);
		return ResponseEntity.ok().body(labelIdResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		labelService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
