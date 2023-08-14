package org.presents.issuetracker.comment.repository;

import org.presents.issuetracker.comment.entity.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CommentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CommentRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("comment")
                .usingColumns("issue_id", "author_id", "contents")
                .usingGeneratedKeyColumns("comment_id");
    }

    public Long save(Comment comment) {
        return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(comment)).longValue();
    }

    public void deleteByIssueId(Long issueId) {
        final String sql = "UPDATE comment SET is_deleted = true WHERE issue_id = :issueId";

        MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

        jdbcTemplate.update(sql, params);
    }

    public Comment findById(Long id) {
        final String sql = "SELECT * FROM comment WHERE comment_id = :commentId";

        MapSqlParameterSource params = new MapSqlParameterSource("commentId", id);

        RowMapper<Comment> mapper = (rs, rowNum) -> Comment.of(
                rs.getLong("comment_id"),
                rs.getLong("issue_id"),
                rs.getLong("author_id"),
                rs.getString("contents"),
                rs.getTimestamp("created_at").toLocalDateTime());

        return jdbcTemplate.queryForObject(sql, params, mapper);
    }
}
