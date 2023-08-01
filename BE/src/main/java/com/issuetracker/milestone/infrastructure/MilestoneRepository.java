package com.issuetracker.milestone.infrastructure;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MilestoneRepository {

	private static final String EXIST_BY_ID_SQL = "SELECT EXISTS(SELECT 1 FROM milestone WHERE id = :id)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MilestoneRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public boolean existById(Long id) {
		return jdbcTemplate.queryForObject(EXIST_BY_ID_SQL, Map.of("id", id), Boolean.class);
	}
}
