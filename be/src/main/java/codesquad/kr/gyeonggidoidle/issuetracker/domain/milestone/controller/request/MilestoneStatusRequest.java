package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MilestoneStatusRequest {

    @JsonProperty("isOpen")
    private boolean open;

    public MilestoneStatusRequest(boolean open) {
        this.open = open;
    }
}
