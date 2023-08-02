package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.vo.MemberDetailsVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFilterInformation {

    private final Long id;
    private final String name;
    private final String profile;

    @Builder
    private MemberFilterInformation(Long id, String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    public static List<MemberFilterInformation> from(List<MemberDetailsVO> memberDetailsVOs) {
        return memberDetailsVOs.stream()
                .map(MemberFilterInformation::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static MemberFilterInformation from(MemberDetailsVO memberDetailsVO) {
        return MemberFilterInformation.builder()
                .id(memberDetailsVO.getId())
                .name(memberDetailsVO.getName())
                .profile(memberDetailsVO.getProfile())
                .build();
    }
}
