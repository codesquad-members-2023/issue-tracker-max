package com.issuetrackermax.domain.comment;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.comment.entity.Comment;

@Repository
public class CommentRepository {
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, rowNum) ->
		Comment.builder()
			.id(rs.getLong("id"))
			.content(rs.getString("content"))
			.writerId(rs.getLong("writer_id"))
			.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
			.build();
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public CommentRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

	}

	public Long save(Comment comment) {
		String sql = "INSERT INTO comments(content ,image_url, writer_id) VALUES (:content, :imageUrl, :writerId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("content", comment.getContent(), Types.VARCHAR)
			.addValue("imageUrl", comment.getImageUrl(), Types.VARCHAR)
			.addValue("writerId", comment.getWriterId(), Types.BIGINT);
		jdbcTemplate.update(sql, parameters, keyHolder);
		Map<String, Object> keys = keyHolder.getKeys();
		return (Long)Objects.requireNonNull(keys).get("ID");
	}

	public List<Comment> findByIssueId(Long id) {
		return new ArrayList<>();
	}

	public Optional<Comment> findById(Long id) {
		String sql = "SELECT id, content, writer_id, created_at FROM comments WHERE id = :id ";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("id", id), COMMENT_ROW_MAPPER)));
	}

	public void updateComment(Comment comment, Long id) {
		String sql = "UPDATE comments SET content = :content WHERE id = :id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("content", comment.getContent(), Types.VARCHAR)
			.addValue("id", id, Types.BIGINT);
		jdbcTemplate.update(sql, parameters);
	}
}
