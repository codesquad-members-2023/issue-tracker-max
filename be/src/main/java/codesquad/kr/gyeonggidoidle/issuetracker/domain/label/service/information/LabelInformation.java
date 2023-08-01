package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LabelInformation {

    private final String name;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    private LabelInformation(String name, String backgroundColor, String textColor) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static List<LabelInformation> from(List<LabelVO> labelVOs) {
        return labelVOs.stream()
                .map(LabelInformation::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static LabelInformation from(LabelVO labelVO) {
        return LabelInformation.builder()
                .name(labelVO.getName())
                .backgroundColor(labelVO.getBackgroundColor())
                .textColor(labelVO.getTextColor())
                .build();
    }
}
