package codesquad.kr.gyeonggidoidle.issuetracker.domain.label;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Label {

    private final Long id;
    private final String name;
    private String description;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    private Label(Long id, String name, String description, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }
}
