package com.issuetrackermax.domain.issue;

import java.sql.Types;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.issue.entity.IssueWithComment;

@Repository
public class IssueCommentRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueCommentRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(IssueWithComment issueWithComment) {
		String sql = "INSERT INTO issue_comment(issue_id,comment_id) VALUES (:issueId,:commentId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("issueId", issueWithComment.getIssueId(), Types.BIGINT)
			.addValue("commentId", issueWithComment.getCommentId(), Types.BIGINT);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return (Long)Objects.requireNonNull(keyHolder.getKey());
	}
}
