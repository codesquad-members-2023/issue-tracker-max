package com.issuetrackermax.domain.issue;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.issue.entity.Issue;

@Repository
public class IssueRepository {

	private static final RowMapper<Issue> ISSUE_ROW_MAPPER = (rs, rowNum) ->
		Issue.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.isOpen(rs.getBoolean("is_open"))
			.writerId(rs.getLong("writer_id"))
			.milestoneId(rs.getLong("milestone_id"))
			.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
			.build();
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public List<Issue> getOpenIssue() {
		String sql = "SELECT id, title, is_open, writer_id, milestone_id,created_at FROM issue WHERE is_open = :isOpen";
		return jdbcTemplate.query(sql, Map.of("isOpen", 1), ISSUE_ROW_MAPPER);
	}

	public List<Issue> getClosedIssue() {
		String sql = "SELECT id, title, is_open, writer_id, milestone_id,created_at FROM issue WHERE is_open = :isOpen";
		return jdbcTemplate.query(sql, Map.of("isOpen", 0), ISSUE_ROW_MAPPER);
	}
}
