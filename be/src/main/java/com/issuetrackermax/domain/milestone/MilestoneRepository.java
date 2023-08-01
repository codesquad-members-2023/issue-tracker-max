package com.issuetrackermax.domain.milestone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MilestoneRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MilestoneRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long getMilestoneCount() {
		String sql = "SELECT COUNT(*) FROM milestone";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
	}

}
