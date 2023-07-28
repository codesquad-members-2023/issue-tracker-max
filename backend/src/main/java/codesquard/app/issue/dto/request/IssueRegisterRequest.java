package codesquard.app.issue.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IssueRegisterRequest {

	private Long userId;
	private String title;
	private String content;
	private Long milestone;
	private List<Long> labels;
	private List<Long> assignees;
}
