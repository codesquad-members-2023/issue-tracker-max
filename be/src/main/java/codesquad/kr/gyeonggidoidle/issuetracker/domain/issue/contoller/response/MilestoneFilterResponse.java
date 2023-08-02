package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.MilestoneFilterInformation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneFilterResponse {

    private final Long id;
    private final String name;

    @Builder
    public MilestoneFilterResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<MilestoneFilterResponse> from(List<MilestoneFilterInformation> informations) {
        return informations.stream()
                .map(MilestoneFilterResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static MilestoneFilterResponse from(MilestoneFilterInformation information) {
        return MilestoneFilterResponse.builder()
                .id(information.getId())
                .name(information.getName())
                .build();
    }
}
