package com.issuetracker.unit.infrastrucure;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.label.domain.Label;
import com.issuetracker.label.domain.LabelRepository;
import com.issuetracker.label.infrastructure.JdbcLabelRepository;
import com.issuetracker.util.JdbcRepositoryTest;

class LabelJdbcRepositoryTest extends JdbcRepositoryTest {

	private LabelRepository labelRepository;

	@Autowired
	public LabelJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.labelRepository = new JdbcLabelRepository(jdbcTemplate);
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

	@Test
	void 레이블_목록을_조회할_수_있다() {
		// when
		List<Label> result = labelRepository.findAll();

		// then
		assertThat(result).isNotEmpty();
	}

	@Test
	void 레이블을_수정할_수_있다() {
		// given
		Label label = Label.builder()
			.id(1L)
			.title("수정된 제목")
			.description("수정된 설명")
			.backgroundColor("#000000")
			.textColor("#000000")
			.build();

		// when
		int result = labelRepository.update(label);

		// then
		assertThat(result).isEqualTo(1);
	}

	@Test
	void 레이블을_삭제할_수_있다() {
		// given
		Label label = Label.builder()
			.id(1L).build();

		// when
		int result = labelRepository.delete(label);

		// then
		assertThat(result).isEqualTo(1);
	}
}
