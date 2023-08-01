package com.issuetracker.issue.infrastrucure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.Assignee;

@Repository
public class AssigneeRepository {

	private static final String SAVE_ALL_SQL = "INSERT INTO assignee(issue_id, member_id) VALUES(:issueId, :memberId)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public AssigneeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public int[] saveAll(List<Assignee> assignees) {
		BeanPropertySqlParameterSource[] params = assignees.stream()
			.map(BeanPropertySqlParameterSource::new)
			.toArray(BeanPropertySqlParameterSource[]::new);
		return jdbcTemplate.batchUpdate(SAVE_ALL_SQL, params);
	}
}
