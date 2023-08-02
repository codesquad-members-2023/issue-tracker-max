package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneFilterInformation {

    private final Long id;
    private final String name;

    @Builder
    private MilestoneFilterInformation(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
