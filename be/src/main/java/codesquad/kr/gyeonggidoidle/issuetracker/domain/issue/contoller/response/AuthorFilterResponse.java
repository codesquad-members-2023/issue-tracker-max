package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.AuthorFilterInformation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AuthorFilterResponse {

    private final Long id;
    private final String name;
    private final String profile;

    @Builder
    private AuthorFilterResponse(Long id, String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    public static List<AuthorFilterResponse> from(List<AuthorFilterInformation> informations) {
        return informations.stream()
                .map(AuthorFilterResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static AuthorFilterResponse from(AuthorFilterInformation information) {
        return AuthorFilterResponse.builder()
                .id(information.getId())
                .name(information.getName())
                .profile(information.getProfile())
                .build();
    }
}
