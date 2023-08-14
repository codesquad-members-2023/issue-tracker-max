package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.request.IssueCreateRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.request.IssueStatusRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.request.IssueUpdateRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.FilterListResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.FilterResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.IssueService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

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
    @GetMapping("/api/issues/filtered")
    public FilterResponse readFiltedIssues(@RequestParam(name = "q", required = false) String encodedQuery) {
        if (encodedQuery == null || encodedQuery.isEmpty()) {
            return FilterResponse.from(issueService.readFilteredIssues("is:open"));
        }
        String filterCondition = UriUtils.decode(encodedQuery, "UTF-8");
        return FilterResponse.from(issueService.readFilteredIssues(filterCondition));
    }

    @PatchMapping("/api/issues")
    public ApiResponse updateIssuesStatus(@RequestBody IssueStatusRequest request) {
        issueService.updateIssuesStatus(IssueStatusRequest.to(request));
        return ApiResponse.success(HttpStatus.OK);
    }

    @PostMapping("/api/issues")
    public ApiResponse create(@RequestBody IssueCreateRequest request, HttpServletRequest httpServletRequest) {
        issueService.createIssue(IssueCreateRequest.to(request,
                Long.valueOf(String.valueOf(httpServletRequest.getAttribute("memberId")))
                )
        );
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
