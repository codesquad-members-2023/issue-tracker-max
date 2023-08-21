package codesquad.issueTracker.issue.dto;

import codesquad.issueTracker.issue.domain.Issue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class IssueWriteRequestDto {
    @NotNull(message = "제목을 입력해주세요")
    @NotBlank(message = "빈 문자열은 입력 할 수 없습니다.")
    private String title;
    private String content;
    private List<Long> assignees;
    private List<Long> labels;
    private Long milestoneId;

    public static Issue toEntity(IssueWriteRequestDto request, Long userId) {
        return Issue.builder()
                .title(request.title)
                .content(request.getContent())
                .milestoneId(request.getMilestoneId())
                .userId(userId)
                .build();
    }

}
