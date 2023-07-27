package codesquard.app.issue.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Issue {

	private final Long id;
	private final Long milestoneId;
	private final Long userId;
	private final String title;
	private final String content;
	private final Boolean status;
	@JsonProperty("isDeleted")
	private final Boolean isDeleted;
	private final LocalDateTime createdAt;
	private final LocalDateTime modifiedAt;
}
