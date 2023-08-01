package com.issuetrackermax.domain.assignee;

import java.sql.Types;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.assignee.entity.Assignee;

@Repository
public class AssigneeRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public Long save(Assignee assignee) {
		String sql = "INSERT INTO assignee(issue_id ,member_id) VALUES (:issueId,:memberId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("issueId", assignee.getIssueId(), Types.BIGINT)
			.addValue("memberId", assignee.getMemberid(), Types.BIGINT);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return (Long)Objects.requireNonNull(keyHolder.getKey());
	}

	public AssigneeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

}
