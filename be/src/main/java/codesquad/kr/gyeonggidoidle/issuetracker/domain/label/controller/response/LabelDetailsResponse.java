package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelDetailsInformation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LabelDetailsResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    private LabelDetailsResponse(Long id, String name, String description, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static List<LabelDetailsResponse> from(List<LabelDetailsInformation> labelDetailsInformations) {
        return labelDetailsInformations.stream()
                .map(LabelDetailsResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static LabelDetailsResponse from(LabelDetailsInformation labelDetailsInformation) {
        return LabelDetailsResponse.builder()
                .id(labelDetailsInformation.getId())
                .name(labelDetailsInformation.getName())
                .description(labelDetailsInformation.getDescription())
                .backgroundColor(labelDetailsInformation.getBackgroundColor())
                .textColor(labelDetailsInformation.getTextColor())
                .build();
    }
}
