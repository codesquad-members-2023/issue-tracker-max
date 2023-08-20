package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository.result.CommentResult;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.service.information.CommentInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueDetailResult;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information.MilestoneInformation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class IssueDetailInformation {

    private final Long id;
    private final String author;
    private final String title;
    private final Boolean isOpen;
    private final LocalDateTime createdAt;
    private final List<String> assigneeProfiles;
    private final Integer commentCount;
    private final List<LabelInformation> labelInformations;
    private final MilestoneInformation milestoneInformation;
    private final List<CommentInformation> commentInformations;

    @Builder
    private IssueDetailInformation(Long id, String author, String title, Boolean isOpen, LocalDateTime createdAt,
                                  List<String> assigneeProfiles, Integer commentCount,
                                  List<LabelInformation> labelInformations, MilestoneInformation milestoneInformation,
                                  List<CommentInformation> commentInformations) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.isOpen = isOpen;
        this.createdAt = createdAt;
        this.assigneeProfiles = assigneeProfiles;
        this.commentCount = commentCount;
        this.labelInformations = labelInformations;
        this.milestoneInformation = milestoneInformation;
        this.commentInformations = commentInformations;
    }

    public static IssueDetailInformation from(IssueDetailResult issueDetailResult, List<String> assigneeProfiles,
                                              int commentCount, List<LabelVO> labelVOs, MilestoneVO milestoneVO,
                                              List<CommentResult> commentResults) {
        return IssueDetailInformation.builder()
                .id(issueDetailResult.getId())
                .author(issueDetailResult.getAuthor())
                .title(issueDetailResult.getTitle())
                .isOpen(issueDetailResult.getIsOpen())
                .createdAt(issueDetailResult.getCreatedAt())
                .assigneeProfiles(assigneeProfiles)
                .commentCount(commentCount)
                .labelInformations(LabelInformation.from(labelVOs))
                .milestoneInformation(MilestoneInformation.from(milestoneVO))
                .commentInformations(CommentInformation.from(commentResults))
                .build();
    }
}
