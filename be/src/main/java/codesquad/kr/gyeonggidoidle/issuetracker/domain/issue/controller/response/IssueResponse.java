package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.IssueInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.response.LabelResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class IssueResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final List<String> assigneeProfiles;
    private final String milestone;
    @JsonProperty("labels")
    private final List<LabelResponse> labelResponses;
    private final LocalDateTime createdAt;

    @Builder
    private IssueResponse(Long id, String title, String author, List<String> assigneeProfiles, String milestone, List<LabelResponse> labelResponses, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.assigneeProfiles = assigneeProfiles;
        this.milestone = milestone;
        this.labelResponses = labelResponses;
        this.createdAt = createdAt;
    }

    public static List<IssueResponse> from(List<codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.IssueInformation> issueInformations) {
        return issueInformations.stream()
                .map(IssueResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static IssueResponse from(IssueInformation issueInformation) {
        return IssueResponse.builder()
                .id(issueInformation.getId())
                .title(issueInformation.getTitle())
                .author(issueInformation.getAuthor())
                .assigneeProfiles(issueInformation.getAssigneeProfiles())
                .milestone(issueInformation.getMilestone())
                .labelResponses(LabelResponse.from(issueInformation.getLabelInformations()))
                .createdAt(issueInformation.getCreatedAt())
                .build();
    }
}
