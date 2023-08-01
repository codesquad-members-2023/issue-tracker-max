package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response.FilterResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
