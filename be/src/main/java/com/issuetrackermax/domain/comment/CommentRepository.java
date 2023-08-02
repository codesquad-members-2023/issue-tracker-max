package com.issuetrackermax.domain.comment;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.comment.entity.Comment;

@Repository
public class CommentRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public CommentRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public List<Comment> findByIssueId(Long issueId) {
		return null;
	}

	public void save(Comment comment) {
	}
}
