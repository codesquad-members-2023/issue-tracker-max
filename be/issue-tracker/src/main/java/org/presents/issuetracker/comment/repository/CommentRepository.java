package org.presents.issuetracker.comment.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public void deleteByIssueId(Long issueId) {
		final String sql = "UPDATE comment SET is_deleted = true WHERE issue_id = :issueId";

		MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

		jdbcTemplate.update(sql, params);
	}
}
