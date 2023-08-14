package com.issuetrackermax.domain.comment;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public CommentRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

	}

	public Long save(Comment comment) {
		String sql = "INSERT INTO comments(content ,writer_id) VALUES (:content, :writerId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("content", comment.getContent(), Types.VARCHAR)
			.addValue("writerId", comment.getWriterId(), Types.BIGINT);
		jdbcTemplate.update(sql, parameters, keyHolder, new String[] {"id"});
		return keyHolder.getKey().longValue();

	}

	public List<Comment> findByIssueId(Long id) {
		return new ArrayList<>();
	}
}
