package com.issuetracker.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum IssueFixture {

	ISSUE1(1L, "프로젝트 초기 설정", "## 프로젝트 초기 설정", true, LocalDateTime.now(), 1L, 1L),
	ISSUE2(2L, "Gradle 의존성 추가", "## Gradle 의존성 추가", true, LocalDateTime.now(), 1L, 1L),
	ISSUE3(3L, "회원가입 기능 개발", "## 회원가입 기능 개발", true, LocalDateTime.now(), 1L, 2L),
	ISSUE4(4L, "일반 로그인 기능 개발", "## 일반 로그인 기능 개발", false, LocalDateTime.now(), 1L, 2L),
	ISSUE5(5L, "OAuth 로그인 기능 개발", "## OAuth 로그인 기능 개발", false, LocalDateTime.now(), 2L, 3L),
	ISSUE6(6L, "이슈 목록 API 개발", "## 이슈 목록 API 개발", false, LocalDateTime.now(), 2L, 3L),
	ISSUE7(7L, "이슈 필터 기능 추가", "## 이슈 필터 기능 추가", true, LocalDateTime.now(), 2L, 4L),
	ISSUE8(8L, "레이블 추가 API 개발", "## 레이블 추가 API 개발", true, LocalDateTime.now(), 2L, 4L),
	ISSUE9(9L, "레이블 삭제 API 개발", "## 레이블 삭제 API 개발", true, LocalDateTime.now(), 3L, 1L),
	ISSUE10(10L, "마일스톤 추가 API 개발", "## 마일스톤 추가 API 개발", false, LocalDateTime.now(), 3L, 1L),
	ISSUE11(11L, "마일스톤 수정 API 개발", "## 마일스톤 수정 API 개발", false, LocalDateTime.now(), 3L, 1L),
	ISSUE12(12L, "README 작성", "## README 작성", false, LocalDateTime.now(), 3L, 1L);

	private final Long id;
	private final String title;
	private final String content;
	private final Boolean isOpen;
	private final LocalDateTime createAt;
	private final Long milestoneId;
	private final Long authorId;

	IssueFixture(Long id, String title, String content, Boolean isOpen, LocalDateTime createAt,
		Long milestoneId, Long authorId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.isOpen = isOpen;
		this.createAt = createAt;
		this.milestoneId = milestoneId;
		this.authorId = authorId;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Boolean getOpen() {
		return isOpen;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public Long getMilestoneId() {
		return milestoneId;
	}

	public static IssueFixture findById(Long id) {
		return Arrays.stream(values())
			.filter(i -> i.id == id)
			.findAny()
			.orElseThrow();
	}

	public static String createInsertSQL() {
		return String.format("INSERT INTO issue(title, content, is_open, create_at, author_id, milestone_id ) VALUES %s", Arrays.stream(values())
			.map(i -> String.format("('%s', '%s', %s, '%s', '%s', '%s')",
				i.title,
				i.content,
				i.isOpen,
				i.createAt,
				i.authorId,
				i.milestoneId))
			.collect(Collectors.joining(", ")));
	}
}
