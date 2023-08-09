package codesquad.issueTracker.comment.repository;

import codesquad.issueTracker.comment.dto.CommentRequestDto;
import codesquad.issueTracker.comment.dto.CommentResponseDto;
import codesquad.issueTracker.comment.vo.CommentUser;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
        return jdbcTemplate.query(sql, Map.of("issueId", issueId), commentRowMapper);
    }

    private final RowMapper<CommentResponseDto> commentRowMapper = ((rs, rowNum) -> {
        CommentUser writer = CommentUser.builder()
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
}
