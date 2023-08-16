package codesquad.issueTracker.issue.vo;

import codesquad.issueTracker.issue.domain.IssueRead;
import codesquad.issueTracker.issue.dto.filter.LabelDetailResponseDto;
import codesquad.issueTracker.issue.dto.filter.MilestoneDetailResponseDto;
import codesquad.issueTracker.issue.dto.filter.UserDetailResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class IssueFilteredVo {

    private Long id;
    private String title;
    private Boolean isClosed;
    private LocalDateTime createdAt;
    private UserDetailResponseDto user;
    private MilestoneDetailResponseDto milestone;
    private List<LabelDetailResponseDto> labels;

    @Builder
    public IssueFilteredVo(Long id, String title, Boolean isClosed, LocalDateTime createdAt, UserDetailResponseDto user, MilestoneDetailResponseDto milestone, List<LabelDetailResponseDto> labels) {
        this.id = id;
        this.title = title;
        this.isClosed = isClosed;
        this.createdAt = createdAt;
        this.user = user;
        this.milestone = milestone;
        this.labels = labels;
    }

    public static List<IssueFilteredVo> of(List<IssueRead> issueReadList) {
        return issueReadList.stream()
                .map(issueRead -> IssueFilteredVo.builder()
                        .id(issueRead.getId())
                        .title(issueRead.getTitle())
                        .isClosed(issueRead.getIsClosed())
                        .createdAt(issueRead.getCreatedAt())
                        .user(UserDetailResponseDto.of(issueRead.getUser()))
                        .milestone(MilestoneDetailResponseDto.of(issueRead.getMilestone()))
                        .labels(LabelDetailResponseDto.of(issueRead.getLabels()))
                        .build())
                .collect(Collectors.toList());
    }


}
