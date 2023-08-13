package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LabelDetailsInformation {

    private final Long id;
    private final String name;
    private final String description;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    private LabelDetailsInformation(Long id, String name, String description, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static List<LabelDetailsInformation> from(List<LabelDetailsVO> labelDetailsVOs) {
        return labelDetailsVOs.stream()
                .map(labelDetailsVO -> from(labelDetailsVO))
                .collect(Collectors.toUnmodifiableList());
    }

    public static LabelDetailsInformation from(LabelDetailsVO labelDetailsVO) {
        return LabelDetailsInformation.builder()
                .id(labelDetailsVO.getId())
                .name(labelDetailsVO.getName())
                .description(labelDetailsVO.getDescription())
                .backgroundColor(labelDetailsVO.getBackgroundColor())
                .textColor(labelDetailsVO.getTextColor())
                .build();
    }
}
