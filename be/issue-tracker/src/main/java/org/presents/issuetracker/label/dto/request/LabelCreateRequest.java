package org.presents.issuetracker.label.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelCreateRequest {
    private String name;
    private String description;
    private String backgroundColor;
    private String textColor;

    @Builder
    public LabelCreateRequest(String name, String description, String backgroundColor, String textColor) {
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }
}
