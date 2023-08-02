package com.issuetracker.label.infrastructure;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class LabelRepository {

	private static final String EXIST_BY_IDS_SQL = "SELECT IF(COUNT(id) = :size, TRUE, FALSE) FROM label WHERE id IN(:labelIds)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public LabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public boolean existByIds(List<Long> labelIds) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("labelIds", labelIds)
			.addValue("size", labelIds.size());
		return jdbcTemplate.queryForObject(EXIST_BY_IDS_SQL, params, Boolean.class);
	}
}
