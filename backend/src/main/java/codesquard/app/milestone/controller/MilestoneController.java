package codesquard.app.milestone.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.api.response.MilestoneResponseMessage;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
import codesquard.app.milestone.dto.request.MilestoneStatusUpdateRequest;
import codesquard.app.milestone.dto.request.MilestoneUpdateRequest;
import codesquard.app.milestone.dto.response.MilestoneDeleteResponse;
import codesquard.app.milestone.dto.response.MilestoneReadResponse;
import codesquard.app.milestone.dto.response.MilestoneSaveResponse;
import codesquard.app.milestone.dto.response.MilestoneStatusUpdateResponse;
import codesquard.app.milestone.dto.response.MilestoneUpdateResponse;
import codesquard.app.milestone.entity.MilestoneStatus;
import codesquard.app.milestone.service.MilestoneService;
import codesquard.app.user.annotation.Login;

@RequestMapping(path = "/api/milestones")
@RestController
public class MilestoneController {
	private final MilestoneService milestoneService;

	public MilestoneController(final MilestoneService milestoneService) {
		this.milestoneService = milestoneService;
	}

	@GetMapping
	public ApiResponse<MilestoneReadResponse> get(
		@RequestParam(name = "state", defaultValue = "opened") final String openedString,
		@RequestParam(name = "state", defaultValue = "closed") final String closedString) {
		MilestoneReadResponse milestoneReadResponse = milestoneService.makeMilestoneResponse(
			MilestoneStatus.chooseStatus(openedString, closedString));
		return ApiResponse.of(HttpStatus.OK, MilestoneResponseMessage.MILESTONE_GET_SUCCESS, milestoneReadResponse);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ApiResponse<MilestoneSaveResponse> save(
		@Valid @RequestBody final MilestoneSaveRequest milestoneSaveRequest,
		@Login AuthenticateUser user) {
		Long userId = user.toEntity().getId();
		Long milestoneId = milestoneService.saveMilestone(milestoneSaveRequest, userId);
		return ApiResponse.of(HttpStatus.CREATED, MilestoneResponseMessage.MILESTONE_SAVE_SUCCESS,
			MilestoneSaveResponse.success(milestoneId));
	}

	@PutMapping("/{milestoneId}")
	public ApiResponse<MilestoneUpdateResponse> update(@PathVariable final Long milestoneId,
		@Valid @RequestBody final MilestoneUpdateRequest milestoneUpdateRequest,
		@Login AuthenticateUser user) {
		Long userId = user.toEntity().getId();
		milestoneService.updateMilestone(milestoneId, milestoneUpdateRequest, userId);
		return ApiResponse.of(HttpStatus.OK, MilestoneResponseMessage.MILESTONE_UPDATE_SUCCESS, null);
	}

	@PatchMapping("/{milestoneId}/status")
	public ApiResponse<MilestoneStatusUpdateResponse> updateStatus(@PathVariable final Long milestoneId,
		@Valid @RequestBody final MilestoneStatusUpdateRequest milestoneStatusUpdateRequest,
		@Login AuthenticateUser user) {
		Long userId = user.toEntity().getId();
		milestoneService.updateMilestoneStatus(milestoneId, milestoneStatusUpdateRequest);
		return ApiResponse.of(HttpStatus.OK, MilestoneResponseMessage.MILESTONE_UPDATE_STATUS_SUCCESS, null);
	}

	@DeleteMapping("/{milestoneId}")
	public ApiResponse<MilestoneDeleteResponse> delete(@PathVariable final Long milestoneId,
		@Login AuthenticateUser user) {
		Long userId = user.toEntity().getId();
		milestoneService.deleteMilestone(milestoneId);
		return ApiResponse.of(HttpStatus.OK, MilestoneResponseMessage.MILESTONE_DELETE_SUCCESS, null);
	}
}
