package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelVO {

    private final String name;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    private LabelVO(String name, String backgroundColor, String textColor) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }
}
