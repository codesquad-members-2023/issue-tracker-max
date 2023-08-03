package org.presents.issuetracker.label.dto.request;

import lombok.Getter;

@Getter
public class LabelUpdateRequest {
    private Long id;
    private String name;
    private String description;
    private String backgroundColor;
    private String textColor;
}
