package codesquard.app.issue.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import codesquard.app.issue.entity.Issue;

@Repository
public class JdbcIssueRepository implements IssueRepository {

	private final NamedParameterJdbcTemplate template;
	private final SimpleJdbcInsert simpleJdbcInsert;

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
		return null;
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
}
