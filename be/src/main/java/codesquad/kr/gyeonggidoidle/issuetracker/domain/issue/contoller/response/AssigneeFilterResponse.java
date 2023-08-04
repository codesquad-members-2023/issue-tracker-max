package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.AssigneeFilterInformation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AssigneeFilterResponse {

    private final Long id;
    private final String name;
    private final String profile;

    @Builder
    private AssigneeFilterResponse(Long id, String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    public static List<AssigneeFilterResponse> from(List<AssigneeFilterInformation> informations) {
        return informations.stream()
                .map(AssigneeFilterResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static AssigneeFilterResponse from(AssigneeFilterInformation information) {
        return AssigneeFilterResponse.builder()
                .id(information.getId())
                .name(information.getName())
                .profile(information.getProfile())
                .build();
    }
}
