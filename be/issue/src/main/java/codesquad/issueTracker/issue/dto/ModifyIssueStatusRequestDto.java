package codesquad.issueTracker.issue.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyIssueStatusRequestDto {
	private String status;
	private List<Long> issueIds;

}
