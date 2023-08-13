package codesquad.issueTracker.issue.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import codesquad.issueTracker.issue.domain.Issue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueWriteRequestDto {
	@NotNull(message = "제목을 입력해주세요")
	private String title;
	private String content;
	private List<Long> assignees;
	private List<Long> labels;
	private Long milestoneId;
	private Long userId;

	public static Issue toEntity(IssueWriteRequestDto request) {
		return Issue.builder()
			.title(request.title)
			.content(request.getContent())
			.milestoneId(request.getMilestoneId())
			.userId(request.getUserId())
			.build();
	}

}
