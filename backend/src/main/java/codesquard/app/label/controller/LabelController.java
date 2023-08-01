package codesquard.app.label.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.label.dto.LabelSaveRequest;
import codesquard.app.label.dto.LabelSaveResponse;
import codesquard.app.label.service.LabelService;

@RestController
public class LabelController {
	private final LabelService labelService;

	public LabelController(LabelService labelService) {
		this.labelService = labelService;
	}

	@PostMapping("/api/labels")
	public ResponseEntity<LabelSaveResponse> save(@Valid @RequestBody LabelSaveRequest labelSaveRequest) {
		Long labelId = labelService.saveLabel(labelSaveRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(LabelSaveResponse.success(true, labelId));
	}
}
