package codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.service.information.LabelFilterInformation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LabelFilterResponse {

    private final Long id;
    private final String name;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    private LabelFilterResponse(Long id, String name, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static List<LabelFilterResponse> from(List<LabelFilterInformation> informations) {
        return informations.stream()
                .map(LabelFilterResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static LabelFilterResponse from(LabelFilterInformation information) {
        return LabelFilterResponse.builder()
                .id(information.getId())
                .name(information.getName())
                .backgroundColor(information.getBackgroundColor())
                .textColor(information.getTextColor())
                .build();
    }
}
