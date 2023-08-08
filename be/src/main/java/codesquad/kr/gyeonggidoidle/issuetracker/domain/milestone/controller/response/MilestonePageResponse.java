package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information.MilestonePageInformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MilestonePageResponse {

    private final Integer openMilestoneCount;
    private final Integer closeMilestoneCount;
    private final Integer labelCount;
    @JsonProperty("milestones")
    private final List<MilestoneDetailsResponse> milestoneDetailsResponses;

    @Builder
    private MilestonePageResponse(Integer openMilestoneCount, Integer closeMilestoneCount, Integer labelCount,
                                  List<MilestoneDetailsResponse> milestoneDetailsResponses) {
        this.openMilestoneCount = openMilestoneCount;
        this.closeMilestoneCount = closeMilestoneCount;
        this.labelCount = labelCount;
        this.milestoneDetailsResponses = milestoneDetailsResponses;
    }

    public static MilestonePageResponse from(MilestonePageInformation milestonePageInformation) {
        return MilestonePageResponse.builder()
                .openMilestoneCount(milestonePageInformation.getOpenMilestoneCount())
                .closeMilestoneCount(milestonePageInformation.getCloseMilestoneCount())
                .labelCount(milestonePageInformation.getLabelCount())
                .milestoneDetailsResponses(MilestoneDetailsResponse.from(milestonePageInformation.getMilestoneDetailsInformations()))
                .build();
    }
}
