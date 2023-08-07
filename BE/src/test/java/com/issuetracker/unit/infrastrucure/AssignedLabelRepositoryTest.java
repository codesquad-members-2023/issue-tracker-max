package com.issuetracker.unit.infrastrucure;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.issue.domain.AssignedLabel;
import com.issuetracker.issue.domain.AssignedLabelRepository;
import com.issuetracker.issue.infrastrucure.JdbcAssignedLabelRepository;
import com.issuetracker.label.domain.Label;
import com.issuetracker.util.DatabaseInitialization;
import com.issuetracker.util.RepositoryTest;

@RepositoryTest
class AssignedLabelRepositoryTest {

	private AssignedLabelRepository assignedLabelRepository;
	private DatabaseInitialization databaseInitialization;

	@Autowired
	public AssignedLabelRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.assignedLabelRepository = new JdbcAssignedLabelRepository(jdbcTemplate);
		this.databaseInitialization = new DatabaseInitialization(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseInitialization.initialization();
		databaseInitialization.loadData();
	}

	@Test
	void 여러_개의_이슈와_라벨을_매핑한_정보를_생성할_수_있다() {
		// given
		List<AssignedLabel> assignedLabels = List.of(
			AssignedLabel.builder().issueId(1L).labelId(1L).build(),
			AssignedLabel.builder().issueId(1L).labelId(2L).build(),
			AssignedLabel.builder().issueId(1L).labelId(3L).build()
		);

		// when
		int[] actual = assignedLabelRepository.saveAll(assignedLabels);

		// then
		assertThat(actual).containsExactly(1, 1, 1);
	}

	@Test
	void 이슈에_등록되어_있는_라벨_목록을_조회한다() {
		// when
		List<Label> actual = assignedLabelRepository.findAll();

		// then
		assertThat(actual).hasSize(4);
	}
}
