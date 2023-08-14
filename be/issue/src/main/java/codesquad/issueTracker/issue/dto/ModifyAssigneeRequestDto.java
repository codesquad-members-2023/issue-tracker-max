package codesquad.issueTracker.issue.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ModifyAssigneeRequestDto {
	private List<Long> assignees;
}
