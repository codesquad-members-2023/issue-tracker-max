package codesquard.app.assignee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.assignee.controller.response.AssigneeReadWrapperResponse;
import codesquard.app.assignee.service.AssigneeQueryService;

@RequestMapping(path = "/api")
@RestController
public class AssigneeRestController {

	private final AssigneeQueryService assigneeQueryService;

	public AssigneeRestController(AssigneeQueryService assigneeQueryService) {
		this.assigneeQueryService = assigneeQueryService;
	}

	@GetMapping("/assignees")
	public ApiResponse<AssigneeReadWrapperResponse> listAssignee() {
		return ApiResponse.ok(new AssigneeReadWrapperResponse(assigneeQueryService.findAll()));
	}
}
