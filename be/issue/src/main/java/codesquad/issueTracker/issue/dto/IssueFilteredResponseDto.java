package codesquad.issueTracker.issue.dto;

import java.util.List;

import codesquad.issueTracker.issue.domain.IssueRead;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueFilteredResponseDto {
	private IssueCountResponseDto issueCountResponseDto;
	private List<IssueRead> issueReads;

	@Builder
	public IssueFilteredResponseDto(IssueCountResponseDto issueCountResponseDto, List<IssueRead> issueReads) {
		this.issueCountResponseDto = issueCountResponseDto;
		this.issueReads = issueReads;
	}

	public static IssueFilteredResponseDto of(IssueCountResponseDto issueCountResponseDto, List<IssueRead> issueReads) {
		return IssueFilteredResponseDto.builder()
			.issueCountResponseDto(issueCountResponseDto)
			.issueReads(issueReads)
			.build();
	}
}
