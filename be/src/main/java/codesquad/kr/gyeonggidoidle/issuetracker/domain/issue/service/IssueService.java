package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository.CommentRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Comment;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Issue;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.IssueRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueCreateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueStatusCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.condition.IssueUpdateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.FilterInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.FilterListInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.LabelRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.vo.MemberDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.MilestoneRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.StatRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
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
    private final LabelRepository labelRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final MilestoneRepository milestoneRepository;

    public FilterInformation readOpenIssues() {
        StatVO statVO = statRepository.countOverallStats();
        List<IssueVO> issueVOs = issueRepository.findOpenIssues();
        List<Long> issueIds = getIssueIds(issueVOs);
        Map<Long, List<LabelVO>> labelVOs = labelRepository.findAllByIssueIds(issueIds);
        Map<Long, List<String>> assigneeProfiles = memberRepository.findAllProfilesByIssueIds(issueIds);

        return FilterInformation.from(statVO, issueVOs, labelVOs, assigneeProfiles);
    }

    public FilterInformation readClosedIssues() {
        StatVO statVO = statRepository.countOverallStats();
        List<IssueVO> issueVOs = issueRepository.findClosedIssues();
        List<Long> issueIds = getIssueIds(issueVOs);
        Map<Long, List<LabelVO>> labelVOs = labelRepository.findAllByIssueIds(issueIds);
        Map<Long, List<String>> assigneeProfiles = memberRepository.findAllProfilesByIssueIds(issueIds);

        return FilterInformation.from(statVO, issueVOs, labelVOs, assigneeProfiles);
    }

    public void updateIssuesStatus(IssueStatusCondition condition) {
        issueRepository.updateIssuesStatus(IssueStatusCondition.to(condition));
    }

    public void createIssue(IssueCreateCondition condition) {
        Issue issue = IssueCreateCondition.toIssue(condition);
        Long createdId = issueRepository.createIssue(issue);
        Comment comment = IssueCreateCondition.toComment(createdId, condition);
        Long fileId = null;
        if (comment.isFileExist()) {
            fileId = commentRepository.updateFile(comment.getFile());
        }
        if (comment.isContentsExist()) {
            commentRepository.createComment(fileId, comment);
        }
        memberRepository.addIssueAssignees(createdId, condition.getAssignees());
        labelRepository.addIssueLabels(createdId, condition.getLabels());
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

    public FilterListInformation readFilters() {
        List<MemberDetailsVO> members = memberRepository.findAllFilters();
        List<LabelDetailsVO> labels = labelRepository.findAllFilters();
        List<MilestoneDetailsVO> milestones = milestoneRepository.findAllFilters();
        return FilterListInformation.from(members, members, labels, milestones);
    }

    public FilterListInformation readFiltersFromIssue() {
        List<MemberDetailsVO> members = memberRepository.findAllFilters();
        List<LabelDetailsVO> labels = labelRepository.findAllFilters();
        List<MilestoneDetailsVO> milestones = milestoneRepository.findAllFilters();
        List<Long> milestoneIds = getMilestoneIds(milestones);
        Map<Long, IssueByMilestoneVO> issuesCountByMilestoneIds = statRepository.findIssuesCountByMilestoneIds(
                milestoneIds);
        return FilterListInformation.from(members, labels, milestones, issuesCountByMilestoneIds);
    }

    private List<Long> getIssueIds(List<IssueVO> issueVOs) {
        return issueVOs.stream()
                .map(IssueVO::getId)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Long> getMilestoneIds(List<MilestoneDetailsVO> milestoneDetailsVOs) {
        return milestoneDetailsVOs.stream()
                .map(MilestoneDetailsVO::getId)
                .collect(Collectors.toUnmodifiableList());
    }
}
