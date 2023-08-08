package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class LabelPageInformation {

    private final Integer milestoneCount;
    private final Integer labelCount;
    private final List<LabelDetailsInformation> labelDetailsInformations;

    @Builder
    private LabelPageInformation(Integer milestoneCount, Integer labelCount,
                                 List<LabelDetailsInformation> labelDetailsInformations) {
        this.milestoneCount = milestoneCount;
        this.labelCount = labelCount;
        this.labelDetailsInformations = labelDetailsInformations;
    }

    public static LabelPageInformation from(StatVO statVO, List<LabelDetailsVO> labelDetailsVOs) {
        return LabelPageInformation.builder()
                .milestoneCount(statVO.getMilestoneCount())
                .labelCount(statVO.getLabelCount())
                .labelDetailsInformations(LabelDetailsInformation.from(labelDetailsVOs))
                .build();
    }
}
