package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.Label;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelCreateCondition {

    private final String name;
    private final String description;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    private LabelCreateCondition(String name, String description, String backgroundColor, String textColor) {
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static Label to(LabelCreateCondition condition) {
        return Label.builder()
                .name(condition.getName())
                .description(condition.getDescription())
                .backgroundColor(condition.getBackgroundColor())
                .textColor(condition.getTextColor())
                .build();
    }
}
