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

	private static final String EXIST_BY_ID_SQL = "SELECT EXISTS(SELECT 1 FROM member WHERE id = :id)";
	private static final String EXIST_BY_IDS_SQL = "SELECT IF(COUNT(id) = :size, TRUE, FALSE) FROM member WHERE id IN(:memberIds)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MemberRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public boolean existById(Long id) {
		return jdbcTemplate.queryForObject(EXIST_BY_ID_SQL, Map.of("id", id), Boolean.class);
	}

	public boolean existByIds(List<Long> Ids) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberIds", Ids)
			.addValue("size", Ids.size());
		return jdbcTemplate.queryForObject(EXIST_BY_IDS_SQL, params, Boolean.class);
	}
}
