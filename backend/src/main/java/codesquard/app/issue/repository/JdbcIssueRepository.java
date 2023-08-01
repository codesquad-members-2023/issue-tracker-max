package codesquard.app.issue.repository;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquard.app.comment.entity.Comment;
import codesquard.app.issue.entity.Issue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JdbcIssueRepository implements IssueRepository {

	private final NamedParameterJdbcTemplate template;

	@Override
	public Long save(Issue issue) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO issue(milestone_id, user_id, title, content, status, created_at) VALUES(:milestoneId, :userId, :title, :content, :status, :createdAt)";
		template.update(sql, getSaveRequestParamSource(issue), keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	private MapSqlParameterSource getSaveRequestParamSource(Issue issue) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("milestoneId", issue.getMilestoneId());
		parameterSource.addValue("userId", issue.getUserId());
		parameterSource.addValue("title", issue.getTitle());
		parameterSource.addValue("content", issue.getContent());
		parameterSource.addValue("status", issue.getStatus().name());
		parameterSource.addValue("createdAt", issue.getCreatedAt());
		return parameterSource;
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

}
