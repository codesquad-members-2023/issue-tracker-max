package org.presents.issuetracker.label.controller;

import java.util.List;

import org.presents.issuetracker.global.dto.response.LabelResponse;
import org.presents.issuetracker.label.dto.request.LabelCreateRequest;
import org.presents.issuetracker.label.dto.request.LabelUpdateRequest;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
import org.presents.issuetracker.label.service.LabelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/labels")
public class LabelController {

	private final LabelService labelService;

	public LabelController(LabelService labelService) {
		this.labelService = labelService;
	}

	@GetMapping("/previews")
	public ResponseEntity<List<LabelPreviewResponse>> getLabelPreviews() {
		List<LabelPreviewResponse> labelPreviews = labelService.getLabels();
		return ResponseEntity.ok().body(labelPreviews);
	}

	@PostMapping
	public ResponseEntity<LabelResponse> create(@RequestBody LabelCreateRequest labelCreateRequest) {
		LabelResponse labelResponse = labelService.create(labelCreateRequest);
		return ResponseEntity.ok().body(labelResponse);
	}

	@PatchMapping
	public ResponseEntity<LabelResponse> update(@RequestBody LabelUpdateRequest dto) {
		LabelResponse labelResponse = labelService.update(dto);
		return ResponseEntity.ok().body(labelResponse);
	}
}
