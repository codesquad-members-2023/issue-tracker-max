package codesquard.app.assignee.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.assignee.controller.response.AssigneeReadResponse;
import codesquard.app.assignee.controller.response.AssigneeReadWrapperResponse;
import codesquard.app.assignee.service.AssigneeQueryService;
import codesquard.app.assignee.service.response.AssigneeReadServiceResponse;

@RequestMapping(path = "/api")
@RestController
public class AssigneeRestController {

	private final AssigneeQueryService assigneeQueryService;

	public AssigneeRestController(AssigneeQueryService assigneeQueryService) {
		this.assigneeQueryService = assigneeQueryService;
	}

	@GetMapping("/assignees")
	public ApiResponse<AssigneeReadWrapperResponse> listAssignee() {
		List<AssigneeReadResponse> assigneeReadResponses = assigneeQueryService.findAll()
			.stream()
			.map(AssigneeReadServiceResponse::toAssigneeReadResponse)
			.collect(Collectors.toUnmodifiableList());
		return ApiResponse.ok(new AssigneeReadWrapperResponse(assigneeReadResponses));
	}
}
