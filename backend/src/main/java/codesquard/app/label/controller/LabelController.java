package codesquard.app.label.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.label.dto.LabelRequest;
import codesquard.app.label.dto.LabelResponse;
import codesquard.app.label.service.LabelService;

@RestController
public class LabelController {
	private final LabelService labelService;

	public LabelController(LabelService labelService) {
		this.labelService = labelService;
	}

	@PostMapping("/api/labels")
	public ResponseEntity<LabelResponse> save(@RequestBody LabelRequest labelRequest) {
		Long labelId = labelService.saveLabel(labelRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(LabelResponse.success(true, labelId));
	}
}
