package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.IssueRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.FilterInformation;
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
    private final LabelRepository labelRepository;
    private final MemberRepository memberRepository;

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

    private List<Long> getIssueIds(List<IssueVO> issueVOs) {
        return issueVOs.stream()
                .map(IssueVO::getId)
                .collect(Collectors.toUnmodifiableList());
    }
}
