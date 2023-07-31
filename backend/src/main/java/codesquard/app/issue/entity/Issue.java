package codesquard.app.issue.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(of = "id")
@ToString
public class Issue {

	private Long id;
	private final Long milestoneId;
	private final Long userId;
	private final String title;
	private final String content;
	private Boolean status;
	@JsonProperty("isDeleted")
	private Boolean isDeleted;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	@Builder
	public Issue(Long milestoneId, Long userId, String title, String content) {
		this.milestoneId = milestoneId;
		this.userId = userId;
		this.title = title;
		this.content = content;
	}
}
