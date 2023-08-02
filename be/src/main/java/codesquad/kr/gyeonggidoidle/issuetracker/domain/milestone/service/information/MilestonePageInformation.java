package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.MilestoneStatVO;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestonePageInformation {

    private final Integer openMilestoneCount;
    private final Integer closeMilestoneCount;
    private final Integer labelCount;
    private final List<MilestoneDetailsInformation> milestoneDetailsInformations;

    @Builder
    private MilestonePageInformation(Integer openMilestoneCount, Integer closeMilestoneCount, Integer labelCount,
                                    List<MilestoneDetailsInformation> milestoneDetailsInformations) {
        this.openMilestoneCount = openMilestoneCount;
        this.closeMilestoneCount = closeMilestoneCount;
        this.labelCount = labelCount;
        this.milestoneDetailsInformations = milestoneDetailsInformations;
    }

    public static MilestonePageInformation from(MilestoneStatVO milestoneStatVO, List<MilestoneDetailsVO> milestoneDetailsVOs,
                                                Map<Long, IssueByMilestoneVO> issueByMilestoneVOs) {
        return MilestonePageInformation.builder()
                .openMilestoneCount(milestoneStatVO.getOpenMilestoneCount())
                .closeMilestoneCount(milestoneStatVO.getCloseMilestoneCount())
                .labelCount(milestoneStatVO.getLabelCount())
                .milestoneDetailsInformations(MilestoneDetailsInformation.from(milestoneDetailsVOs, issueByMilestoneVOs))
                .build();
    }
}
