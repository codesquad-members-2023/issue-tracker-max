package codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.vo.MemberDetailsVO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AuthorFilterInformation {

    private final Long id;
    private final String name;
    private final String profile;

    @Builder
    private AuthorFilterInformation(Long id, String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    public static List<AuthorFilterInformation> from(List<MemberDetailsVO> memberDetailsVOs) {
        return memberDetailsVOs.stream()
                .map(AuthorFilterInformation::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static AuthorFilterInformation from(MemberDetailsVO memberDetailsVO) {
        return AuthorFilterInformation.builder()
                .id(memberDetailsVO.getId())
                .name(memberDetailsVO.getName())
                .profile(memberDetailsVO.getProfile())
                .build();
    }
}
