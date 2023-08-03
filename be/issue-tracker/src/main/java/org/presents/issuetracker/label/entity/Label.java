package org.presents.issuetracker.label.entity;

import lombok.Getter;
import org.presents.issuetracker.label.dto.request.LabelUpdateRequest;

import java.util.Objects;

@Getter
public class Label {
    private Long id;
    private String name;
    private String description;
    private String backgroundColor;
    private String textColor;
    private boolean isDeleted;

    private Label(Long id, String name, String description, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    private Label(String name, String description, String backgroundColor, String textColor) {
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static Label of(Long id, String name, String description, String backgroundColor, String textColor) {
        return new Label(id, name, description, backgroundColor, textColor);}

    public static Label of(String name, String description, String backgroundColor, String textColor) {
        return new Label(name, description, backgroundColor, textColor);}

    public Label updateFrom(LabelUpdateRequest dto) {
        if (!Objects.equals(this.name, dto.getName())) {
            this.name = dto.getName();
        }
        if (!Objects.equals(this.description, dto.getDescription())) {
            this.description = dto.getDescription();
        }
        if (!Objects.equals(this.backgroundColor, dto.getBackgroundColor())) {
            this.backgroundColor = dto.getBackgroundColor();
        }
        if (!Objects.equals(this.textColor, dto.getTextColor())) {
            this.textColor = dto.getTextColor();
        }

        return this;
    }
}
