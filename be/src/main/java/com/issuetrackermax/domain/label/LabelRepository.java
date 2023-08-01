package com.issuetrackermax.domain.label;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LabelRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public LabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long getLabelCount() {
		String sql = "SELECT COUNT(*) FROM label";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
	}
}
