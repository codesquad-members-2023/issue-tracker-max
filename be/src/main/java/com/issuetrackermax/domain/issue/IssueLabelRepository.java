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

import com.issuetrackermax.domain.issue.entity.IssueWithLabel;

@Repository
public class IssueLabelRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueLabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(IssueWithLabel issueWithLabel) {
		String sql = "INSERT INTO issue_label(issue_id,label_id) VALUES (:issueId,:labelId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("issueId", issueWithLabel.getIssueId(), Types.BIGINT)
			.addValue("labelId", issueWithLabel.getLabelId(), Types.BIGINT);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	public int deleteAppliedLabels(Long issueId) {
		String sql = "DELETE FROM issue_label WHERE issue_id = :issueId";
		return jdbcTemplate.update(sql, new MapSqlParameterSource("issueId", issueId));
	}

	public int applyLabels(Long issueId, Long labelId) {
		String sql = "INSERT INTO issue_label(issue_id, label_id) VALUES (:issueId, :labelId)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		return jdbcTemplate.update(sql, new MapSqlParameterSource()
			.addValue("issueId", issueId)
			.addValue("labelId", labelId), keyHolder);
	}
}
