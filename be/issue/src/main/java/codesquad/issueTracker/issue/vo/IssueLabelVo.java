package codesquad.issueTracker.issue.vo;

import codesquad.issueTracker.label.vo.LabelVo;
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

    public static IssueLabelVo from(LabelVo labelVo) {
        return IssueLabelVo.builder()
                .id(labelVo.getId())
                .name(labelVo.getName())
                .backgroundColor(labelVo.getBackgroundColor())
                .textColor(labelVo.getTextColor())
                .build();
    }
}
