package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MilestoneStatusRequest {

    @NotNull(message = "값을 입력해주세요.")
    @JsonProperty("isOpen")
    private boolean open;

    public MilestoneStatusRequest(boolean open) {
        this.open = open;
    }
}
