package org.presents.issuetracker.issue.dto.request;

import lombok.Builder;

@Builder
public class IssueSearchParam {
	private String status; // status:open
	private String authorName; // author:khundi
	private String labelName; // label:feat
	private String milestoneName; // milestone:milestone1
	private String title; // 검색할 제목
	private String assigneeName; //assignee:bono
	private String commentAuthorName; // comment:jian
	private boolean isFilterRemoved = false; // 필터 적용 후 삭제 시

	public static IssueSearchParam from(String query) {
		if (query == null) { // parameter로 받은 filter 값이 없다는 의미
			return null;
		}
		if (query.equals("")) { // "" 공백열을 받으면 filter가 제거 되었다는 의미
			return IssueSearchParam.builder().isFilterRemoved(true).build();
		}
		String[] splitedQuery = query.split(" ");
		String status = null;
		String authorName = null;
		String labelName = null;
		String milestoneName = null;
		String assigneeName = null;
		String commentAuthorName = null;
		StringBuilder title = null;

		for (String s : splitedQuery) {
			if (s.startsWith("status")) {
				status = s.replace("status:", "");
			}
			if (s.startsWith("author")) {
				authorName = s.replace("author:", "");
			}
			if (s.startsWith("label")) {
				labelName = s.replace("label:", "");
			}
			if (s.startsWith("milestone")) {
				milestoneName = s.replace("milestone:", "");
			}
			if (s.startsWith("assignee")) {
				assigneeName = s.replace("assignee:", "");
			}
			if (s.startsWith("comment")) {
				commentAuthorName = s.replace("comment:", "");
			}
			if (!s.contains(":") && (!s.equals(""))) {
				if (title == null) {
					title = new StringBuilder();
				}
				title.append(s).append(" ");
			}
		}

		return IssueSearchParam.builder()
			.status(status)
			.authorName(authorName)
			.labelName(labelName)
			.milestoneName(milestoneName)
			.assigneeName(assigneeName)
			.commentAuthorName(commentAuthorName)
			.title(title != null ? title.deleteCharAt(title.length() - 1).toString() : null)
			.build();
	}

	public String getStatus() {
		return this.status;
	}
}
