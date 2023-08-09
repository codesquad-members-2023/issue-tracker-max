package com.issuetracker.issue.domain.comment;

import java.time.LocalDateTime;

import com.issuetracker.member.domain.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IssueCommentRead {

	private Long id;
	private String content;
	private LocalDateTime createAt;
	private Long issueId;
	private Member author;

	@Builder
	private IssueCommentRead(Long id, String content, LocalDateTime createAt, Long issueId, Member author) {
		this.id = id;
		this.content = content;
		this.createAt = createAt;
		this.issueId = issueId;
		this.author = author;
	}
}
