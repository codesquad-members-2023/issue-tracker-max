package codesquad.issueTracker.issue.dto;

import codesquad.issueTracker.issue.vo.IssueUserVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueUserResponseDto {
    private List<IssueUserVo> participants;


    @Builder
    public IssueUserResponseDto(List<IssueUserVo> participants) {
        this.participants = participants;
    }

    public static IssueUserResponseDto from(List<IssueUserVo> participants) {
        return IssueUserResponseDto.builder()
                .participants(participants)
                .build();
    }
}
