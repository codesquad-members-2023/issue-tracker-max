package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneDetailsInformation {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate dueDate;
    private final Integer openIssueCount;
    private final Integer closedIssuesCount;

    @Builder
    private MilestoneDetailsInformation(Long id, String name, String description, LocalDate dueDate,
                                       Integer openIssueCount, Integer closedIssuesCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.openIssueCount = openIssueCount;
        this.closedIssuesCount = closedIssuesCount;
    }

    public static List<MilestoneDetailsInformation> from(List<MilestoneDetailsVO> milestoneDetailsVOs,
                                                         Map<Long, IssueByMilestoneVO> issueByMilestoneVOs) {
        return milestoneDetailsVOs.stream()
                .map(milestoneDetailsVO -> from(milestoneDetailsVO, issueByMilestoneVOs))
                .collect(Collectors.toUnmodifiableList());
    }

    public static MilestoneDetailsInformation from(MilestoneDetailsVO milestoneDetailsVO,
                                                   Map<Long, IssueByMilestoneVO> issueByMilestoneVOs) {
        return MilestoneDetailsInformation.builder()
                .id(milestoneDetailsVO.getId())
                .name(milestoneDetailsVO.getName())
                .description(milestoneDetailsVO.getDescription())
                .dueDate(milestoneDetailsVO.getDueDate())
                .openIssueCount(issueByMilestoneVOs.get(milestoneDetailsVO.getId()).getOpenIssueCount())
                .closedIssuesCount(issueByMilestoneVOs.get(milestoneDetailsVO.getId()).getClosedIssueCount())
                .build();

    }
}
