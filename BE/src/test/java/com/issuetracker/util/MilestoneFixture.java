package com.issuetracker.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum MilestoneFixture {

	MILESTONR1(1L, "Sprint #0", "첫 번째 스프린트", LocalDate.of(2023, Month.JUNE, 1), false),
	MILESTONR2(2L, "Sprint #1", "두 번째 스프린트", LocalDate.of(2023, Month.AUGUST, 11), true),
	MILESTONR3(3L, "Sprint #2", "세 번째 스프린트", LocalDate.of(2023, Month.SEPTEMBER, 7), true),
	MILESTONR4(4L, "Sprint #3", "네 번째 스프린트", LocalDate.of(2023, Month.SEPTEMBER, 7), true);

	private final Long id;
	private final String title;
	private final String description;
	private final LocalDate deadline;
	private final Boolean isOpen;

	MilestoneFixture(Long id, String title, String description, LocalDate deadline, Boolean isOpen) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.deadline = deadline;
		this.isOpen = isOpen;
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

	public LocalDate getDeadline() {
		return deadline;
	}

	public Boolean getOpen() {
		return isOpen;
	}

	public static MilestoneFixture findById(Long id) {
		return Arrays.stream(values())
			.filter(m -> m.id == id)
			.findAny()
			.orElseThrow();
	}

	public static String createInsertSQL() {
		return String.format(
			"INSERT INTO milestone(title, description, deadline, is_open) VALUES %s",
			Arrays.stream(values())
				.map(m -> String.format(
					"('%s', '%s', '%s', %s)",
					m.getTitle(),
					m.getDescription(),
					m.getDeadline(),
					m.getOpen()))
				.collect(Collectors.joining(", ")));
	}
}
