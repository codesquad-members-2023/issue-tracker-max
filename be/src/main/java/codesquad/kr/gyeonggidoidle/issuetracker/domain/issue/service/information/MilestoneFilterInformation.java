package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class MilestoneFilterInformation {

    private final Long id;
    private final String name;
    private final Integer openIssueCount;
    private final Integer closedIssueCount;

    @Builder
    private MilestoneFilterInformation(Long id, String name, Integer openIssueCount, Integer closedIssueCount) {
        this.id = id;
        this.name = name;
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
    }

    public static List<MilestoneFilterInformation> from(List<MilestoneDetailsVO> milestoneDetailsVOs) {
        return milestoneDetailsVOs.stream()
                .map(MilestoneFilterInformation::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static MilestoneFilterInformation from(MilestoneDetailsVO milestoneDetailsVO) {
        return MilestoneFilterInformation.builder()
                .id(milestoneDetailsVO.getId())
                .name(milestoneDetailsVO.getName())
                .build();
    }

    public static List<MilestoneFilterInformation> from(List<MilestoneDetailsVO> milestoneDetailsVOs,
                                                        Map<Long, IssueByMilestoneVO> issuesCountByMilestoneIds) {
        return milestoneDetailsVOs.stream()
                .map(milestoneDetailsVO -> from(milestoneDetailsVO, issuesCountByMilestoneIds))
                .collect(Collectors.toUnmodifiableList());
    }

    public static MilestoneFilterInformation from(MilestoneDetailsVO milestoneDetailsVO, Map<Long, IssueByMilestoneVO> issuesCountByMilestoneIds) {
        return MilestoneFilterInformation.builder()
                .id(milestoneDetailsVO.getId())
                .name(milestoneDetailsVO.getName())
                .openIssueCount(issuesCountByMilestoneIds.get(milestoneDetailsVO.getId()).getOpenIssueCount())
                .closedIssueCount(issuesCountByMilestoneIds.get(milestoneDetailsVO.getId()).getClosedIssueCount())
                .build();
    }
}
