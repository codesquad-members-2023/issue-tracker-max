package com.issuetracker.member.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MemberRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public boolean existById(Long id) {
		String sql = "SELECT IF(COUNT(id) = 1, TRUE, FALSE) FROM member WHERE id = :id";

		return jdbcTemplate.queryForObject(sql, Map.of("id", id), Boolean.class);
	}

	public boolean existByIds(List<Long> Ids) {
		String sql = "SELECT IF(COUNT(id) = :size, TRUE, FALSE) "
			+ "FROM member "
			+ " WHERE id IN(:memberIds)";

		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberIds", Ids)
			.addValue("size", Ids.size());
		return jdbcTemplate.queryForObject(sql, params, Boolean.class);
	}
}
