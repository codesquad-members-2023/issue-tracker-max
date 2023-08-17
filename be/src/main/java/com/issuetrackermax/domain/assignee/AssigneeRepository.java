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

	public AssigneeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(Assignee assignee) {
		String sql = "INSERT INTO assignee(issue_id ,member_id) VALUES (:issueId,:memberId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("issueId", assignee.getIssueId(), Types.BIGINT)
			.addValue("memberId", assignee.getMemberId(), Types.BIGINT);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	public int applyAssigneesToIssue(Long issueId, Long memberId) {
		String sql = "INSERT INTO assignee(issue_id, member_id) VALUES (:issueId, :memberId)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		return jdbcTemplate.update(sql, new MapSqlParameterSource()
			.addValue("issueId", issueId)
			.addValue("memberId", memberId), keyHolder);
	}

	public int deleteAppliedAssignees(Long issueId) {
		String sql = "DELETE FROM assignee WHERE issue_id = :issueId";
		return jdbcTemplate.update(sql, new MapSqlParameterSource("issueId", issueId));
	}

}
