package codesquard.app.issue.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.dto.response.IssueSaveResponse;
import codesquard.app.issue.service.IssueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class IssueController {

	private final IssueService issueService;

	@PostMapping("/api/issues")
	public ResponseEntity<IssueSaveResponse> save(
		@Valid @RequestBody IssueSaveRequest issueSaveRequest) {
		Long userId = 1L;
		Long id = issueService.save(issueSaveRequest, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(new IssueSaveResponse(true, id));
	}
}
