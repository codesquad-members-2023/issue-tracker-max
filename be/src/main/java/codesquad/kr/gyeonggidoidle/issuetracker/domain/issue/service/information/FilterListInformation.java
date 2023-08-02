package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.vo.MemberDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FilterListInformation {

    private final List<MemberFilterInformation> assigneeFilterInformations;
    private final List<MemberFilterInformation> authorFilterInformations;
    private final List<LabelFilterInformation> labelFilterInformations;
    private final List<MilestoneFilterInformation> milestoneFilterInformations;

    @Builder
    private FilterListInformation(List<MemberFilterInformation> assigneeFilterInformations,
                                 List<MemberFilterInformation> authorFilterInformations,
                                 List<LabelFilterInformation> labelFilterInformations,
                                 List<MilestoneFilterInformation> milestoneFilterInformations) {
        this.assigneeFilterInformations = assigneeFilterInformations;
        this.authorFilterInformations = authorFilterInformations;
        this.labelFilterInformations = labelFilterInformations;
        this.milestoneFilterInformations = milestoneFilterInformations;
    }

    public static FilterListInformation from(List<MemberDetailsVO> members, List<LabelDetailsVO> labels,
                                             List<MilestoneDetailsVO> milestones) {
        return FilterListInformation.builder()
                .assigneeFilterInformations(MemberFilterInformation.from(members))
                .authorFilterInformations(MemberFilterInformation.from(members))
                .labelFilterInformations(LabelFilterInformation.from(labels))
                .milestoneFilterInformations(MilestoneFilterInformation.from(milestones))
                .build();

    }
}
