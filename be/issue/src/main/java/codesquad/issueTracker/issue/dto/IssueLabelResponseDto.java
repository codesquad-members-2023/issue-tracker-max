package codesquad.issueTracker.issue.dto;

import codesquad.issueTracker.label.vo.LabelVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueLabelResponseDto {
    private Long id;
    private String name;
    private String backgroundColor;
    private String textColor;

    @Builder
    public IssueLabelResponseDto(Long id, String name, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static IssueLabelResponseDto from(LabelVo label) {
        return IssueLabelResponseDto.builder()
                .id(label.getId())
                .name(label.getName())
                .backgroundColor(label.getBackgroundColor())
                .textColor(label.getTextColor())
                .build();
    }
}
