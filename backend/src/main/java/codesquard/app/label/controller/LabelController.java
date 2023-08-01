package codesquard.app.label.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.label.dto.LabelRequestDto;
import codesquard.app.label.dto.LabelResponseDto;
import codesquard.app.label.service.LabelService;

@RestController
public class LabelController {
	private final LabelService labelService;

	public LabelController(LabelService labelService) {
		this.labelService = labelService;
	}

	@PostMapping("/api/labels")
	public ResponseEntity<LabelResponseDto> save(@RequestBody LabelRequestDto labelRequestDto) {
		Long labelId = labelService.saveLabel(labelRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(LabelResponseDto.success(true, labelId));
	}
}
