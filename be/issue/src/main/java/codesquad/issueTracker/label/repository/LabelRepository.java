package codesquad.issueTracker.label.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.label.domain.Label;

@Repository
public class LabelRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public LabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long insert(Label label) {
		String sql = "INSERT INTO labels (name,text_color,background_color,description) VALUES (:name,:text_color,:background_color,:description)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", label.getName());
		params.addValue("text_color", label.getTextColor());
		params.addValue("background_color", label.getBackgroundColor());
		params.addValue("description", label.getDescription());

		int result = jdbcTemplate.update(sql, params, keyHolder);
		if (result == 0) {
			throw new CustomException(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
		}
		return keyHolder.getKey().longValue();
	}

	public Long update(Long id, Label label) {
		String sql = "UPDATE labels SET name = :name, text_color = :textColor, background_color = :backgroundColor, description = :description WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		params.addValue("name", label.getName());
		params.addValue("textColor", label.getTextColor());
		params.addValue("backgroundColor", label.getBackgroundColor());
		params.addValue("description", label.getDescription());
		int result = jdbcTemplate.update(sql, params);
		if (result == 0) {
			throw new CustomException(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
		}
		return id;
	}

	public Long delete(Long id) {
		String sql = "UPDATE labels SET is_deleted = TRUE where id = :id";
		int result = jdbcTemplate.update(sql, new MapSqlParameterSource()
			.addValue("id", id));
		if (result == 0) {
			throw new CustomException(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
		}
		return id;
	}

	public Optional<List<Label>> findAll() {
		String sql = "SELECT id, name, text_color, background_color, description "
			+ "FROM labels "
			+ "WHERE is_deleted = FALSE";
		return Optional.of(jdbcTemplate.query(sql, labelRowMapper));
	}

	public Optional<Integer> findMilestonesCount() {
		String sql = "SELECT COUNT(*) FROM milestones WHERE is_deleted = false";
		MapSqlParameterSource params = new MapSqlParameterSource();

		return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, Integer.class));
	}

	private final RowMapper<Label> labelRowMapper = (rs, rowNum) -> Label.builder()
		.id(rs.getLong("id"))
		.name(rs.getString("name"))
		.textColor(rs.getString("text_color"))
		.backgroundColor(rs.getString("background_color"))
		.description(rs.getString("description"))
		.build();
}
