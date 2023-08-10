package codesquad.issueTracker.label.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.global.ApiResponse;
import codesquad.issueTracker.label.controller.dto.LabelRequestDto;
import codesquad.issueTracker.label.controller.dto.CreateLabelResponseDto;
import codesquad.issueTracker.label.controller.dto.LabelResponseDto;
import codesquad.issueTracker.label.service.LabelService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LabelController {

	private final LabelService labelService;

	@PostMapping("/labels")
	public ApiResponse<CreateLabelResponseDto> create(@RequestBody LabelRequestDto labelRequestDto) {
		CreateLabelResponseDto createLabelResponseDto = labelService.save(labelRequestDto);
		return ApiResponse.success(SUCCESS.getStatus(), createLabelResponseDto);
	}

	@PatchMapping("labels/{labelId}")
	public ApiResponse<String> modify(@PathVariable Long labelId, @RequestBody LabelRequestDto labelRequestDto) {
		labelService.modify(labelId, labelRequestDto);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@DeleteMapping("labels/{labelId}")
	public ApiResponse<String> delete(@PathVariable Long labelId) {
		labelService.delete(labelId);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@GetMapping("labels")
	public ApiResponse<LabelResponseDto> findAll(){
		LabelResponseDto labelResponseDto = labelService.findAll();
		return ApiResponse.success(SUCCESS.getStatus(), labelResponseDto);
	}
}
