package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LabelFilterInformation {

    private final Long id;
    private final String name;
    private final String backgroundColor;
    private final String textColor;

    @Builder
    private LabelFilterInformation(Long id, String name, String backgroundColor, String textColor) {
        this.id = id;
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static List<LabelFilterInformation> from(List<LabelDetailsVO> labelDetailsVOs) {
        return labelDetailsVOs.stream()
                .map(LabelFilterInformation::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static LabelFilterInformation from(LabelDetailsVO labelDetailsVO) {
        return LabelFilterInformation.builder()
                .id(labelDetailsVO.getId())
                .name(labelDetailsVO.getName())
                .backgroundColor(labelDetailsVO.getBackgroundColor())
                .textColor(labelDetailsVO.getTextColor())
                .build();
    }
}
