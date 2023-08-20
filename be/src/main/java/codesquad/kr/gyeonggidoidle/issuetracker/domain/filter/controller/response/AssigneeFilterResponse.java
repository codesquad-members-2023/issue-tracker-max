package codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.controller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.service.information.AssigneeFilterInformation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

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
