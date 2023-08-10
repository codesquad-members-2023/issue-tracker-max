package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.request;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.condition.LabelCreateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.condition.LabelUpdateCondition;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelRequest {

    private final String name;
    private final String description;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    private LabelRequest(String name, String description, String backgroundColor, String textColor) {
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static LabelCreateCondition to(LabelRequest request) {
        return LabelCreateCondition.builder()
                .name(request.getName())
                .description(request.getDescription())
                .backgroundColor(request.getBackgroundColor())
                .textColor(request.getTextColor())
                .build();
    }

    public static LabelUpdateCondition to(Long labelId, LabelRequest request) {
        return LabelUpdateCondition.builder()
                .id(labelId)
                .name(request.getName())
                .description(request.getDescription())
                .backgroundColor(request.getBackgroundColor())
                .textColor(request.getTextColor())
                .build();
    }
}
