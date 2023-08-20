package codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.service.information.FilterInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.LabelRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.vo.MemberDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.MilestoneRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.StatRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FilterService {

    private final StatRepository statRepository;
    private final MemberRepository memberRepository;
    private final LabelRepository labelRepository;
    private final MilestoneRepository milestoneRepository;

    public FilterInformation getMainFilter() {
        List<MemberDetailsVO> memberFilter = memberRepository.getMemberFilter();
        List<LabelDetailsVO> labelFilter = labelRepository.getLabelFilter();
        List<MilestoneDetailsVO> milestoneFilter = milestoneRepository.getMilestoneFilter();

        return FilterInformation.from(memberFilter, memberFilter, labelFilter, milestoneFilter);
    }

    public FilterInformation getDetailFilter() {
        List<MemberDetailsVO> members = memberRepository.getMemberFilter();
        List<LabelDetailsVO> labels = labelRepository.getLabelFilter();
        List<MilestoneDetailsVO> milestones = milestoneRepository.getMilestoneFilter();
        List<Long> milestoneIds = getMilestoneIds(milestones);
        Map<Long, IssueByMilestoneVO> issuesCountByMilestoneIds = statRepository.countIssuesByMilestoneIds(
                milestoneIds);

        return FilterInformation.from(members, labels, milestones, issuesCountByMilestoneIds);
    }

    private List<Long> getMilestoneIds(List<MilestoneDetailsVO> milestoneDetailsVOs) {
        return milestoneDetailsVOs.stream()
                .map(MilestoneDetailsVO::getId)
                .collect(Collectors.toUnmodifiableList());
    }
}
