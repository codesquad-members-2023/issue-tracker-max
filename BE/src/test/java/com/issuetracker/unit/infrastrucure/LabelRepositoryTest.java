package com.issuetracker.unit.infrastrucure;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.label.domain.LabelRepository;
import com.issuetracker.label.infrastructure.JdbcLabelRepository;
import com.issuetracker.util.DatabaseInitialization;
import com.issuetracker.util.RepositoryTest;

@RepositoryTest
class LabelRepositoryTest {

	private LabelRepository labelRepository;
	private DatabaseInitialization databaseInitialization;

	@Autowired
	public LabelRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.labelRepository = new JdbcLabelRepository(jdbcTemplate);
		this.databaseInitialization = new DatabaseInitialization(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseInitialization.initialization();
		databaseInitialization.loadData();
	}

	@Test
	void 라벨_아이디_목록을_입력하면_존재하는_라벨인지_확인한다() {
		// given
		List<Long> labelIds = List.of(1L, 2L, 3L, 4L);

		// when
		boolean result = labelRepository.existByIds(labelIds);

		// then
		assertThat(result).isTrue();
	}

}
