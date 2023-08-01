package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelInformation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LabelResponse {

    private final String name;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    public LabelResponse(String name, String backgroundColor, String textColor) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static List<LabelResponse> from(List<LabelInformation> labelInformations) {
        return labelInformations.stream()
                .map(LabelResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static LabelResponse from(LabelInformation labelInformation) {
        return LabelResponse.builder()
                .name(labelInformation.getName())
                .backgroundColor(labelInformation.getBackgroundColor())
                .textColor(labelInformation.getTextColor())
                .build();
    }
}
