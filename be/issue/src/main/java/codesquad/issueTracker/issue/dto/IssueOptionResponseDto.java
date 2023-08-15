package codesquad.issueTracker.issue.dto;

import codesquad.issueTracker.issue.vo.IssueLabelVo;
import codesquad.issueTracker.issue.vo.IssueMilestoneVo;
import codesquad.issueTracker.issue.vo.AssigneeVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueOptionResponseDto {

    private List<AssigneeVo> assignees;
    private List<IssueLabelVo> labels;
    private IssueMilestoneVo milestone;

    @Builder
    public IssueOptionResponseDto(List<AssigneeVo> assignees, List<IssueLabelVo> labels, IssueMilestoneVo milestone) {
        this.assignees = assignees;
        this.labels = labels;
        this.milestone = milestone;
    }

    public static IssueOptionResponseDto of(List<AssigneeVo> assignees, List<IssueLabelVo> labels, IssueMilestoneVo milestone) {
        if (milestone.getIssueMileStoneDetailVo().getId() == null) {
            return IssueOptionResponseDto.builder()
                    .assignees(assignees)
                    .labels(labels)
                    .build();
        }

        return IssueOptionResponseDto.builder()
                .assignees(assignees)
                .labels(labels)
                .milestone(milestone)
                .build();
    }

}
