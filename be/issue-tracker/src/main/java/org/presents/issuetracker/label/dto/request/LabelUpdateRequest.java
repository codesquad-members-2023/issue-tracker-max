package org.presents.issuetracker.label.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class LabelUpdateRequest {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String backgroundColor;

    @NotBlank
    private String textColor;

    private String description;
}
