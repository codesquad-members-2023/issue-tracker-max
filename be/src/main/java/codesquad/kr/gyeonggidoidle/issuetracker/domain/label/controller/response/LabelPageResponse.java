package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelPageInformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelPageResponse {

    private final Integer milestoneCount;
    private final Integer labelCount;
    @JsonProperty("labels")
    private final List<LabelDetailsResponse> labelDetailsResponses;

    @Builder
    private LabelPageResponse(Integer milestoneCount, Integer labelCount,
                             List<LabelDetailsResponse> labelDetailsResponses) {
        this.milestoneCount = milestoneCount;
        this.labelCount = labelCount;
        this.labelDetailsResponses = labelDetailsResponses;
    }

    public static LabelPageResponse from(LabelPageInformation labelPageInformation) {
        return LabelPageResponse.builder()
                .milestoneCount(labelPageInformation.getMilestoneCount())
                .labelCount(labelPageInformation.getLabelCount())
                .labelDetailsResponses(LabelDetailsResponse.from(labelPageInformation.getLabelDetailsInformations()))
                .build();
    }
}
