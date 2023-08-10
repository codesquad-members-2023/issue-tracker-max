package codesquard.app.issue.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class IssueFilterRequest {

	private String is; // opened, closed

	private String milestone;

	private List<String> label;

	private String author; // 작성자 // 왼쪽에 있는 필터 리스트에서 선택하면 @me

	private String assignee; // 왼쪽에 있는 필터 리스트에서 선택하면 @me

	private String mentions; // 왼쪽에 있는 필터 리스트에서 선택하면 @me

	public String getIs() {
		return is;
	}

	public String getMilestone() {
		return milestone;
	}

	public List<String> getLabel() {
		return label;
	}

	public String getAuthor() {
		return author;
	}

	public String getAssignee() {
		return assignee;
	}

	public String getMentions() {
		return mentions;
	}

	public void setIs(String is) {
		this.is = is;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public void setLabel(List<String> label) {
		this.label = label;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public void setMentions(String mentions) {
		this.mentions = mentions;
	}
	
}
