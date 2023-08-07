package codesquard.app.comment.repository;

import java.util.Map;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquard.app.comment.entity.Comment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CommentRepository {

	private final NamedParameterJdbcTemplate template;

	public Long save(Comment comment) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO comment(issue_id, user_id, content, created_at) VALUES(:issueId, :userId, :content, :createdAt)";
		template.update(sql, getSaveRequestParamSource(comment), keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	private MapSqlParameterSource getSaveRequestParamSource(Comment comment) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("issueId", comment.getIssueId());
		parameterSource.addValue("userId", comment.getUserId());
		parameterSource.addValue("content", comment.getContent());
		parameterSource.addValue("createdAt", comment.getCreatedAt());
		return parameterSource;
	}

	public Long modify(Comment comment) {
		String sql = "UPDATE comment SET content = :content, modified_at = :modifiedAt WHERE id = :id";
		template.update(sql,
			Map.of("id", comment.getId(), "content", comment.getContent(), "modifiedAt", comment.getModifiedAt()));
		return comment.getId();
	}

	public Long deleteById(Long id) {
		String sql = "UPDATE comment SET is_deleted = 1 WHERE id = :id";
		template.update(sql, Map.of("id", id));
		return id;
	}

	public void deleteByIssueId(Long issueId) {
		String sql = "UPDATE comment SET is_deleted = true WHERE issue_id = :issueId";
		template.update(sql, Map.of("issueId", issueId));
	}

	public boolean isExists(Long id) {
		String sql = "SELECT EXISTS (SELECT 1 FROM comment WHERE id = :id AND is_deleted = false)";
		return Boolean.TRUE.equals(template.queryForObject(sql, Map.of("id", id), Boolean.class));
	}

}
