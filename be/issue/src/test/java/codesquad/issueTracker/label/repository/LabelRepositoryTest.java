package codesquad.issueTracker.label.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import annotation.RepositoryTest;
import codesquad.issueTracker.label.domain.Label;

@RepositoryTest
class LabelRepositoryTest {

	private LabelRepository labelRepository;
	private NamedParameterJdbcTemplate template;

	@Autowired
	public LabelRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
		this.labelRepository = new LabelRepository(jdbcTemplate);
		this.template = jdbcTemplate;
	}

	@DisplayName("DB에 정상적으로 label 객체를 저장하는지 테스트")
	@Test
	void insertTest() {
		//given
		Label label = Label.builder()
			.name("abc")
			.backgroundColor("333")
			.textColor("444")
			.description("")
			.build();

		Label label2 = Label.builder()
			.name("def")
			.backgroundColor("555")
			.textColor("666")
			.description("")
			.build();

		Long id = labelRepository.insert(label);
		Long id2 = labelRepository.insert(label2);

		Label expectLabel1 = findById(id);
		Label expectLabel2 = findById(id2);
		//then
		assertAll(
			() -> assertThat(label.getName()).isEqualTo(expectLabel1.getName()),
			() -> assertThat(label.getBackgroundColor()).isEqualTo(expectLabel1.getBackgroundColor()),
			() -> assertThat(label.getTextColor()).isEqualTo(expectLabel1.getTextColor()),
			() -> assertThat(label.getDescription()).isEqualTo(expectLabel1.getDescription()),
			() -> assertThat(label2.getName()).isEqualTo(expectLabel2.getName()),
			() -> assertThat(label2.getBackgroundColor()).isEqualTo(expectLabel2.getBackgroundColor()),
			() -> assertThat(label2.getTextColor()).isEqualTo(expectLabel2.getTextColor()),
			() -> assertThat(label2.getDescription()).isEqualTo(expectLabel2.getDescription())
		);
	}

	@DisplayName("DB에 정상적으로 label 객체를 수정되는지 테스트")
	@Test
	void updateTest() {
		//given
		Label updateLabel = Label.builder()
			.name("updated")
			.backgroundColor("111")
			.textColor("222")
			.description("updated")
			.build();
		labelRepository.update(1L, updateLabel);
		Label expectLabel = findById(1L);
		//then
		assertAll(
			() -> assertThat(updateLabel.getName()).isEqualTo(expectLabel.getName()),
			() -> assertThat(updateLabel.getBackgroundColor()).isEqualTo(expectLabel.getBackgroundColor()),
			() -> assertThat(updateLabel.getTextColor()).isEqualTo(expectLabel.getTextColor()),
			() -> assertThat(updateLabel.getDescription()).isEqualTo(expectLabel.getDescription())
		);
	}

	public Label findById(Long id) {
		String sql = "SELECT id, name, text_color, background_color, description "
			+ "FROM labels "
			+ "WHERE id = :id";
		return template.queryForObject(sql, new MapSqlParameterSource("id", id), labelRowMapper);
	}

	private final RowMapper<Label> labelRowMapper = (rs, rowNum) -> Label.builder()
		.id(rs.getLong("id"))
		.name(rs.getString("name"))
		.textColor(rs.getString("text_color"))
		.backgroundColor(rs.getString("background_color"))
		.description(rs.getString("description"))
		.build();
}


