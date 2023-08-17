package com.issuetracker.util.fixture;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum LabelFixture {
	LABEL1(1L, "style", "layout, style 등 수정", "#fbca04", "#000000"),
	LABEL2(2L, "bug fix", "Something working", "#d73a4a", "#000000"),
	LABEL3(3L, "docs", "Improvements or additions to documentation", "#0075ca", "#000000"),
	LABEL4(4L, "feature", "New feature", "#a2eeef", "#000000"),
	LABEL5(5L, "infra", "인프라 환경 설정", "#0315EB", "#000000"),
	LABEL6(6L, "refactor", "코드 리팩토링", "#23B63C", "#000000"),
	LABEL7(7L, "test", "테스트 코드", "#0e8a16", "#000000");

	private final Long id;
	private final String title;
	private final String description;
	private final String backgroundColor;
	private final String textColor;

	LabelFixture(Long id, String title, String description, String backgroundColor, String textColor) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public static LabelFixture findById(Long id) {
		return Arrays.stream(values())
			.filter(l -> l.id == id)
			.findFirst()
			.orElseThrow();
	}

	public static LabelFixture findByTitle(String title) {
		return Arrays.stream(values())
			.filter(l -> l.title.equals(title))
			.findFirst()
			.orElseThrow();
	}

	public static List<LabelFixture> findByIssueId(Long issueId) {
		return Arrays.stream(AssignedLabelFixture.values())
			.filter(a -> a.getIssueId() == issueId)
			.map(a -> findById(a.getLabelId()))
			.collect(Collectors.toUnmodifiableList());
	}

	public static List<LabelFixture> findAllByTitles(List<String> titles) {
		return titles.stream()
			.map(LabelFixture::findByTitle)
			.collect(Collectors.toUnmodifiableList());
	}

	public static String createInsertSQL() {
		return String.format("INSERT INTO label(title, description, background_color, text_color) VALUES %s", Arrays.stream(values())
			.map(l -> String.format("('%s', '%s', '%s', '%s')",
				l.getTitle(),
				l.getDescription(),
				l.getBackgroundColor(),
				l.getTextColor()))
			.collect(Collectors.joining(", ")));
	}
}
