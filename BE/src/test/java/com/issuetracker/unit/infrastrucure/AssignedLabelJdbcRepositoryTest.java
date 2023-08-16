package com.issuetracker.unit.infrastrucure;

import static com.issuetracker.util.fixture.AssignedLabelFixture.ASSIGNED_LABEL1;
import static com.issuetracker.util.fixture.IssueFixture.ISSUE1;
import static com.issuetracker.util.fixture.LabelFixture.LABEL1;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.issue.domain.assignedlabel.AssignedLabel;
import com.issuetracker.issue.domain.assignedlabel.AssignedLabelRepository;
import com.issuetracker.issue.infrastrucure.JdbcAssignedLabelRepository;
import com.issuetracker.label.domain.Label;
import com.issuetracker.util.JdbcRepositoryTest;
import com.issuetracker.util.fixture.AssignedLabelFixture;

class AssignedLabelJdbcRepositoryTest extends JdbcRepositoryTest {

	private AssignedLabelRepository assignedLabelRepository;

	@Autowired
	public AssignedLabelJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.assignedLabelRepository = new JdbcAssignedLabelRepository(jdbcTemplate);
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

	@Test
	void 이슈에_라벨을_등록한다() {
		// given
		AssignedLabel assignedLabel = AssignedLabel.builder()
			.issueId(ISSUE1.getId())
			.labelId(LABEL1.getId())
			.build();

		// when
		long actual = assignedLabelRepository.save(assignedLabel);

		// then
		int expected = AssignedLabelFixture.values().length + 1;
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void 이슈에_라벨을_삭제한다() {
		// when
		int actual = assignedLabelRepository.delete(ASSIGNED_LABEL1.getId());

		// then
		assertThat(actual).isEqualTo(1);
	}
}
