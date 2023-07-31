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

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public AssigneeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(Assignee assignee) {
		String sql = "INSERT INTO assignee(issue_id, member_id) "
			+ "VALUES(:issueId, :memberId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(assignee), keyHolder);
		return keyHolder.getKey().longValue();
	}

	public List<Long> saveAll(List<Assignee> assignees) {
		return assignees.stream()
			.map(this::save)
			.collect(Collectors.toUnmodifiableList());
	}
}
