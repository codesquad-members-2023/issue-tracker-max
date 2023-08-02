package com.issuetrackermax.domain.issue;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.issue.entity.Issue;

@Repository
public class IssueRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(Issue issue) {
		String sql = "INSERT INTO issue(title,is_open,writer_id, milestone_id) VALUES (:title,:isOpen,:writerId,:milestoneId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("title", issue.getTitle(), Types.VARCHAR)
			.addValue("isOpen", issue.getIsOpen(), Types.TINYINT)
			.addValue("writerId", issue.getWriterId(), Types.BIGINT)
			.addValue("milestoneId", issue.getMilestoneId(), Types.BIGINT);

		jdbcTemplate.update(sql, parameters, keyHolder);
		return (Long)Objects.requireNonNull(keyHolder.getKeys().get("ID"));
	}

	public List<Issue> getOpenIssue() {
		String sql = "SELECT id, title, is_open, writer_id, milestone_id,created_at FROM issue WHERE is_open = :isOpen";
		return jdbcTemplate.query(sql, Map.of("isOpen", 1), ISSUE_ROW_MAPPER);
	}

	public List<Issue> getClosedIssue() {
		String sql = "SELECT id, title, is_open, writer_id, milestone_id,created_at FROM issue WHERE is_open = :isOpen";
		return jdbcTemplate.query(sql, Map.of("isOpen", 0), ISSUE_ROW_MAPPER);
	}

	private static final RowMapper<Issue> ISSUE_ROW_MAPPER = (rs, rowNum) ->
		Issue.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.isOpen(rs.getBoolean("is_open"))
			.writerId(rs.getLong("writer_id"))
			.milestoneId(rs.getLong("milestone_id"))
			.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
			.build();
}

