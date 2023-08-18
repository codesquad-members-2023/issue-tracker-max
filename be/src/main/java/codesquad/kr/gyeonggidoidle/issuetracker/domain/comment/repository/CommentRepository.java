package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.Comment;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository.result.CommentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final NamedParameterJdbcTemplate template;

    public List<CommentResult> findByIssueId(Long issueId) {
        String sql = "SELECT comment.id, " +
                "comment.author_id, " +
                "author.name AS author_name, " +
                "comment.contents, " +
                "comment.created_at " +
                "FROM comment " +
                "LEFT JOIN member AS author " +
                "ON author.id = comment.author_id " +
                "WHERE comment.issue_id = :issueId " +
                "ORDER BY comment.created_at";

        return template.query(sql, Map.of("issueId", issueId), commentResultRowMapper());
    }

    public Long save(Comment comment) {
        String sql = "INSERT INTO comment(issue_id, author_id, contents) "
                + "VALUES (:issueId, :authorId, :contents)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("issueId", comment.getIssueId())
                .addValue("authorId", comment.getAuthorId())
                .addValue("contents", comment.getContents());
        template.update(sql, params, keyHolder, new String[]{"id"});

        return keyHolder.getKey().longValue();
    }

    public boolean update(Comment comment) {
        String sql = "UPDATE comment " +
                "SET contents = :contents " +
                "WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", comment.getId())
                .addValue("contents", comment.getContents());

        return template.update(sql, params) > 0;
    }

    public boolean delete(Long commentId) {
        String sql = "UPDATE comment SET is_deleted = TRUE WHERE id = :commentId";

        return template.update(sql, Map.of("commentId", commentId)) > 0;
    }

    private RowMapper<CommentResult> commentResultRowMapper() {
        return ((rs, rowNum) -> CommentResult.builder()
                .id(rs.getLong("id"))
                .authorId(rs.getLong("author_id"))
                .authorName(rs.getString("author_name"))
                .contents(rs.getString("contents"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build());
    }
}
