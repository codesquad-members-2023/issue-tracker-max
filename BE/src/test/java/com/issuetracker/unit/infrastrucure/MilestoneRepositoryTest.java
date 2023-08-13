package com.issuetracker.unit.infrastrucure;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.milestone.domain.MilestoneRepository;
import com.issuetracker.milestone.infrastructure.JdbcMilestoneRepository;
import com.issuetracker.util.DatabaseInitialization;
import com.issuetracker.util.RepositoryTest;

@RepositoryTest
class MilestoneRepositoryTest {

	private MilestoneRepository milestoneRepository;
	private DatabaseInitialization databaseInitialization;

	@Autowired
	public MilestoneRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.milestoneRepository = new JdbcMilestoneRepository(jdbcTemplate);
		this.databaseInitialization = new DatabaseInitialization(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseInitialization.initialization();
		databaseInitialization.loadData();
	}

	@Test
	void 마일스톤_아이디를_입력하면_존재하는_마일스톤인지_확일할_수_있다() {
		// given
		Long id = 1L;

		// when
		boolean result = milestoneRepository.existById(id);

		// then
		assertThat(result).isTrue();
	}

	@Test
	void 필터용_마일스톤_목록을_조회할_수_있다() {
		// when
		List<Milestone> milestones = milestoneRepository.findAllForFilter();

		// then
		assertThat(milestones).isNotEmpty();
	}

	@Test
	void 마일스톤을_생성할_수_있다() {
		// given
		Milestone milestone = Milestone.builder()
			.title("마일스톤 제목")
			.description("마일스톤 설명")
			.deadline(LocalDate.of(2023, 8, 8))
			.build();

		// when
		Long result = milestoneRepository.save(milestone);

		// then
		assertThat(result).isPositive();
	}

	@Test
	void 마일스톤을_수정할_수_있다() {
		// given
		Milestone milestone = Milestone.builder()
			.id(1L)
			.title("수정된 마일스톤 제목")
			.description("수정된 마일스톤 설명")
			.deadline(LocalDate.of(2023, 8, 8))
			.build();

		// when
		int result = milestoneRepository.update(milestone);

		// then
		assertThat(result).isEqualTo(1);
	}

	@Test
	void 마일스톤을_삭제할_수_있다() {
		// given
		Milestone milestone = Milestone.builder()
			.id(1L)
			.build();

		// when
		int result = milestoneRepository.delete(milestone);

		// then
		assertThat(result).isEqualTo(1);
	}
}
