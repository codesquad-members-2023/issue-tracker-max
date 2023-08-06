package org.presents.issuetracker.label.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelDetailResponse {
    private long id;
    private String name;
    private String description;
    private String textColor;
    private String backgroundColor;

    @Builder
    public LabelDetailResponse(long id, String name, String description, String textColor, String backgroundColor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }
}
