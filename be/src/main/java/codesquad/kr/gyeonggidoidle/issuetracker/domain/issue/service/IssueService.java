package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository.CommentRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Comment;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.SearchFilter;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Issue;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.IssueSearchRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.IssueRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueSearchResult;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueCreateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueStatusPatchCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueUpdateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.SearchInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.LabelRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.StatRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final StatRepository statRepository;
    private final IssueRepository issueRepository;
    private final IssueSearchRepository issueSearchRepository;
    private final LabelRepository labelRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public SearchInformation findIssuesBySearchFilter(String searchFilterCondition) {
        StatVO statVO = statRepository.countOverallStats();
        SearchFilter searchFilter = SearchFilter.from(searchFilterCondition);
        List<IssueSearchResult> issueSearchResults = issueSearchRepository.findBySearchFilter(searchFilter);
        List<Long> issueIds = getIssueIds(issueSearchResults);
        Map<Long, List<LabelVO>> labelVOs = labelRepository.findAllByIssueIds(issueIds);
        Map<Long, List<String>> assigneeProfiles = memberRepository.findAllProfilesByIssueIds(issueIds);

        return SearchInformation.from(statVO, issueSearchResults, labelVOs, assigneeProfiles, searchFilterCondition);
    }

    public void createIssue(IssueCreateCondition condition) {
        Issue issue = IssueCreateCondition.toIssue(condition);
        Long createdId = issueRepository.saveIssue(issue);
        Comment comment = null;

        if (condition.hasComment()) {
            comment = IssueCreateCondition.toComment(createdId, condition);
            Long fileUrl = comment.isFileExist()? commentRepository.updateFile(comment.getFile()) : null;
            if (comment.isContentsExist()) {
                commentRepository.createComment(fileUrl, comment);
            }
        }
        if (condition.hasAssignees()) {
            memberRepository.addIssueAssignees(createdId, condition.getAssignees());
        }
        if (condition.hasLabels()) {
            labelRepository.addIssueLabels(createdId, condition.getLabels());
        }
    }

    public void updateIssuesStatus(IssueStatusPatchCondition condition) {
        issueRepository.updateIssuesStatus(IssueStatusPatchCondition.to(condition));
    }

    public void deleteIssue(Long issueId) {
        issueRepository.deleteIssue(issueId);
    }

    public void updateIssue(IssueUpdateCondition condition) {
        issueRepository.updateIssue(IssueUpdateCondition.to(condition));
        if (!condition.getAssignees().isEmpty()) {
            memberRepository.updateIssueAssignees(condition.getIssueId(), condition.getAssignees());
        }
        if (!condition.getLabels().isEmpty()) {
            labelRepository.updateIssueLabels(condition.getIssueId(), condition.getLabels());
        }
    }

    private List<Long> getIssueIds(List<IssueSearchResult> issueSearchResults) {
        return issueSearchResults.stream()
                .map(IssueSearchResult::getId)
                .collect(Collectors.toUnmodifiableList());
    }
}
