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

import codesquard.app.issue.dto.response.IssueCommentsResponse;
import codesquard.app.issue.dto.response.IssueMilestoneCountResponse;
import codesquard.app.issue.dto.response.IssueMilestoneResponse;
import codesquard.app.issue.dto.response.IssueReadResponse;
import codesquard.app.issue.dto.response.IssueUserResponse;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.label.entity.Label;
import codesquard.app.user.entity.User;

@Repository
public class JdbcIssueRepository implements IssueRepository {

	private final NamedParameterJdbcTemplate template;
	private final SimpleJdbcInsert simpleJdbcInsert;

	private static final RowMapper<IssueReadResponse> issueReadResponseRowMapper = ((rs, rowNum) -> new IssueReadResponse(
		rs.getLong("id"),
		rs.getString("title"),
		IssueStatus.valueOf(rs.getString("status")),
		rs.getTimestamp("status_modified_at") != null ? rs.getTimestamp("status_modified_at").toLocalDateTime() : null,
		rs.getTimestamp("created_at").toLocalDateTime(),
		rs.getTimestamp("modified_at") != null ? rs.getTimestamp("modified_at").toLocalDateTime() : null,
		rs.getString("content"),
		new IssueMilestoneResponse(rs.getLong("milestone_id"), rs.getString("name"), new IssueMilestoneCountResponse()),
		new IssueUserResponse(rs.getLong("writer_id"), rs.getString("login_id"), rs.getString("avatar_url"))));
	private static final RowMapper<User> userRowMapper = ((rs, rowNum) -> new User(
		rs.getLong("id"),
		rs.getString("login_id"),
		rs.getString("avatar_url")));
	private static final RowMapper<Label> labelRowMapper = ((rs, rowNum) -> new Label(
		rs.getLong("id"),
		rs.getString("name"),
		rs.getString("color"),
		rs.getString("background")));
	private static final RowMapper<IssueMilestoneCountResponse> issueMilestoneCountResponseRowMapper = ((rs, rowNum) -> new IssueMilestoneCountResponse(
		rs.getInt("openedIssueCount"),
		rs.getInt("closedIssueCount")));
	private static final RowMapper<IssueCommentsResponse> issueCommentsResponseRowMapper = ((rs, rowNum) -> new IssueCommentsResponse(
		rs.getLong("id"),
		rs.getString("login_id"),
		rs.getString("avatar_url"),
		rs.getString("content"),
		rs.getTimestamp("created_at").toLocalDateTime(),
		rs.getTimestamp("modified_at").toLocalDateTime()));

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
	public IssueReadResponse findBy(Long id) {
		String sql = "SELECT i.id, i.title, i.content, i.status, i.status_modified_at, i.created_at, "
			+ "i.modified_at, m.id as milestone_id, m.name, u.id as writer_id, u.login_id, u.avatar_url FROM issue as i "
			+ "LEFT JOIN milestone as m ON m.id = i.milestone_id "
			+ "LEFT JOIN user as u ON u.id = i.user_id "
			+ "WHERE i.id = :id AND is_deleted = false";
		return template.queryForObject(sql, Map.of("id", id), issueReadResponseRowMapper);
	}

	@Override
	public Long modify(Issue issue) {
		return null;
	}

	@Override
	public void deleteBy(Long id) {
		String sql = "UPDATE issue SET is_deleted = true WHERE id = :id";
		template.update(sql, Map.of("id", id));
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
	public void deleteIssueAssigneesBy(Long issueId) {
		String sql = "DELETE FROM issue_assignee WHERE issue_id = :issueId";
		template.update(sql, Map.of("issueId", issueId));
	}

	@Override
	public void deleteIssueLabelsBy(Long issueId) {
		String sql = "DELETE FROM issue_label WHERE issue_id = :issueId";
		template.update(sql, Map.of("issueId", issueId));
	}

	@Override
	public List<User> findAssigneesBy(Long issueId) {
		String sql = "SELECT u.id, u.login_id, u.avatar_url FROM user as u "
			+ "JOIN issue_assignee as ia ON ia.user_id = u.id "
			+ "WHERE ia.issue_id = :issueId";
		return template.query(sql, Map.of("issueId", issueId), userRowMapper);
	}

	@Override
	public List<Label> findLabelsBy(Long issueId) {
		String sql = "SELECT l.id, l.name, l.color, l.background FROM label as l "
			+ "JOIN issue_label as il ON il.label_id = l.id "
			+ "WHERE il.issue_id = :issueId";
		return template.query(sql, Map.of("issueId", issueId), labelRowMapper);
	}

	@Override
	public boolean exist(Long issueId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM issue WHERE id = :id AND is_deleted = false)";
		return Boolean.TRUE.equals(template.queryForObject(sql, Map.of("id", issueId), Boolean.class));
	}

	@Override
	public IssueMilestoneCountResponse countIssueBy(Long milestoneId) {
		String sql = "SELECT COUNT(CASE WHEN status = 'OPENED' AND is_deleted = false AND milestone_id = :milestoneId "
			+ "THEN 1 END) as openedIssueCount, COUNT(CASE WHEN status = 'CLOSED' AND is_deleted = false "
			+ "AND milestone_id = :milestoneId THEN 1 END) as closedIssueCount FROM issue";
		return template.queryForObject(sql, Map.of("milestoneId", milestoneId), issueMilestoneCountResponseRowMapper);
	}

	@Override
	public List<IssueCommentsResponse> findCommentsBy(Long issueId) {
		String sql = "SELECT c.id, c.content, c.created_at, c.modified_at, u.login_id, u.avatar_url FROM comment as c "
			+ "JOIN user as u ON u.id = c.user_id "
			+ "WHERE c.issue_id = :issueId";
		return template.query(sql, Map.of("issueId", issueId), issueCommentsResponseRowMapper);
	}
}
