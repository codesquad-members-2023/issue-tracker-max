package codesquard.app.label.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.api.response.LabelResponseMessage;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.label.dto.request.LabelSaveRequest;
import codesquard.app.label.dto.request.LabelUpdateRequest;
import codesquard.app.label.dto.response.LabelDeleteResponse;
import codesquard.app.label.dto.response.LabelReadResponse;
import codesquard.app.label.dto.response.LabelSaveResponse;
import codesquard.app.label.dto.response.LabelUpdateResponse;
import codesquard.app.label.service.LabelService;
import codesquard.app.user.annotation.Login;

@RequestMapping(path = "/api/labels")
@RestController
public class LabelController {
	private final LabelService labelService;

	public LabelController(final LabelService labelService) {
		this.labelService = labelService;
	}

	@GetMapping
	public ApiResponse<LabelReadResponse> get() {
		LabelReadResponse labelReadResponse = labelService.makeLabelReadResponse();
		return ApiResponse.of(HttpStatus.OK, LabelResponseMessage.LABEL_GET_SUCCESS,
			labelReadResponse);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ApiResponse<LabelSaveResponse> save(@Valid @RequestBody final LabelSaveRequest labelSaveRequest,
		@Login AuthenticateUser user) {
		Long userId = user.toEntity().getId();
		Long labelId = labelService.saveLabel(labelSaveRequest, userId);
		return ApiResponse.of(HttpStatus.CREATED, LabelResponseMessage.LABEL_SAVE_SUCCESS,
			LabelSaveResponse.success(labelId));
	}

	@PutMapping("/{labelId}")
	public ApiResponse<LabelUpdateResponse> update(@PathVariable final Long labelId,
		@Valid @RequestBody LabelUpdateRequest labelUpdateRequest,
		@Login AuthenticateUser user) {
		Long userId = user.toEntity().getId();
		labelService.updateLabel(labelId, userId, labelUpdateRequest);
		return ApiResponse.of(HttpStatus.OK, LabelResponseMessage.LABEL_UPDATE_SUCCESS, null);
	}

	@DeleteMapping("/{labelId}")
	public ApiResponse<LabelDeleteResponse> delete(@PathVariable final Long labelId,
		@Login AuthenticateUser user) {
		Long userId = user.toEntity().getId();
		labelService.deleteLabel(labelId, userId);
		return ApiResponse.of(HttpStatus.OK, LabelResponseMessage.LABEL_DELETE_SUCCESS, null);
	}
}
