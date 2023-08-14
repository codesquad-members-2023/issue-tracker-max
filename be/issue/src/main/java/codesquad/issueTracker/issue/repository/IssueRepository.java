package codesquad.issueTracker.issue.repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

	public Optional<Issue> findById(Long id) {
		String sql = "SELECT id, milestone_id, user_id, title, content, created_at, is_closed FROM issues WHERE id = :id AND is_deleted = 0";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(
				jdbcTemplate.query(sql, Map.of("id", id), issueRowMapper)));
	}

	public Long modifyStatus(Long issueId, Boolean status) {
		String sql = "UPDATE issues SET is_closed = :status  where id = :id AND is_deleted = 0";
		SqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue("id", issueId)
			.addValue("status", status);
		jdbcTemplate.update(sql, parameterSource);
		return issueId;
	}

	private final RowMapper<Issue> issueRowMapper = (rs, rowNum) -> Issue.builder()
		.id(rs.getLong("id"))
		.milestoneId(rs.getLong("milestone_id"))
		.userId(rs.getLong("user_id"))
		.title(rs.getString("title"))
		.content(rs.getString("content"))
		.createdAt(rs.getObject("created_at", LocalDateTime.class))
		.isClosed(rs.getBoolean("is_closed"))
		.build();

	public Long updateContent(Long id, String modifiedContent) {
		String sql = "UPDATE issues SET content = :modifiedContent WHERE Id = :id";
		SqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("modifiedContent", modifiedContent);
		jdbcTemplate.update(sql, parameterSource);
		return id;

	}

	public Long updateTitle(Long id, String modifiedTitle) {
		String sql = "UPDATE issues SET title = :modifiedTitle WHERE Id = :id";
		SqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("modifiedTitle", modifiedTitle);
		jdbcTemplate.update(sql, parameterSource);
		return id;
	}

	public Long delete(Long id) {
		String sql = "UPDATE issues SET is_deleted = 1 where id = :id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		jdbcTemplate.update(sql, parameters);
		return id;
	}

	public Long resetAssignees(Long issueId) {
		String sql = "DELETE FROM assignees WHERE issue_id = :issueId";
		SqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue("issueId", issueId);
		jdbcTemplate.update(sql, parameterSource);
		return issueId;
	}

	public Long updateMilestone(Long issueId, Long milestoneId) {
		String sql = "UPDATE issues SET milestone_id = :milestoneId WHERE id = :issueId ";
		SqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue("issueId", issueId)
			.addValue("milestoneId", milestoneId);
		jdbcTemplate.update(sql, parameterSource);
		return issueId;
	}
}
