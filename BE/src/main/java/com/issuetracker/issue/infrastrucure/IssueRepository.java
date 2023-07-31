package com.issuetracker.issue.infrastrucure;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.Issue;

@Repository
public class IssueRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(Issue issue) {
		String sql = "INSERT INTO issue(title, content, is_open, create_at, milestone_id, author_id) "
			+ "VALUES(:title, :content, :isOpen, :createAt, :milestoneId, :authorId)";

		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("title", issue.getTitle())
			.addValue("content", issue.getContent())
			.addValue("isOpen", issue.getIsOpen())
			.addValue("createAt", issue.getCreateAt())
			.addValue("milestoneId", issue.getMilestone().getId())
			.addValue("authorId", issue.getAuthor().getId());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(sql, param, keyHolder);

		return keyHolder.getKey().longValue();
	}
}
