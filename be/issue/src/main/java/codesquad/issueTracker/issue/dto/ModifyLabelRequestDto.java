package codesquad.issueTracker.issue.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ModifyLabelRequestDto {
    private List<Long> labels;

}
