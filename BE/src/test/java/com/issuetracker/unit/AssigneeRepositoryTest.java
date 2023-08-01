package com.issuetracker.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.issue.domain.Assignee;
import com.issuetracker.issue.infrastrucure.AssigneeRepository;
import com.issuetracker.util.DatabaseInitialization;
import com.issuetracker.util.RepositoryTest;

@RepositoryTest
class AssigneeRepositoryTest {

	private AssigneeRepository assigneeRepository;
	private DatabaseInitialization databaseInitialization;

	@Autowired
	public AssigneeRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.assigneeRepository = new AssigneeRepository(jdbcTemplate);
		this.databaseInitialization = new DatabaseInitialization(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseInitialization.initialization();
		databaseInitialization.loadData();
	}

	@Test
	void 작업자를_생성할_수_있다() {

		Assignee assignee = Assignee.builder()
			.issueId(1L)
			.memberId(1L)
			.build();

		Long assigneeId = assigneeRepository.save(assignee);

		assertThat(assigneeId).isNotNull();
	}

	@Test
	void 여러명의_작업자를_생성할_수_있다() {

		List<Assignee> assignees = List.of(
			Assignee.builder().issueId(1L).memberId(1L).build(),
			Assignee.builder().issueId(1L).memberId(2L).build(),
			Assignee.builder().issueId(1L).memberId(3L).build()
		);

		List<Long> assigneeIds = assigneeRepository.saveAll(assignees);

		assertThat(assigneeIds).isNotEmpty();
	}
}
