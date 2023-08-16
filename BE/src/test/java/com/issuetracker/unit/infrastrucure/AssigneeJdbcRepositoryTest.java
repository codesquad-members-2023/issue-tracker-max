package com.issuetracker.unit.infrastrucure;

import static com.issuetracker.util.fixture.AssigneeFixture.ASSIGNEE1;
import static com.issuetracker.util.fixture.IssueFixture.ISSUE1;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER4;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.issue.domain.assignee.Assignee;
import com.issuetracker.issue.domain.assignee.AssigneeRepository;
import com.issuetracker.issue.infrastrucure.JdbcAssigneeRepository;
import com.issuetracker.member.domain.Member;
import com.issuetracker.util.JdbcRepositoryTest;
import com.issuetracker.util.fixture.AssigneeFixture;

class AssigneeJdbcRepositoryTest extends JdbcRepositoryTest {

	private AssigneeRepository assigneeRepository;

	@Autowired
	public AssigneeJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.assigneeRepository = new JdbcAssigneeRepository(jdbcTemplate);
	}

	@Test
	void 여러명의_작업자를_생성할_수_있다() {
		// given
		List<Assignee> assignees = List.of(
			Assignee.builder().issueId(1L).memberId(1L).build(),
			Assignee.builder().issueId(1L).memberId(2L).build(),
			Assignee.builder().issueId(1L).memberId(3L).build()
		);

		// when
		int[] actual = assigneeRepository.saveAll(assignees);

		// then
		assertThat(actual).containsExactly(1, 1, 1);
	}

	@Test
	void 이슈에_등록_되어있는_담담자_목록을_조회한다() {
		// when
		List<Member> actual = assigneeRepository.findAll();

		// then
		assertThat(actual).hasSize(4);
	}

	@Test
	void 이슈에_담담자를_등록한다() {
		// given
		Assignee assignee = Assignee.builder()
			.issueId(ISSUE1.getId())
			.memberId(MEMBER4.getId())
			.build();

		// when
		long actual = assigneeRepository.save(assignee);

		// then
		int expected = AssigneeFixture.values().length + 1;
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void 이슈에_담당자를_삭제한다() {
		// when
		int actual = assigneeRepository.delete(ASSIGNEE1.getId());

		// then
		assertThat(actual).isEqualTo(1);
	}
}
