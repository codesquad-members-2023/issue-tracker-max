package codesquad.issueTracker.issue.vo;

import codesquad.issueTracker.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueUserVo {
    private Long id;
    private String name;
    private String imageUrl;

    @Builder
    public IssueUserVo(Long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static IssueUserVo from(User user) {
        return IssueUserVo.builder()
                .id(user.getId())
                .name(user.getName())
                .imageUrl(user.getProfileImg())
                .build();
    }
}
