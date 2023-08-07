package com.issuetracker.util.fixture;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum AssignedLabelFixture {

	ASSIGNED_LABEL1(1L, 1L, 5L),
	ASSIGNED_LABEL2(2L, 2L, 5L),
	ASSIGNED_LABEL3(3L, 3L, 4L),
	ASSIGNED_LABEL4(4L, 3L, 7L),
	ASSIGNED_LABEL5(5L, 4L, 4L),
	ASSIGNED_LABEL6(6L, 4L, 7L),
	ASSIGNED_LABEL7(7L, 5L, 4L),
	ASSIGNED_LABEL8(8L, 5L, 7L),
	ASSIGNED_LABEL9(9L, 6L, 4L),
	ASSIGNED_LABEL10(10L, 6L, 7L),
	ASSIGNED_LABEL11(11L, 7L, 4L),
	ASSIGNED_LABEL12(12L, 7L, 7L),
	ASSIGNED_LABEL13(13L, 8L, 4L),
	ASSIGNED_LABEL14(14L, 8L, 7L),
	ASSIGNED_LABEL15(15L, 9L, 4L),
	ASSIGNED_LABEL16(16L, 9L, 7L),
	ASSIGNED_LABEL17(17L, 10L, 4L),
	ASSIGNED_LABEL18(18L, 10L, 7L),
	ASSIGNED_LABEL19(19L, 11L, 4L),
	ASSIGNED_LABEL20(20L, 11L, 7L),
	ASSIGNED_LABEL21(21L, 12L, 3L);

	private final Long id;
	private final Long issueId;
	private final Long labelId;

	AssignedLabelFixture(Long id, Long issueId, Long labelId) {
		this.id = id;
		this.issueId = issueId;
		this.labelId = labelId;
	}

	public Long getId() {
		return id;
	}

	public Long getIssueId() {
		return issueId;
	}

	public Long getLabelId() {
		return labelId;
	}

	public static AssignedLabelFixture findById(Long id) {
		return Arrays.stream(values())
			.filter(ilm -> ilm.id == id)
			.findAny()
			.orElseThrow();
	}

	public static String createInsertSQL() {
		return String.format(
			"INSERT INTO assigned_label(issue_id, label_id) VALUES %s",
			Arrays.stream(values())
				.map(ilm -> String.format(
					"('%s', '%s')",
					ilm.getIssueId(),
					ilm.getLabelId()))
				.collect(Collectors.joining(", ")));
	}
}
