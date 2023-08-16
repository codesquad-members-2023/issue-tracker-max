package codesquad.issueTracker.issue.controller;

import codesquad.issueTracker.global.common.ApiResponse;
import codesquad.issueTracker.issue.dto.*;
import codesquad.issueTracker.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static codesquad.issueTracker.global.exception.SuccessCode.SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/issues")
    public ApiResponse<IssueFilteredResponseDto> getIssues(@RequestParam String status,
                                                           @RequestParam(required = false) List<Long> labels,
                                                           @RequestParam(required = false) Long milestone,
                                                           @RequestParam(required = false) Long writer,
                                                           @RequestParam(required = false) List<Long> assignees,
                                                           @RequestParam(required = false) Boolean comment,
                                                           HttpServletRequest httpServletRequest) {

        Long userId = Long.parseLong(String.valueOf(httpServletRequest.getAttribute("userId")));
        IssueFilterRequestDto issueFilterRequestDto = IssueFilterRequestDto.of(status, labels, milestone, writer,
                assignees, comment);
        IssueFilteredResponseDto issueFilteredResponseDto = issueService.findByFilter(
                issueFilterRequestDto.toIssueSearch(userId));

        return ApiResponse.success(SUCCESS.getStatus(), issueFilteredResponseDto);
    }

    @PostMapping("/issues")
    public ApiResponse<String> postIssues(@Valid @RequestBody IssueWriteRequestDto request,
                                          HttpServletRequest httpServletRequest) {
        Long id = Long.parseLong(String.valueOf(httpServletRequest.getAttribute("userId")));
        issueService.save(request, id);
        return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
    }

    @GetMapping("/issues/labels")
    public ApiResponse<IssueLabelResponseDto> getIssueLabels() {
        IssueLabelResponseDto labels = issueService.getIssueLabels();
        return ApiResponse.success(SUCCESS.getStatus(), labels);
    }

    @GetMapping("/issues/milestones")
    public ApiResponse<IssueMilestoneResponseDto> getIssueMilestones() {
        IssueMilestoneResponseDto milestones = issueService.getIssueMilestones();
        return ApiResponse.success(SUCCESS.getStatus(), milestones);
    }

    @GetMapping("/issues/participants")
    public ApiResponse<IssueUserResponseDto> getIssueUsers() {
        IssueUserResponseDto participants = issueService.getIssueUsers();
        return ApiResponse.success(SUCCESS.getStatus(), participants);
    }

    @GetMapping("/issues/{issueId}")
    public ApiResponse<IssueResponseDto> getIssue(@PathVariable Long issueId) {
        IssueResponseDto issueResponseDto = issueService.getIssueById(issueId);
        return ApiResponse.success(SUCCESS.getStatus(), issueResponseDto);
    }

    @GetMapping("/issues/{issueId}/options")
    public ApiResponse<IssueOptionResponseDto> getIssueOptions(@PathVariable Long issueId) {
        IssueOptionResponseDto issueOptionResponseDto = issueService.getIssueOptions(issueId);
        return ApiResponse.success(SUCCESS.getStatus(), issueOptionResponseDto);
    }

    @PatchMapping("/issues/status")
    public ApiResponse<String> patchStatus(@RequestBody ModifyIssueStatusRequestDto request) {
        issueService.modifyIssueStatus(request);
        return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
    }

    @PatchMapping("/issues/{id}/status")
    public ApiResponse<String> patchInDetailStatus(@PathVariable Long id,
                                                   @RequestBody ModifyIssueStatusRequestDto request) {
        issueService.modifyIssueStatusInDetail(id, request);
        return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
    }

    @PatchMapping("/issues/{id}/content")
    public ApiResponse<ModifyIssueContentResponseDto> patchContent(@PathVariable Long id,
                                                                   @RequestBody ModifyIssueContentRequestDto request) {
        ModifyIssueContentResponseDto response = issueService.modifyIssueContent(id, request);
        return ApiResponse.success(SUCCESS.getStatus(), response);
    }

    @PatchMapping("/issues/{id}/title")
    public ApiResponse<ModifyIssueTitleResponse> patchTitle(@PathVariable Long id,
                                                            @Valid @RequestBody ModifyIssueTitleRequest request) {
        ModifyIssueTitleResponse response = issueService.modifyIssueTitle(id, request);
        return ApiResponse.success(SUCCESS.getStatus(), response);
    }

    @DeleteMapping("/issues/{id}")
    public ApiResponse<String> deleteIssues(@PathVariable Long id) {
        issueService.delete(id);
        return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
    }

    @PatchMapping("/issues/{id}/assignees")
    public ApiResponse<String> patchAssignees(@PathVariable Long id, @RequestBody ModifyAssigneeRequestDto request) {
        issueService.modifyAssignees(id, request);
        return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
    }

    @PatchMapping("/issues/{id}/labels")
    public ApiResponse<String> patchLabels(@PathVariable Long id, @RequestBody ModifyLabelRequestDto request) {
        issueService.modifyLabels(id, request);
        return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
    }

    @PatchMapping("/issues/{id}/milestones")
    public ApiResponse<String> patchMilestone(@PathVariable Long id, @RequestBody ModifyIssueMilestoneDto request) {
        issueService.modifyMilestone(id, request);
        return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
    }

    @PostMapping("/upload")
    public ApiResponse<IssueFileResponseDto> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) {
        IssueFileResponseDto response = issueService.uploadImg(multipartFile);
        return ApiResponse.success(SUCCESS.getStatus(), response);
    }
}
