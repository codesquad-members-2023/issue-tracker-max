package com.issuetracker.label.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class LabelRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public LabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public int countExistingLabelIds(List<Long> labelIds) {
		String sql = "SELECT COUNT(id) FROM label WHERE id IN(:labelIds)";
		return jdbcTemplate.queryForObject(sql, Map.of("labelIds", labelIds), Integer.class);
	}

	public boolean existByIds(List<Long> labelIds) {
		String sql = "SELECT IF(COUNT(id) = :size, TRUE, FALSE) "
								 + "FROM label "
							 + " WHERE id IN(:labelIds)";

		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("labelIds", labelIds)
			.addValue("size", labelIds.size());
		return jdbcTemplate.queryForObject(sql, params, Boolean.class);
	}
}
