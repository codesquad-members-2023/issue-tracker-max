package com.issuetracker.label.infrastructure;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.label.domain.Label;
import com.issuetracker.label.domain.LabelRepository;

@Repository
public class JdbcLabelRepository implements LabelRepository {

	private static final String EXIST_BY_IDS_SQL = "SELECT IF(COUNT(id) = :size, TRUE, FALSE) FROM label WHERE id IN(:labelIds)";
	private static final String SAVE_SQL = "INSERT INTO label(title, description, color) VALUE(:title, :description, :color)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcLabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public boolean existByIds(List<Long> labelIds) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("labelIds", labelIds)
			.addValue("size", labelIds.size());
		return jdbcTemplate.queryForObject(EXIST_BY_IDS_SQL, params, Boolean.class);
	}

	@Override
	public Long save(Label label) {
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("title", label.getTitle())
			.addValue("description", label.getDescription())
			.addValue("color", label.getColor());
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(SAVE_SQL, param, keyHolder);
		return keyHolder.getKey().longValue();
	}
}
