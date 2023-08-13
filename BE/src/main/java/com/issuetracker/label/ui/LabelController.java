package com.issuetracker.label.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetracker.label.application.LabelService;
import com.issuetracker.label.ui.dto.LabelCreateRequest;
import com.issuetracker.label.ui.dto.LabelCreateResponse;
import com.issuetracker.label.ui.dto.LabelDeleteRequest;
import com.issuetracker.label.ui.dto.LabelUpdateRequest;
import com.issuetracker.label.ui.dto.LabelsResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/labels")
@RequiredArgsConstructor
public class LabelController {

	private final LabelService labelService;

	@PostMapping
	public ResponseEntity<LabelCreateResponse> createLabel(@RequestBody @Valid LabelCreateRequest labelCreateRequest) {
		LabelCreateResponse labelCreateResponse
			= LabelCreateResponse.from(
			labelService.create(labelCreateRequest.toLabelCreateInputData())
		);

		return ResponseEntity.created(
			URI.create("/api/labels/" + labelCreateResponse.getId())
		).body(labelCreateResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateLabel(@PathVariable Long id,
		@RequestBody @Valid LabelUpdateRequest labelUpdateRequest) {

		labelService.update(labelUpdateRequest.toLabelUpdateInputData(id));
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLabel(@PathVariable Long id) {

		labelService.delete(LabelDeleteRequest.toLabelDeleteInputData(id));
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<LabelsResponse> showLabels() {
		return ResponseEntity.ok()
			.body(LabelsResponse.from(labelService.search()));
	}
}
