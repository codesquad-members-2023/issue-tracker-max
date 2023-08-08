package org.presents.issuetracker.label.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Label {
    private Long id;
    private String name;
    private String description;
    private String backgroundColor;
    private String textColor;
    private boolean isDeleted;

    private Label(Long id, String name, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    private Label(String name, String description, String backgroundColor, String textColor) {
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    private Label(Long id, String name, String description, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static Label of(Long id, String name, String backgroundColor, String textColor) {
        return new Label(id, name, backgroundColor, textColor);
    }

    public static Label of(String name, String description, String backgroundColor, String textColor) {
        return new Label(name, description, backgroundColor, textColor);
    }

    public static Label of(Long id, String name, String description, String backgroundColor, String textColor) {
        return new Label(id, name, description, backgroundColor, textColor);
    }
}
