package codesquad.issueTracker.issue.dto;

import codesquad.issueTracker.issue.vo.IssueLabelVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueLabelResponseDto {
    List<IssueLabelVo> labels;

    @Builder
    public IssueLabelResponseDto(List<IssueLabelVo> labels) {
        this.labels = labels;
    }

    public static IssueLabelResponseDto from(List<IssueLabelVo> labels) {
        return IssueLabelResponseDto.builder()
                .labels(labels)
                .build();
    }
}
