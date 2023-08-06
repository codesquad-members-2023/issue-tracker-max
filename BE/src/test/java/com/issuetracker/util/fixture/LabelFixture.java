package com.issuetracker.util.fixture;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum LabelFixture {
	LABEL1(1L, "style", "layout, style 등 수정", "#fbca04"),
	LABEL2(2L, "bug fix", "Something working", "#d73a4a"),
	LABEL3(3L, "docs", "Improvements or additions to documentation", "#0075ca"),
	LABEL4(4L, "feature", "New feature", "#a2eeef"),
	LABEL5(5L, "infra", "인프라 환경 설정", "#0315EB"),
	LABEL6(6L, "refactor", "코드 리팩토링", "#23B63C"),
	LABEL7(7L, "test", "테스트 코드", "#0e8a16");

	private final Long id;
	private final String title;
	private final String description;
	private final String color;

	LabelFixture(Long id, String title, String description, String color) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	public static LabelFixture findById(Long id) {
		return Arrays.stream(values())
			.filter(l -> l.id == id)
			.findFirst()
			.orElseThrow();
	}

	public static List<LabelFixture> findAllById(List<Long> ids) {
		return ids.stream()
			.map(LabelFixture::findById)
			.collect(Collectors.toUnmodifiableList());
	}

	public static String createInsertSQL() {
		return String.format("INSERT INTO label(title, description, color) VALUES %s", Arrays.stream(values())
			.map(l -> String.format("('%s', '%s', '%s')",
				l.getTitle(),
				l.getDescription(),
				l.getColor()))
			.collect(Collectors.joining(", ")));
	}
}
