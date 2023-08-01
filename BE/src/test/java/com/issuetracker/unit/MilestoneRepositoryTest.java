package com.issuetracker.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.milestone.infrastructure.MilestoneRepository;
import com.issuetracker.util.DatabaseInitialization;
import com.issuetracker.util.RepositoryTest;

@RepositoryTest
class MilestoneRepositoryTest {

	private MilestoneRepository milestoneRepository;
	private DatabaseInitialization databaseInitialization;

	@Autowired
	public MilestoneRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.milestoneRepository = new MilestoneRepository(jdbcTemplate);
		this.databaseInitialization = new DatabaseInitialization(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseInitialization.initialization();
		databaseInitialization.loadData();
	}

	@Test
	void 마일스톤_아이디를_입력하면_존재하는_마일스톤인지_확일할_수_있다() {
		Long id = 1L;

		boolean result = milestoneRepository.existById(id);

		assertThat(result).isTrue();
	}
}
