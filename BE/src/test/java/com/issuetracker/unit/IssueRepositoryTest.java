package com.issuetracker.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.infrastrucure.IssueRepository;
import com.issuetracker.label.domain.Label;
import com.issuetracker.member.domain.Member;
import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.util.DatabaseInitialization;
import com.issuetracker.util.RepositoryTest;

@RepositoryTest
class IssueRepositoryTest {

	private IssueRepository issueRepository;
	private DatabaseInitialization databaseInitialization;

	@Autowired
	public IssueRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.issueRepository = new IssueRepository(jdbcTemplate);
		this.databaseInitialization = new DatabaseInitialization(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseInitialization.initialization();
		databaseInitialization.loadData();
	}

	@Test
	void 이슈를_생성할_수_있다() {

		Issue issue = Issue.builder()
			.title("제목")
			.content("내용")
			.isOpen(true)
			.createAt(LocalDateTime.now())
			.milestone(Milestone.createInstanceById(1L))
			.author(Member.createInstanceById(1L))
			.labels(List.of(Label.createInstanceById(1L)))
			.build();

		Long issueId = issueRepository.save(issue);

		assertThat(issueId).isNotNull();
	}
}
