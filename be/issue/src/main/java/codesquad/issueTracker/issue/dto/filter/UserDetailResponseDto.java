package codesquad.issueTracker.issue.dto.filter;

import codesquad.issueTracker.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDetailResponseDto {
    private Long id;
    private String profileImg;
    private String name;

    public static UserDetailResponseDto of(User user) {
        if (user == null) {
            return new UserDetailResponseDto();
        }
        return UserDetailResponseDto.builder()
                .id(user.getId())
                .profileImg(user.getProfileImg())
                .name(user.getName())
                .build();
    }

    @Builder
    public UserDetailResponseDto(Long id, String profileImg, String name) {
        this.id = id;
        this.profileImg = profileImg;
        this.name = name;
    }
}
