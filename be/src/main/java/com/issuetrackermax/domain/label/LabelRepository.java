package com.issuetrackermax.domain.label;

import java.sql.Types;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
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

	public Long getLabelCount() {
		String sql = "SELECT COUNT(*) FROM label";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
	}

	public Boolean existByIds(List<Long> ids) {
		String sql = "SELECT COUNT(*) FROM label WHERE id IN (:ids)";
		Integer count = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("ids", ids), Integer.class);
		return count != null && count.equals(ids.size());
	}
}
