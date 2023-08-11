package codesquard.app.issue.mapper.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class IssueFilterRequest {

	private static final String ME = "@me";

	private String is;

	private String milestone;

	private List<String> label;

	private String author;

	private String assignee;

	private String mentions;

	public IssueFilterRequest convertMe(String loginId) {
		if (author != null && author.equals(ME)) {
			setAuthor(loginId);
		}
		if (assignee != null && assignee.equals(ME)) {
			setAssignee(loginId);
		}
		if (mentions != null && mentions.equals(ME)) {
			setMentions(loginId);
		}
		return this;
	}

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
