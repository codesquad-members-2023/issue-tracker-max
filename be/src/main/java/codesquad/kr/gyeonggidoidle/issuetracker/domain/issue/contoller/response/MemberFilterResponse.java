package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.MemberFilterInformation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFilterResponse {

    private final Long id;
    private final String name;
    private final String profile;

    @Builder
    private MemberFilterResponse(Long id, String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    public static List<MemberFilterResponse> from(List<MemberFilterInformation> informations) {
        return informations.stream()
                .map(MemberFilterResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static MemberFilterResponse from(MemberFilterInformation information) {
        return MemberFilterResponse.builder()
                .id(information.getId())
                .name(information.getName())
                .profile(information.getProfile())
                .build();
    }
}
