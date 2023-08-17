package codesquad.issueTracker.issue.dto.filter;

import codesquad.issueTracker.issue.dto.IssueCountResponseDto;
import codesquad.issueTracker.issue.vo.IssueFilteredVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueFilteredResponseDto {
    private IssueCountResponseDto issueCountResponseDto;
    private List<IssueFilteredVo> issueReads;

    @Builder
    public IssueFilteredResponseDto(IssueCountResponseDto issueCountResponseDto, List<IssueFilteredVo> issueReads) {
        this.issueCountResponseDto = issueCountResponseDto;
        this.issueReads = issueReads;
    }

    public static IssueFilteredResponseDto of(IssueCountResponseDto issueCountResponseDto, List<IssueFilteredVo> issueReads) {
        return IssueFilteredResponseDto.builder()
                .issueCountResponseDto(issueCountResponseDto)
                .issueReads(issueReads)
                .build();
    }
}
