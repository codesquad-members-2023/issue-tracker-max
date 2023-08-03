package codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDetailsVO {

    private final Long id;
    private final String name;
    private final String profile;

    @Builder
    private MemberDetailsVO(Long id, String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }
}
