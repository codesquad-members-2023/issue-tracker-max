package kr.codesquad.issuetracker.presentation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.IssueService;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.auth.AuthPrincipal;
import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/issues")
@RequiredArgsConstructor
@RestController
public class IssueController {

	private final IssueService issueService;

	@GetMapping
	public ResponseEntity<List<IssueSimpleMapper>> findAll() {
		return ResponseEntity.ok(issueService.findAll());
	}

	@PostMapping
	public ResponseEntity<Void> register(@AuthPrincipal Integer userId,
		@Valid @RequestBody IssueRegisterRequest request) {
		Integer issueId = issueService.register(userId, request);
		return ResponseEntity.status(HttpStatus.FOUND)
			.header(HttpHeaders.LOCATION, "/api/issues/" + issueId)
			.build();
	}
}
