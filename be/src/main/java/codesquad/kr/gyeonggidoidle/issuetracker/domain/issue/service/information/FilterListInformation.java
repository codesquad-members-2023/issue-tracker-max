package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.vo.MemberDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
                .assigneeFilterInformations(AssigneeFilterInformation.fromByMain(assignees))
                .authorFilterInformations(AuthorFilterInformation.from(authors))
                .labelFilterInformations(LabelFilterInformation.from(labels))
                .milestoneFilterInformations(MilestoneFilterInformation.from(milestones))
                .build();
    }

    public static FilterListInformation from(List<MemberDetailsVO> assignees, List<LabelDetailsVO> labels,
                                             List<MilestoneDetailsVO> milestones, Map<Long, IssueByMilestoneVO> issuesCountByMilestoneIds) {
        return FilterListInformation.builder()
                .assigneeFilterInformations(AssigneeFilterInformation.fromByIssue(assignees))
                .authorFilterInformations(Collections.emptyList())
                .labelFilterInformations(LabelFilterInformation.from(labels))
                .milestoneFilterInformations(MilestoneFilterInformation.from(milestones, issuesCountByMilestoneIds))
                .build();
    }
}
