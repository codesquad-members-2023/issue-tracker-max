package com.issuetracker.issue.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetracker.issue.application.IssueService;
import com.issuetracker.issue.ui.dto.AuthorResponses;
import com.issuetracker.issue.ui.dto.AssigneesResponses;
import com.issuetracker.issue.ui.dto.IssueCreateRequest;
import com.issuetracker.issue.ui.dto.IssueCreateResponse;
import com.issuetracker.issue.ui.dto.AssignedLabelResponses;
import com.issuetracker.issue.ui.dto.IssueDetailResponse;
import com.issuetracker.issue.ui.dto.IssueSearchRequest;
import com.issuetracker.issue.ui.dto.IssueUpdateRequest;
import com.issuetracker.issue.ui.dto.IssuesSearchResponse;
import com.issuetracker.member.application.MemberService;
import com.issuetracker.milestone.application.MilestoneService;
import com.issuetracker.milestone.ui.dto.MilestonesSearchResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

	private final IssueService issueService;
	private final MemberService memberService;
	private final MilestoneService milestoneService;

	@GetMapping
	public ResponseEntity<IssuesSearchResponse> showIssues(IssueSearchRequest issueSearchRequest) {
		IssuesSearchResponse issuesSearchResponse = IssuesSearchResponse.of(
			issueService.findIssuesCount(),
			issueService.search(issueSearchRequest.toIssueSearchData(1L))
		);
		return ResponseEntity.ok().body(issuesSearchResponse);
	}

	@PostMapping
	public ResponseEntity<IssueCreateResponse> createIssue(@RequestBody @Valid IssueCreateRequest issueCreateRequest) {
		IssueCreateResponse issueCreateResponse = IssueCreateResponse.from(issueService.create(issueCreateRequest.toIssueCreateData(1L)));
		return ResponseEntity.created(URI.create("/issues/" + issueCreateResponse.getId()))
			.body(issueCreateResponse);
	}
  
  	@GetMapping("/milestones")
	public ResponseEntity<MilestonesSearchResponse> showMilestonesForFilter() {
		return ResponseEntity.ok().body(MilestonesSearchResponse.from(milestoneService.searchMilestonesForFilter()));
	}

	@GetMapping("/authors")
	public ResponseEntity<AuthorResponses> showAuthors() {
		return ResponseEntity.ok().body(AuthorResponses.from(memberService.searchAuthors()));
  }
  
	@GetMapping("/assignees")
	public ResponseEntity<AssigneesResponses> showAssignees() {
		AssigneesResponses assigneesResponses = AssigneesResponses.from(issueService.searchAssignee());
		return ResponseEntity.ok().body(assigneesResponses);
	}

	@GetMapping("/labels")
	public ResponseEntity<AssignedLabelResponses> showLabels() {
		AssignedLabelResponses assignedLabelResponses = AssignedLabelResponses.from(issueService.searchAssignedLabel());
		return ResponseEntity.ok().body(assignedLabelResponses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<IssueDetailResponse> showIssueDetail(@PathVariable Long id) {
		IssueDetailResponse issueDetailResponse = IssueDetailResponse.from(issueService.findById(id));
		return ResponseEntity.ok().body(issueDetailResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIssue(@PathVariable long id) {
		issueService.deleteIssue(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/open")
	public ResponseEntity<Void> updateIssueOpen(@PathVariable long id, @RequestBody IssueUpdateRequest issueUpdateRequest) {
		issueService.updateIssueOpen(issueUpdateRequest.toIssueUpdateDataOpen(id));
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/title")
	public ResponseEntity<Void> updateIssueTitle(@PathVariable long id, @RequestBody IssueUpdateRequest issueUpdateRequest) {
		issueService.updateIssueTitle(issueUpdateRequest.toIssueUpdateDataTitle(id));
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/content")
	public ResponseEntity<Void> updateIssueContent(@PathVariable long id, @RequestBody IssueUpdateRequest issueUpdateRequest) {
		issueService.updateIssueContent(issueUpdateRequest.toIssueUpdateDataContent(id));
		return ResponseEntity.noContent().build();
	}
}
