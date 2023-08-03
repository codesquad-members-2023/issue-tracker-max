package org.presents.issuetracker.label.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelPreviewResponse {
    private long id;
    private String name;
    private String textColor;
    private String backgroundColor;

    @Builder
    public LabelPreviewResponse(long id, String name, String textColor, String backgroundColor) {
        this.id = id;
        this.name = name;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }
}
