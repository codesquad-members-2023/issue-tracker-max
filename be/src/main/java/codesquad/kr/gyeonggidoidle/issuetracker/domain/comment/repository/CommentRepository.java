package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CommentRepository {

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public CommentRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long createComment(Long fileId, Comment comment) {
        String sql = "INERT INTO comment (issue_id, author_id, file_id, contents) " +
                "VALUES (:issue_id, :author_id, :file_id, :contents)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("issue_id", comment.getIssueId())
                .addValue("author_id", comment.getAuthorId())
                .addValue("file_id", fileId)
                .addValue("contents", comment.getContents());
        template.update(sql, params, keyHolder, new String[]{"id"});
        return keyHolder.getKey().longValue();
    }

    public Long updateFile(String url) {
        String sql = "INSERT INTO file (url) VALUES (:url)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("url", url);
        template.update(sql, params, keyHolder, new String[]{"id"});
        return keyHolder.getKey().longValue();
    }
}
