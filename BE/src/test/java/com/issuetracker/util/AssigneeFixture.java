package com.issuetracker.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum AssigneeFixture {

	ASSIGNEE1(1L, 1L, 1L),
	ASSIGNEE2(2L, 1L, 2L),
	ASSIGNEE3(3L, 1L, 3L),
	ASSIGNEE4(4L, 2L, 1L),
	ASSIGNEE5(5L, 2L, 4L),
	ASSIGNEE6(6L, 3L, 2L),
	ASSIGNEE7(7L, 4L, 1L),
	ASSIGNEE8(8L, 4L, 3L),
	ASSIGNEE9(9L, 4L, 4L),
	ASSIGNEE10(10L, 4L, 2L),
	ASSIGNEE11(11L, 5L, 4L),
	ASSIGNEE12(12L, 6L, 4L),
	ASSIGNEE13(13L, 7L, 2L),
	ASSIGNEE14(14L, 7L, 3L),
	ASSIGNEE15(15L, 8L, 1L),
	ASSIGNEE16(16L, 9L, 1L),
	ASSIGNEE17(17L, 9L, 3L),
	ASSIGNEE18(18L, 9L, 4L),
	ASSIGNEE19(19L, 10L, 1L),
	ASSIGNEE20(20L, 11L, 2L),
	ASSIGNEE21(21L, 12L, 1L),
	ASSIGNEE22(22L, 12L, 4L);

	private final Long id;
	private final Long issueId;
	private final Long memberId;

	AssigneeFixture(Long id, Long issueId, Long memberId) {
		this.id = id;
		this.issueId = issueId;
		this.memberId = memberId;
	}

	public Long getId() {
		return id;
	}

	public Long getIssueId() {
		return issueId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public static AssigneeFixture findById(Long id) {
		return Arrays.stream(values())
			.filter(a -> a.id == id)
			.findAny()
			.orElseThrow();
	}

	public static String createInsertSQL() {
		return String.format(
			"INSERT INTO assignee(issue_id, member_id) VALUES %s",
			Arrays.stream(values())
				.map(ic -> String.format(
					"('%s', '%s')",
					ic.getIssueId(),
					ic.getMemberId()))
				.collect(Collectors.joining(", ")));
	}
}
