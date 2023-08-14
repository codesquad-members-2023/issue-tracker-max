package org.presents.issuetracker.comment.repository;

import org.presents.issuetracker.comment.entity.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class CommentRepository {
    private static final int NOT_DELETED_FLAG = 0;
    private static final int DELETED_FLAG = 1;
    private static final int NO_RECORD = 0;

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

    public void update(Comment comment) {
        final String sql = "UPDATE comment SET contents = :contents WHERE comment_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("contents", comment.getContents())
                .addValue("id", comment.getId());

        jdbcTemplate.update(sql, params);
    }

    public void deleteById(Long id) {
        final String sql = "UPDATE comment SET is_deleted = :deleted_flag WHERE comment_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("deleted_flag", DELETED_FLAG);

        jdbcTemplate.update(sql, params);
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

    public boolean existsById(Long id) {
        final String sql = "SELECT COUNT(*) FROM comment WHERE comment_id = :id AND is_deleted = :not_deleted_flag";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Map.of(
                        "id", id,
                        "not_deleted_flag", NOT_DELETED_FLAG
                ),
                Integer.class
        );

        return Optional.ofNullable(count).orElse(NO_RECORD) > NO_RECORD;
    }
}
