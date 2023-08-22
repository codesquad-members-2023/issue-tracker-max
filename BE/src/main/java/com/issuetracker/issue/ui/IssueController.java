package com.issuetracker.issue.ui;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
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

import com.issuetracker.common.util.MemberIdExtractor;
import com.issuetracker.issue.application.AssignedLabelService;
import com.issuetracker.issue.application.AssigneeService;
import com.issuetracker.issue.application.IssueCommentService;
import com.issuetracker.issue.application.IssueService;
import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.application.dto.assignedlabel.AssignedLabelCreateData;
import com.issuetracker.issue.application.dto.assignee.AssigneeCreateData;
import com.issuetracker.issue.application.dto.comment.IssueCommentCreateData;
import com.issuetracker.issue.ui.dto.IssueCreateRequest;
import com.issuetracker.issue.ui.dto.IssueCreateResponse;
import com.issuetracker.issue.ui.dto.IssueDetailResponse;
import com.issuetracker.issue.ui.dto.IssueSearchRequest;
import com.issuetracker.issue.ui.dto.IssueUpdateAllOpenRequest;
import com.issuetracker.issue.ui.dto.IssueUpdateRequest;
import com.issuetracker.issue.ui.dto.IssuesSearchResponse;
import com.issuetracker.issue.ui.dto.assignedlabel.AssignedLabelCandidatesResponse;
import com.issuetracker.issue.ui.dto.assignedlabel.AssignedLabelCreateRequest;
import com.issuetracker.issue.ui.dto.assignedlabel.AssignedLabelCreateResponse;
import com.issuetracker.issue.ui.dto.assignedlabel.AssignedLabelResponses;
import com.issuetracker.issue.ui.dto.assignee.AssigneeCandidatesResponse;
import com.issuetracker.issue.ui.dto.assignee.AssigneeCreateRequest;
import com.issuetracker.issue.ui.dto.assignee.AssigneesCreateResponse;
import com.issuetracker.issue.ui.dto.assignee.AssigneesResponses;
import com.issuetracker.issue.ui.dto.assignee.AuthorResponses;
import com.issuetracker.issue.ui.dto.comment.IssueCommentCreateRequest;
import com.issuetracker.issue.ui.dto.comment.IssueCommentCreateResponse;
import com.issuetracker.issue.ui.dto.comment.IssueCommentUpdateRequest;
import com.issuetracker.issue.ui.dto.milestone.MilestoneCandidatesResponse;
import com.issuetracker.label.application.LabelService;
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
	private final AssigneeService assigneeService;
	private final AssignedLabelService assignedLabelService;
	private final IssueCommentService issueCommentService;
	private final LabelService labelService;

	@GetMapping
	public ResponseEntity<IssuesSearchResponse> showIssues(IssueSearchRequest issueSearchRequest,
		HttpServletRequest request) {
		IssuesSearchResponse issuesSearchResponse = IssuesSearchResponse.of(
			issueService.findIssuesCount(),
			issueService.search(issueSearchRequest.toIssueSearchData(MemberIdExtractor.extractMemberId(request)))
		);
		return ResponseEntity.ok().body(issuesSearchResponse);
	}

	@PostMapping
	public ResponseEntity<IssueCreateResponse> createIssue(@RequestBody @Valid IssueCreateRequest issueCreateRequest,
		HttpServletRequest request) {
		IssueCreateInputData issueCreateInputData = issueCreateRequest.toIssueCreateData(
			MemberIdExtractor.extractMemberId(request));
		IssueCreateResponse issueCreateResponse = IssueCreateResponse.from(issueService.create(issueCreateInputData));
		return ResponseEntity.created(URI.create("/issues/" + issueCreateResponse.getId()))
			.body(issueCreateResponse);
	}

	@GetMapping("/milestones")
	public ResponseEntity<MilestonesSearchResponse> showMilestonesForFilter() {
		return ResponseEntity.ok().body(MilestonesSearchResponse.from(milestoneService.searchMilestonesForFilter()));
	}

	@GetMapping("/authors")
	public ResponseEntity<AuthorResponses> showAuthors() {
		return ResponseEntity.ok().body(AuthorResponses.from(memberService.searchMember()));
	}

	@GetMapping("/assignees")
	public ResponseEntity<AssigneesResponses> showAssignees() {
		AssigneesResponses assigneesResponses = AssigneesResponses.from(memberService.searchMember());
		return ResponseEntity.ok().body(assigneesResponses);
	}

	@GetMapping("/labels")
	public ResponseEntity<AssignedLabelResponses> showLabels() {
		AssignedLabelResponses assignedLabelResponses = AssignedLabelResponses.from(
			labelService.searchOrderByTitle());
		return ResponseEntity.ok().body(assignedLabelResponses);
	}

	@PatchMapping("/open-all")
	public ResponseEntity<Void> updateAllOpen(@RequestBody IssueUpdateAllOpenRequest issueUpdateAllOpenRequest) {
		issueService.updateAllIssueOpen(issueUpdateAllOpenRequest.toIssueUpdateAllOpenData());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<IssueDetailResponse> showIssueDetail(@PathVariable Long id) {
		IssueDetailResponse issueDetailResponse = IssueDetailResponse.from(issueService.findById(id));
		return ResponseEntity.ok().body(issueDetailResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
		issueService.deleteIssue(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/open")
	public ResponseEntity<Void> updateIssueOpen(@PathVariable Long id,
		@RequestBody IssueUpdateRequest issueUpdateRequest) {
		issueService.updateIssueOpen(issueUpdateRequest.toIssueUpdateDataOpen(id));
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/title")
	public ResponseEntity<Void> updateIssueTitle(@PathVariable Long id,
		@RequestBody @Valid IssueUpdateRequest issueUpdateRequest) {
		issueService.updateIssueTitle(issueUpdateRequest.toIssueUpdateDataTitle(id));
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/content")
	public ResponseEntity<Void> updateIssueContent(@PathVariable Long id,
		@RequestBody @Valid IssueUpdateRequest issueUpdateRequest) {
		issueService.updateIssueContent(issueUpdateRequest.toIssueUpdateDataContent(id));
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/milestone")
	public ResponseEntity<Void> updateIssueMilestone(@PathVariable Long id,
		@RequestBody @Valid IssueUpdateRequest issueUpdateRequest) {
		issueService.updateIssueMilestone(issueUpdateRequest.toIssueUpdateDataMilestone(id));
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/comments")
	public ResponseEntity<IssueCommentCreateResponse> createIssueComment(@PathVariable Long id, @RequestBody @Valid
	IssueCommentCreateRequest issueCommentCreateRequest, HttpServletRequest request) {
		IssueCommentCreateData issueCommentCreateData = issueCommentCreateRequest.toIssueCommentCreateData(id,
			MemberIdExtractor.extractMemberId(request));
		IssueCommentCreateResponse issueCommentCreateResponse = IssueCommentCreateResponse.from(
			issueCommentService.createIssueComment(issueCommentCreateData));
		return ResponseEntity.ok().body(issueCommentCreateResponse);
	}

	@PatchMapping("/{id}/comments/{comment-id}")
	public ResponseEntity<Void> updateIssueCommentContent(@PathVariable Long id,
		@PathVariable("comment-id") Long commentId,
		@RequestBody @Valid IssueCommentUpdateRequest issueCommentUpdateRequest) {
		issueCommentService.updateIssueCommentContent(
			issueCommentUpdateRequest.toIssueCommentUpdateData(id, commentId));
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/assignee-candidates")
	public ResponseEntity<AssigneeCandidatesResponse> showAssigneesForIssueUpdate(
		@PathVariable Long id) {

		return ResponseEntity.ok()
			.body(
				AssigneeCandidatesResponse.from(
					assigneeService.searchAssigneeCandidates(id)
				)
			);
	}

	@GetMapping("/{id}/label-candidates")
	public ResponseEntity<AssignedLabelCandidatesResponse> showLabelsForIssueUpdate(
		@PathVariable Long id) {

		return ResponseEntity.ok()
			.body(
				AssignedLabelCandidatesResponse.from(
					assignedLabelService.searchLabelCandidates(id)
				)
			);
	}

	@GetMapping("/{id}/milestone-candidates")
	public ResponseEntity<MilestoneCandidatesResponse> showMilestonesForIssueUpdate(
		@PathVariable Long id) {

		return ResponseEntity.ok()
			.body(
				MilestoneCandidatesResponse.from(
					milestoneService.searchMilestoneCandidates(id)
				)
			);
	}

	@PostMapping("/{id}/assignees")
	public ResponseEntity<AssigneesCreateResponse> createAssignee(@PathVariable Long id,
		@RequestBody AssigneeCreateRequest assigneeCreateRequest) {
		AssigneeCreateData assigneeCreateData = assigneeCreateRequest.toAssigneeCreateData(id);
		AssigneesCreateResponse assigneesCreateResponse = AssigneesCreateResponse.from(
			assigneeService.createAssignee(assigneeCreateData));
		return ResponseEntity.ok().body(assigneesCreateResponse);
	}

	@DeleteMapping("/{id}/assignees/{member-id}")
	public ResponseEntity<Void> deleteAssignee(@PathVariable Long id, @PathVariable("member-id") Long memberId) {
		assigneeService.deleteAssignee(id, memberId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/assigned-labels")
	public ResponseEntity<AssignedLabelCreateResponse> createAssignedLabel(@PathVariable Long id,
		@RequestBody AssignedLabelCreateRequest assignedLabelCreateRequest) {
		AssignedLabelCreateData assignedLabelCreateData = assignedLabelCreateRequest.toAssignedLabelCreateData(id);
		AssignedLabelCreateResponse assignedLabelCreateResponse = AssignedLabelCreateResponse.from(
			assignedLabelService.createAssignedLabel(assignedLabelCreateData));
		return ResponseEntity.ok().body(assignedLabelCreateResponse);
	}

	@DeleteMapping("/{id}/assigned-labels/{label-id}")
	public ResponseEntity<Void> deleteAssignedLabel(@PathVariable Long id, @PathVariable("label-id") Long labelId) {
		assignedLabelService.deleteAssignedLabel(id, labelId);
		return ResponseEntity.noContent().build();
	}
}
