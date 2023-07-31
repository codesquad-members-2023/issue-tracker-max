package com.issuetracker.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum IssueLabelMappingFixture {

	ISSUE_LABEL_MAPPING_FIXTURE1(1L, 1L, 5L),
	ISSUE_LABEL_MAPPING_FIXTURE2(2L, 2L, 5L),
	ISSUE_LABEL_MAPPING_FIXTURE3(3L, 3L, 4L),
	ISSUE_LABEL_MAPPING_FIXTURE4(4L, 3L, 7L),
	ISSUE_LABEL_MAPPING_FIXTURE5(5L, 4L, 4L),
	ISSUE_LABEL_MAPPING_FIXTURE6(6L, 4L, 7L),
	ISSUE_LABEL_MAPPING_FIXTURE7(7L, 5L, 4L),
	ISSUE_LABEL_MAPPING_FIXTURE8(8L, 5L, 7L),
	ISSUE_LABEL_MAPPING_FIXTURE9(9L, 6L, 4L),
	ISSUE_LABEL_MAPPING_FIXTURE10(10L, 6L, 7L),
	ISSUE_LABEL_MAPPING_FIXTURE11(11L, 7L, 4L),
	ISSUE_LABEL_MAPPING_FIXTURE12(12L, 7L, 7L),
	ISSUE_LABEL_MAPPING_FIXTURE13(13L, 8L, 4L),
	ISSUE_LABEL_MAPPING_FIXTURE14(14L, 8L, 7L),
	ISSUE_LABEL_MAPPING_FIXTURE15(15L, 9L, 4L),
	ISSUE_LABEL_MAPPING_FIXTURE16(16L, 9L, 7L),
	ISSUE_LABEL_MAPPING_FIXTURE17(17L, 10L, 4L),
	ISSUE_LABEL_MAPPING_FIXTURE18(18L, 10L, 7L),
	ISSUE_LABEL_MAPPING_FIXTURE19(19L, 11L, 4L),
	ISSUE_LABEL_MAPPING_FIXTURE20(20L, 11L, 7L),
	ISSUE_LABEL_MAPPING_FIXTURE21(21L, 12L, 3L);

	private final Long id;
	private final Long issueId;
	private final Long labelId;

	IssueLabelMappingFixture(Long id, Long issueId, Long labelId) {
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

	public static IssueLabelMappingFixture findById(Long id) {
		return Arrays.stream(values())
			.filter(ilm -> ilm.id == id)
			.findAny()
			.orElseThrow();
	}

	public static String createInsertSQL() {
		return String.format(
			"INSERT INTO issue_label_mapping(issue_id, label_id) VALUES %s",
			Arrays.stream(values())
				.map(ilm -> String.format(
					"('%s', '%s')",
					ilm.getIssueId(),
					ilm.getLabelId()))
				.collect(Collectors.joining(", ")));
	}
}
