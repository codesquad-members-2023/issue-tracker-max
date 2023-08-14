package codesquad.issueTracker.issue.vo;

import codesquad.issueTracker.label.domain.Label;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueLabelVo {

    private Long id;
    private String name;
    private String backgroundColor;
    private String textColor;

    @Builder
    public IssueLabelVo(Long id, String name, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static IssueLabelVo from(Label label) {
        return IssueLabelVo.builder()
                .id(label.getId())
                .name(label.getName())
                .backgroundColor(label.getBackgroundColor())
                .textColor(label.getTextColor())
                .build();
    }
}
