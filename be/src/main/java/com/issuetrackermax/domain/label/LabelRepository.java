package com.issuetrackermax.domain.label;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.label.entity.Label;

@Repository
public class LabelRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public LabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(Label label) {
		String sql = "INSERT INTO label(title ,description, text_color, background_color) VALUES (:title,:description,:textColor,:backgroundColor)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("title", label.getTitle(), Types.VARCHAR)
			.addValue("description", label.getDescription(), Types.VARCHAR)
			.addValue("textColor", label.getTextColor(), Types.VARCHAR)
			.addValue("backgroundColor", label.getBackgroundColor(), Types.VARCHAR);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return (Long)Objects.requireNonNull(keyHolder.getKey());
	}

	public Long update(Long id, Label label) {
		String sql = "UPDATE label SET title = :title, description = :description, text_color = :textColor, background_color=:backgroundColor WHERE id = :labelId";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("labelId", id)
			.addValue("title", label.getTitle(), Types.VARCHAR)
			.addValue("description", label.getDescription(), Types.VARCHAR)
			.addValue("textColor", label.getTextColor(), Types.VARCHAR)
			.addValue("backgroundColor", label.getBackgroundColor(), Types.VARCHAR);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return (Long)Objects.requireNonNull(keyHolder.getKey());
	}

	public Long getLabelCount() {
		String sql = "SELECT COUNT(*) FROM label";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
	}

	public List<Label> getLabels() {
		String sql = "SELECT id, title, description, text_color, background_color FROM label";
		return jdbcTemplate.query(sql, Map.of(), LABEL_ROW_MAPPER);
	}

	public Boolean existByIds(List<Long> ids) {
		String sql = "SELECT COUNT(*) FROM label WHERE id IN (:ids)";
		Integer count = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("ids", ids), Integer.class);
		return count != null && count.equals(ids.size());
	}

	public Boolean existByTitle(String title) {
		String sql = "SELECT EXISTS(SELECT 1 FROM label WHERE title =:title)";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("title", title), Boolean.class);
	}

	public Boolean existById(Long id) {
		String sql = "SELECT EXISTS(SELECT 1 FROM label WHERE id =:id)";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("id", id), Boolean.class);
	}

	public Label findById(Long id) {
		String sql = "SELECT id, title, description, text_color, background_color FROM label WHERE id = :id ";
		return DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("id", id), LABEL_ROW_MAPPER));

	}

	public int deleteById(Long id) {
		String sql = "DELETE FROM label WHERE id = :id";
		return jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}

	private static final RowMapper<Label> LABEL_ROW_MAPPER = (rs, rowNum) ->
		Label.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.description(rs.getString("description"))
			.textColor(rs.getString("text_color"))
			.backgroundColor(rs.getString("background_color"))
			.build();
}
