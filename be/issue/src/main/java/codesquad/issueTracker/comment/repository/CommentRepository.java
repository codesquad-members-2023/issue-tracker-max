package codesquad.issueTracker.comment.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquad.issueTracker.comment.domain.Comment;
import codesquad.issueTracker.comment.dto.CommentRequestDto;
import codesquad.issueTracker.comment.dto.CommentResponseDto;
import codesquad.issueTracker.comment.vo.CommentUserVo;

@Repository
public class CommentRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public CommentRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public List<CommentResponseDto> findByIssueId(Long issueId) {
		String sql = "SELECT c.*, u.name, u.profile_img "
			+ "FROM comments c "
			+ "JOIN users u ON c.user_id = u.id "
			+ "WHERE c.issue_id = :issueId AND c.is_deleted = false";
		return jdbcTemplate.query(sql, Map.of("issueId", issueId), commentResponseDtoRowMapper);
	}

	private final RowMapper<CommentResponseDto> commentResponseDtoRowMapper = ((rs, rowNum) -> {
		CommentUserVo writer = CommentUserVo.builder()
			.name(rs.getString("name"))
			.profileImg(rs.getString("profile_img"))
			.build();

		return CommentResponseDto.builder()
			.id(rs.getLong("id"))
			.writer(writer)
			.content(rs.getString("content"))
			.createdAt(rs.getTimestamp("created_At").toLocalDateTime())
			.build();
	});

	public Optional<Long> create(Long userId, Long issueId, CommentRequestDto commentRequestDto) {
		String sql = "INSERT INTO comments (user_id, issue_id, content) VALUES (:userId, :issueId, :content)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userId", userId);
		params.addValue("issueId", issueId);
		params.addValue("content", commentRequestDto.getContent());

		int updatedRow = jdbcTemplate.update(sql, params, keyHolder);

		if (updatedRow > 0) {
			return Optional.ofNullable(keyHolder.getKey().longValue());
		}
		return Optional.empty();
	}

	public Optional<Long> update(Long commentId, CommentRequestDto commentRequestDto) {
		String sql = "UPDATE comments SET content = :content WHERE id = :commentId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("content", commentRequestDto.getContent());
		params.addValue("commentId", commentId);

		int updatedRow = jdbcTemplate.update(sql, params);

		if (updatedRow > 0) {
			return Optional.ofNullable(commentId);
		}
		return Optional.empty();
	}

	public Optional<Long> deleteById(Long commentId) {
		String sql = "UPDATE comments SET is_deleted = true WHERE id = :commentId";
		int updatedRow = jdbcTemplate.update(sql, Map.of("commentId", commentId));

		if (updatedRow > 0) {
			return Optional.ofNullable(commentId);
		}
		return Optional.empty();
	}

	public Optional<Comment> findById(Long commentId) {
		String sql = "SELECT * FROM comments WHERE id = :commentId";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(
				jdbcTemplate.query(sql, Map.of("commentId", commentId), commentRowMapper)));
	}

	public Optional<Comment> findExistCommentById(Long commentId) {
		String sql = "SELECT * FROM comments WHERE id = :commentId AND is_deleted = false";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(
				jdbcTemplate.query(sql, Map.of("commentId", commentId), commentRowMapper)));
	}

	private final RowMapper<Comment> commentRowMapper = ((rs, rowNum) -> Comment.builder()
		.id(rs.getLong("id"))
		.userId(rs.getLong("user_id"))
		.issueId(rs.getLong("issue_id"))
		.content(rs.getString("content"))
		.createdAt(rs.getTimestamp("created_At").toLocalDateTime())
		.build());
}
