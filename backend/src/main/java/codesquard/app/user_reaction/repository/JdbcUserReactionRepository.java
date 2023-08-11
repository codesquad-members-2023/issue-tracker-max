package codesquard.app.user_reaction.repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquard.app.issue.dto.response.userReactionResponse;
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
	public List<userReactionResponse> findIssueReactionBy(Long issueId, Long userId) {
		String sql = "SELECT r.unicode, IF (EXISTS (SELECT 1 FROM user_reaction as ur WHERE ur.user_id = :userId AND "
			+ "u.id = ur.user_id AND ur.reaction_id = r.id AND ur.issue_id = :issueId), ur.id, null) as selected FROM reaction as r "
			+ "LEFT JOIN user_reaction as ur ON ur.reaction_id = r.id AND ur.issue_id = :issueId "
			+ "LEFT JOIN user as u ON u.id = ur.user_id";
		return template.query(sql, Map.of("issueId", issueId, "userId", userId),
			userReactionResponseRowMapper(issueId));
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM user_reaction WHERE id = :id";
		template.update(sql, Map.of("id", id));
	}

	@Override
	public boolean isExistReaction(Long reactionId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM reaction WHERE id = :id)";
		return Boolean.TRUE.equals(template.queryForObject(sql, Map.of("id", reactionId), Boolean.class));
	}

	@Override
	public boolean isExistUserReaction(Long id) {
		String sql = "SELECT EXISTS (SELECT 1 FROM user_reaction WHERE id = :id)";
		return Boolean.TRUE.equals(template.queryForObject(sql, Map.of("id", id), Boolean.class));
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

	private RowMapper<userReactionResponse> userReactionResponseRowMapper(Long issueId) {
		String sql = "SELECT u.login_id FROM user as u "
			+ "JOIN user_reaction as ur ON ur.issue_id = :issueId AND ur.user_id = u.id "
			+ "JOIN reaction as r ON r.unicode = :unicode AND r.id = ur.reaction_id";
		return ((rs, rowNum) -> new userReactionResponse(
			rs.getString("unicode"),
			template.query(sql, Map.of("issueId", issueId, "unicode", rs.getString("unicode")), usersRowMapper()),
			rs.getLong("selected")));
	}

	private RowMapper<String> usersRowMapper() {
		return ((rs, rowNum) -> rs.getString("login_id"));
	}
}
