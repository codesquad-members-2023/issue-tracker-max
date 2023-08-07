package codesquard.app.assignee.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.assignee.service.AssigneeQueryService;
import codesquard.app.assignee.service.response.AssigneeReadResponse;

@RequestMapping(path = "/api")
@RestController
public class AssigneeRestController {

	private final AssigneeQueryService assigneeQueryService;

	public AssigneeRestController(AssigneeQueryService assigneeQueryService) {
		this.assigneeQueryService = assigneeQueryService;
	}

	@GetMapping("/assignees")
	public ResponseEntity<List<AssigneeReadResponse>> listAssignee() {
		return ResponseEntity.ok(assigneeQueryService.findAll());
	}
}
