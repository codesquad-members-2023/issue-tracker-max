package com.issuetracker.util.fixture;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum IssueCommentFixture {

	ISSUE_COMMENT1(1L, "LGTM!", LocalDateTime.now(), 1L, 1L),
	ISSUE_COMMENT2(2L, "고생하셨어요~", LocalDateTime.now(), 2L, 1L),
	ISSUE_COMMENT3(3L, "잘봤습니다.", LocalDateTime.now(), 3L, 1L),
	ISSUE_COMMENT4(4L, "고생하셨어요~", LocalDateTime.now(), 2L, 2L),
	ISSUE_COMMENT5(5L, "잘봤습니다.", LocalDateTime.now(), 2L, 2L),
	ISSUE_COMMENT6(6L, "잘봤습니다.", LocalDateTime.now(), 1L, 3L),
	ISSUE_COMMENT7(7L, "LGTM!", LocalDateTime.now(), 1L, 3L),
	ISSUE_COMMENT8(8L, "고생하셨어요~", LocalDateTime.now(), 4L, 3L),
	ISSUE_COMMENT9(9L, "LGTM!", LocalDateTime.now(), 1L, 4L),
	ISSUE_COMMENT10(10L, "잘봤습니다.", LocalDateTime.now(), 3L, 4L),
	ISSUE_COMMENT11(11L, "고생하셨어요~", LocalDateTime.now(), 3L, 5L),
	ISSUE_COMMENT12(12L, "잘봤습니다.", LocalDateTime.now(), 3L, 5L);

	private final Long id;
	private final String content;
	private final LocalDateTime createAt;
	private final Long authorId;
	private final Long issueId;

	IssueCommentFixture(Long id, String content, LocalDateTime createAt, Long authorId, Long issueId) {
		this.id = id;
		this.content = content;
		this.createAt = createAt;
		this.authorId = authorId;
		this.issueId = issueId;
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public static IssueCommentFixture findById(Long id) {
		return Arrays.stream(values())
			.filter(ic -> ic.id == id)
			.findAny()
			.orElseThrow();
	}

	public static String createInsertSQL() {
		return String.format(
			"INSERT INTO issue_comment(id, content, create_at, author_id, issue_id) VALUES %s",
			Arrays.stream(values())
				.map(ic -> String.format(
						"('%s', '%s', '%s', '%s', '%s')",
						ic.getId(),
						ic.getContent(),
						ic.getCreateAt(),
						ic.getAuthorId(),
						ic.getIssueId()))
				.collect(Collectors.joining(", ")));
	}
}
