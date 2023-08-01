package kr.codesquad.issuetracker.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Comment {

	private Integer id;
	private String content;
	private LocalDateTime createdAt;
	private Boolean isDeleted;
	private Integer userAccountId;
	private Integer issueId;

	public Comment(String content, Integer userAccountId, Integer issueId) {
		this.content = content;
		this.userAccountId = userAccountId;
		this.issueId = issueId;
	}
}
