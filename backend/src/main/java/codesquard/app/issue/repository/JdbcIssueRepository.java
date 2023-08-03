package codesquard.app.issue.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.label.entity.Label;
import codesquard.app.user.entity.User;

@Repository
public class JdbcIssueRepository implements IssueRepository {

	private final NamedParameterJdbcTemplate template;
	private final SimpleJdbcInsert simpleJdbcInsert;

	private static final RowMapper<Issue> issueRowMapper = ((rs, rowNum) -> new Issue(
		rs.getLong("id"),
		rs.getLong("milestone_id"),
		rs.getLong("user_id"),
		rs.getString("title"),
		rs.getString("content"),
		IssueStatus.valueOf(rs.getString("status")),
		rs.getBoolean("is_deleted"),
		rs.getTimestamp("created_at").toLocalDateTime(),
		rs.getTimestamp("modified_at").toLocalDateTime()));
	private static final RowMapper<User> userRowMapper = ((rs, rowNum) -> new User(
		rs.getLong("id"),
		rs.getString("login_id"),
		rs.getString("avatar_url")));
	private static final RowMapper<Label> labelRowMapper = ((rs, rowNum) -> new Label(
		rs.getLong("id"),
		rs.getString("name"),
		rs.getString("color"),
		rs.getString("background")));

	public JdbcIssueRepository(NamedParameterJdbcTemplate template, DataSource dataSource) {
		this.template = template;
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("issue")
			.usingColumns("title", "content", "milestone_id", "user_id")
			.usingGeneratedKeyColumns("id");
	}

	@Override
	public Long save(Issue issue) {
		return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(issue)).longValue();
	}

	@Override
	public List<Issue> findAll() {
		return null;
	}

	@Override
	public Issue findById(Long id) {
		String sql = "SELECT id, title, content, status, status_modified_at, created_at, modified_at, milestone_id, "
			+ "user_id, is_deleted FROM issue WHERE id = :id";
		return template.query(sql, Map.of("id", id), issueRowMapper).get(0);
	}

	@Override
	public Long modify(Issue issue) {
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		return null;
	}

	@Override
	public void saveIssueLabel(Long issueId, Long labelId) {
		String sql = "INSERT INTO issue_label(issue_id, label_id) VALUES(:issueId, :labelId)";
		template.update(sql, Map.of("issueId", issueId, "labelId", labelId));
	}

	@Override
	public void saveIssueAssignee(Long issueId, Long userId) {
		String sql = "INSERT INTO issue_assignee(issue_id, user_id) VALUES(:issueId, :userId)";
		template.update(sql, Map.of("issueId", issueId, "userId", userId));
	}

	@Override
	public void modifyStatus(String status, Long issueId) {
		String sql = "UPDATE issue SET status = :status WHERE id = :id";
		template.update(sql, Map.of("status", status, "id", issueId));
	}

	@Override
	public void modifyTitle(String title, Long issueId) {
		String sql = "UPDATE issue SET title = :title WHERE id = :id";
		template.update(sql, Map.of("title", title, "id", issueId));
	}

	@Override
	public void modifyContent(String content, Long issueId) {
		String sql = "UPDATE issue SET content = :content WHERE id = :id";
		template.update(sql, Map.of("content", content, "id", issueId));
	}

	@Override
	public void modifyMilestone(Long milestoneId, Long issueId) {
		String sql = "UPDATE issue SET milestone_id = :milestoneId WHERE id = :id";
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("milestoneId", milestoneId)
			.addValue("id", issueId);
		template.update(sql, params);
	}

	@Override
	public void deleteAssigneesById(Long issueId) {
		String sql = "DELETE FROM issue_assignee WHERE issue_id = :issueId";
		template.update(sql, Map.of("issueId", issueId));
	}

	@Override
	public void deleteLabelsById(Long issueId) {
		String sql = "DELETE FROM issue_label WHERE issue_id = :issueId";
		template.update(sql, Map.of("issueId", issueId));
	}

	@Override
	public List<User> findAssigneesById(Long issueId) {
		String sql = "SELECT u.id, u.login_id, u.avatar_url FROM user as u "
			+ "JOIN issue_assignee as ia ON ia.user_id = u.id "
			+ "WHERE ia.issue_id = :issueId";
		return template.query(sql, Map.of("issueId", issueId), userRowMapper);
	}

	@Override
	public List<Label> findLabelsById(Long issueId) {
		String sql = "SELECT l.id, l.name, l.color, l.background FROM label as l "
			+ "JOIN issue_label as il ON il.label_id = l.id "
			+ "WHERE il.issue_id = :issueId";
		return template.query(sql, Map.of("issueId", issueId), labelRowMapper);
	}

	@Override
	public boolean exist(Long issueId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM issue WHERE id = :id)";
		return Boolean.TRUE.equals(template.queryForObject(sql, Map.of("id", issueId), Boolean.class));
	}
}
