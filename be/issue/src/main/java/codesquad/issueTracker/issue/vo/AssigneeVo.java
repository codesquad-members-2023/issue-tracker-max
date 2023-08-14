package codesquad.issueTracker.issue.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AssigneeVo {
    private Long id;
    private String name;
    private String imgUrl;

    @Builder
    public AssigneeVo(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }
}
