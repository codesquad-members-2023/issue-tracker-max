package codesquard.app.label.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.label.dto.response.LabelDeleteResponse;
import codesquard.app.label.dto.response.LabelReadResponse;
import codesquard.app.label.dto.request.LabelSaveRequest;
import codesquard.app.label.dto.response.LabelSaveResponse;
import codesquard.app.label.dto.request.LabelUpdateRequest;
import codesquard.app.label.dto.response.LabelUpdateResponse;
import codesquard.app.label.service.LabelService;

@RestController
public class LabelController {
	private final LabelService labelService;

	public LabelController(final LabelService labelService) {
		this.labelService = labelService;
	}

	@GetMapping("/api/labels")
	public ResponseEntity<LabelReadResponse> get() {
		LabelReadResponse labelReadResponse = labelService.makeLabelReadResponse();

		return ResponseEntity.status(HttpStatus.OK)
			.body(labelReadResponse.success());
	}

	@PostMapping("/api/labels")
	public ResponseEntity<LabelSaveResponse> save(@Valid @RequestBody final LabelSaveRequest labelSaveRequest) {
		Long labelId = labelService.saveLabel(labelSaveRequest);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(LabelSaveResponse.success(labelId));
	}

	@PutMapping("/api/labels/{labelId}")
	public ResponseEntity<LabelUpdateResponse> update(@PathVariable final Long labelId,
		@Valid @RequestBody LabelUpdateRequest labelUpdateRequest) {
		labelService.updateLabel(labelId, labelUpdateRequest);

		return ResponseEntity.status(HttpStatus.OK)
			.body(LabelUpdateResponse.success());
	}

	@DeleteMapping("/api/labels/{labelId}")
	public ResponseEntity<LabelDeleteResponse> delete(@PathVariable final Long labelId) {
		labelService.deleteLabel(labelId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(LabelDeleteResponse.success());
	}
}
