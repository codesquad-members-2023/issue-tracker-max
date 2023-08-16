package codesquad.issueTracker.issue.dto.filter;

import codesquad.issueTracker.label.domain.Label;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class LabelDetailResponseDto {

    private Long id;
    private String name;
    private String textColor;
    private String backgroundColor;

    public static List<LabelDetailResponseDto> of(List<Label> labels) {
        if (labels == null) {
            return new ArrayList<>();
        }
        return labels.stream()
                .map(LabelDetailResponseDto::of)
                .collect(Collectors.toList());
    }

    public static LabelDetailResponseDto of(Label label) {
        if (label == null) {
            return new LabelDetailResponseDto();
        }
        return LabelDetailResponseDto.builder()
                .id(label.getId())
                .name(label.getName())
                .textColor(label.getTextColor())
                .backgroundColor(label.getBackgroundColor())
                .build();
    }

    @Builder
    public LabelDetailResponseDto(Long id, String name, String textColor, String backgroundColor) {
        this.id = id;
        this.name = name;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }
}