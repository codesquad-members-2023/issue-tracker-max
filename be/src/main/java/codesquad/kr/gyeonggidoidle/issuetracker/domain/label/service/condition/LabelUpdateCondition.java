package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.condition;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.Label;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelUpdateCondition {

    private final Long id;
    private final String name;
    private final String description;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    private LabelUpdateCondition(Long id, String name, String description, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static Label to(LabelUpdateCondition condition) {
        return Label.builder()
                .id(condition.getId())
                .name(condition.getName())
                .description(condition.getDescription())
                .backgroundColor(condition.getBackgroundColor())
                .textColor(condition.getTextColor())
                .build();
    }
}
