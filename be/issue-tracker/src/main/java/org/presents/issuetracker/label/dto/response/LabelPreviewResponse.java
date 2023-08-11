package org.presents.issuetracker.label.dto.response;

import org.presents.issuetracker.label.entity.Label;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LabelPreviewResponse {
    private static final Long NO_LABEL_ID = 0L;
    private static final String NO_NAME = "none";
    private static final String NO_COLOR = "";

    private long id;
    private String name;
    private String textColor;
    private String backgroundColor;

    private static final LabelPreviewResponse LABEL_NOT_ASSIGNED_RESPONSE
            = new LabelPreviewResponse(NO_LABEL_ID, NO_NAME, NO_COLOR, NO_COLOR);

    public static LabelPreviewResponse of(long id, String name, String textColor, String backgroundColor) {
        return new LabelPreviewResponse(id, name, textColor, backgroundColor);
    }

    public static LabelPreviewResponse getLabelNotAssignedResponse() {
        return LABEL_NOT_ASSIGNED_RESPONSE;
    }

    @Builder
    private LabelPreviewResponse(long id, String name, String textColor, String backgroundColor) {
        this.id = id;
        this.name = name;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    public static LabelPreviewResponse from(Label label) {
        return LabelPreviewResponse.builder()
                .id(label.getId())
                .name(label.getName())
                .backgroundColor(label.getBackgroundColor())
                .textColor(label.getTextColor())
                .build();
    }
}
