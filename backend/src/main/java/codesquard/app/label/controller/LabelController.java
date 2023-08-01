package codesquard.app.label.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.label.dto.LabelSavedRequest;
import codesquard.app.label.dto.LabelSavedResponse;
import codesquard.app.label.service.LabelService;

@RestController
public class LabelController {
	private final LabelService labelService;

	public LabelController(LabelService labelService) {
		this.labelService = labelService;
	}

	@PostMapping("/api/labels")
	public ResponseEntity<LabelSavedResponse> save(@Valid @RequestBody LabelSavedRequest labelSavedRequest) {
		Long labelId = labelService.saveLabel(labelSavedRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(LabelSavedResponse.success(true, labelId));
	}
}
