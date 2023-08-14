package codesquad.issueTracker.issue.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquad.issueTracker.issue.domain.Issue;

@Repository
public class IssueRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long insert(Issue issue) {
		String sql = "INSERT INTO issues(title, content, milestone_id, user_id) VALUES (:title,:content,:milestoneId, :userId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("title", issue.getTitle())
			.addValue("content", issue.getContent())
			.addValue("milestoneId", issue.getMilestoneId())
			.addValue("userId", issue.getUserId());
		jdbcTemplate.update(sql, parameters, keyHolder);
		return keyHolder.getKey().longValue();
	}

	public Long insertLabels(Long issueId, Long labelId) {
		String sql = "INSERT INTO issues_labels(issue_id, label_id) VALUES(:issueId, :labelId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("issueId", issueId)
			.addValue("labelId", labelId);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return keyHolder.getKey().longValue();
	}

	public Long insertAssignees(Long issueId, Long userId) {
		String sql = "INSERT INTO assignees(issue_id, user_id) VALUES(:issueId, :userId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("issueId", issueId)
			.addValue("userId", userId);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return keyHolder.getKey().longValue();
	}
}
