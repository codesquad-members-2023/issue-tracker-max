package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.vo.MemberDetailsVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AssigneeFilterInformation {

    private final Long id;
    private final String name;
    private final String profile;

    @Builder
    private AssigneeFilterInformation(Long id, String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    public static List<AssigneeFilterInformation> from(List<MemberDetailsVO> memberDetailsVOs) {
        List<AssigneeFilterInformation> informations = new ArrayList<>();
        informations.add(new AssigneeFilterInformation(0L, "담당자가 없는 이슈", ""));

        memberDetailsVOs.stream()
                .map(AssigneeFilterInformation::from)
                .forEach(informations::add);

        return Collections.unmodifiableList(informations);
    }

    public static AssigneeFilterInformation from(MemberDetailsVO memberDetailsVO) {
        return AssigneeFilterInformation.builder()
                .id(memberDetailsVO.getId())
                .name(memberDetailsVO.getName())
                .profile(memberDetailsVO.getProfile())
                .build();
    }
}
