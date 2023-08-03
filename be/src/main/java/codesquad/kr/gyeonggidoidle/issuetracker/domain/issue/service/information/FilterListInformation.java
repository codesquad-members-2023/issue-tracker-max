package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.vo.MemberDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FilterListInformation {

    private final List<AssigneeFilterInformation> assigneeFilterInformations;
    private final List<AuthorFilterInformation> authorFilterInformations;
    private final List<LabelFilterInformation> labelFilterInformations;
    private final List<MilestoneFilterInformation> milestoneFilterInformations;

    @Builder
    private FilterListInformation(List<AssigneeFilterInformation> assigneeFilterInformations,
                                 List<AuthorFilterInformation> authorFilterInformations,
                                 List<LabelFilterInformation> labelFilterInformations,
                                 List<MilestoneFilterInformation> milestoneFilterInformations) {
        this.assigneeFilterInformations = assigneeFilterInformations;
        this.authorFilterInformations = authorFilterInformations;
        this.labelFilterInformations = labelFilterInformations;
        this.milestoneFilterInformations = milestoneFilterInformations;
    }

    public static FilterListInformation from(List<MemberDetailsVO> assignees, List<MemberDetailsVO> authors,
                                             List<LabelDetailsVO> labels, List<MilestoneDetailsVO> milestones) {
        return FilterListInformation.builder()
                .assigneeFilterInformations(AssigneeFilterInformation.from(assignees))
                .authorFilterInformations(AuthorFilterInformation.from(authors))
                .labelFilterInformations(LabelFilterInformation.from(labels))
                .milestoneFilterInformations(MilestoneFilterInformation.from(milestones))
                .build();

    }
}
