package codesquad.issueTracker.issue.dto;

import codesquad.issueTracker.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueUserResponseDto {
    private Long id;
    private String name;
    private String imageUrl;

    @Builder
    public IssueUserResponseDto(Long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static IssueUserResponseDto from(User user) {
        return IssueUserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .imageUrl(user.getProfileImg())
                .build();
    }
}
