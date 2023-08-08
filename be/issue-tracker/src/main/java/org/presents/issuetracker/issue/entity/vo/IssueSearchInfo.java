package org.presents.issuetracker.issue.entity.vo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.issue.entity.AssigneeImage;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.milestone.entity.Milestone;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueSearchInfo {
	private Long id;
	private String title;
	private String authorLoginId;
	private List<AssigneeImage> assigneeImages;
	private List<Label> labels;
	private Milestone milestone;
	private LocalDateTime createdAt;
	private String status;

	@Builder
	public IssueSearchInfo(Long id, String title, String authorLoginId, List<AssigneeImage> assigneeImages,
		List<Label> labels, Milestone milestone, LocalDateTime createdAt, String status) {
		this.id = id;
		this.title = title;
		this.authorLoginId = authorLoginId;
		this.assigneeImages = assigneeImages;
		this.labels = labels;
		this.milestone = milestone;
		this.createdAt = createdAt;
		this.status = status;
	}

	public List<String> getGeneratedAssigneeImages() {
		return assigneeImages.stream()
			.map(AssigneeImage::getImage)
			.collect(Collectors.toUnmodifiableList());
	}
}
