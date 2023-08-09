package codesquard.app.user_reaction.repository;

import java.util.Map;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JdbcUserReactionRepository implements UserReactionRepository {

	private final NamedParameterJdbcTemplate template;

	@Override
	public Long saveIssueReaction(Long reactionId, Long userId, Long issueId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO user_reaction(reaction_id, user_id, issue_id) "
			+ "VALUES(:reactionId, :userId, :issueId)";
		template.update(sql, saveIssueParamSource(reactionId, userId, issueId), keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	@Override
	public Long saveCommentReaction(Long reactionId, Long userId, Long commentId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO user_reaction(reaction_id, user_id, comment_id) "
			+ "VALUES(:reactionId, :userId, :commentId)";
		template.update(sql, saveCommentParamSource(reactionId, userId, commentId), keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	@Override
	public boolean isExist(Long reactionId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM reaction WHERE id = :id)";
		return Boolean.TRUE.equals(template.queryForObject(sql, Map.of("id", reactionId), Boolean.class));
	}

	private MapSqlParameterSource saveIssueParamSource(Long reactionId, Long userId, Long issueId) {
		return new MapSqlParameterSource()
			.addValue("reactionId", reactionId)
			.addValue("userId", userId)
			.addValue("issueId", issueId);
	}

	private MapSqlParameterSource saveCommentParamSource(Long reactionId, Long userId, Long commentId) {
		return new MapSqlParameterSource()
			.addValue("reactionId", reactionId)
			.addValue("userId", userId)
			.addValue("commentId", commentId);
	}
}
