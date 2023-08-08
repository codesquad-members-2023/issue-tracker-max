package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.request.IssueCreateRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.request.IssueStatusRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.request.IssueUpdateRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response.FilterListResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response.FilterResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/api/issues/open")
    public FilterResponse readOpenIssues() {
        return FilterResponse.from(issueService.readOpenIssues());
    }

    @GetMapping("/api/issues/closed")
    public FilterResponse readCloseIssues() {
        return FilterResponse.from(issueService.readClosedIssues());
    }

    @PatchMapping("/api/issues")
    public ApiResponse updateIssuesStatus(@RequestBody IssueStatusRequest request) {
        issueService.updateIssuesStatus(IssueStatusRequest.to(request));
        return ApiResponse.success(HttpStatus.OK);
    }

    @PostMapping("/api/issues")
    public ApiResponse create(@RequestBody IssueCreateRequest request) {
        issueService.createIssue(IssueCreateRequest.to(request));
        return ApiResponse.success(HttpStatus.OK);
    }

    @DeleteMapping("/api/issues/{issueId}")
    public ApiResponse delete(@PathVariable Long issueId) {
        issueService.deleteIssue(issueId);
        return ApiResponse.success(HttpStatus.OK);
    }

    @PatchMapping("/api/issues/{issueId}")
    public ApiResponse updateIssueStatus(@PathVariable Long issueId, @RequestBody IssueStatusRequest request) {
        issueService.updateIssuesStatus(IssueStatusRequest.to(issueId, request));
        return ApiResponse.success(HttpStatus.OK);
    }

    @PutMapping("/api/issues/{issueId}")
    public ApiResponse updateIssue(@PathVariable Long issueId, @RequestBody IssueUpdateRequest request) {
        issueService.updateIssue(IssueUpdateRequest.to(issueId, request));
        return ApiResponse.success(HttpStatus.OK);
    }

    @GetMapping("/api/filters")
    public FilterListResponse readFilters() {
        return FilterListResponse.from(issueService.readFilters());
    }

    @GetMapping("/api/issues")
    public FilterListResponse readFiltersFromIssue() {
        return FilterListResponse.from(issueService.readFiltersFromIssue());
    }
}
