package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.controller.response.CommentResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.IssueDetailInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.response.LabelResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.response.MilestoneResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class IssueDetailResponse {

    private final Long id;
    private final String author;
    private final String title;
    private final Boolean isOpen;
    private final LocalDateTime createdAt;
    private final List<String> assigneeProfiles;
    private final Integer commentCount;
    @JsonProperty("labels")
    private final List<LabelResponse> labelResponses;
    @JsonProperty("milestone")
    private final MilestoneResponse milestoneResponse;
    @JsonProperty("comments")
    private final List<CommentResponse> commentResponses;

    @Builder
    private IssueDetailResponse(Long id, String author, String title, Boolean isOpen, LocalDateTime createdAt,
                               List<String> assigneeProfiles, Integer commentCount, List<LabelResponse> labelResponses,
                               MilestoneResponse milestoneResponse, List<CommentResponse> commentResponses) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.isOpen = isOpen;
        this.createdAt = createdAt;
        this.assigneeProfiles = assigneeProfiles;
        this.commentCount = commentCount;
        this.labelResponses = labelResponses;
        this.milestoneResponse = milestoneResponse;
        this.commentResponses = commentResponses;
    }

    public static IssueDetailResponse from(IssueDetailInformation issueDetailInformation) {
        return IssueDetailResponse.builder()
                .id(issueDetailInformation.getId())
                .author(issueDetailInformation.getAuthor())
                .title(issueDetailInformation.getTitle())
                .isOpen(issueDetailInformation.getIsOpen())
                .createdAt(issueDetailInformation.getCreatedAt())
                .assigneeProfiles(issueDetailInformation.getAssigneeProfiles())
                .commentCount(issueDetailInformation.getCommentCount())
                .labelResponses(LabelResponse.from(issueDetailInformation.getLabelInformations()))
                .milestoneResponse(MilestoneResponse.from(issueDetailInformation.getMilestoneInformation()))
                .commentResponses(CommentResponse.from(issueDetailInformation.getCommentInformations()))
                .build();
    }
}
