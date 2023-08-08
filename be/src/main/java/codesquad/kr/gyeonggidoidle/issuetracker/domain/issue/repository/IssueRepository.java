package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Issue;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueStatusVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueUpdateVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class IssueRepository {

    private final NamedParameterJdbcTemplate template;
    private final RowMapper<IssueVO> issueVOMapper = (rs, rowNum) -> IssueVO.builder()
            .id(rs.getLong("id"))
            .author(rs.getString("author_name"))
            .milestone(rs.getString("milestone_name"))
            .title(rs.getString("title"))
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    public List<IssueVO> findOpenIssues() {
        String sql = "SELECT issue.id, " +
                "milestone.name AS milestone_name, " +
                "member.name AS author_name, " +
                "issue.title, " +
                "issue.created_at " +
                "FROM issue " +
                "LEFT JOIN member ON issue.author_id = member.id " +
                "LEFT JOIN milestone ON issue.milestone_id = milestone.id " +
                "WHERE issue.is_open = true AND issue.is_deleted = false " +
                "ORDER BY issue.id DESC";

        return template.query(sql, issueVOMapper);
    }

    public List<IssueVO> findClosedIssues() {
        String sql = "SELECT issue.id, " +
                "milestone.name AS milestone_name, " +
                "member.name AS author_name, " +
                "issue.title, " +
                "issue.created_at " +
                "FROM issue " +
                "LEFT JOIN member ON issue.author_id = member.id " +
                "LEFT JOIN milestone ON issue.milestone_id = milestone.id " +
                "WHERE issue.is_open = false AND issue.is_deleted = false " +
                "ORDER BY issue.id DESC";

        return template.query(sql, issueVOMapper);
    }

    public void updateIssuesStatus(IssueStatusVO vo) {
        String sql = "UPDATE issue SET is_open = :is_open WHERE id IN (:issueIds)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("is_open", vo.isOpen())
                .addValue("issueIds", vo.getIssueIds());
        template.update(sql, params);
    }

    public Long createIssue(Issue issue) {
        String sql = "INSERT INTO issue (author_id, milestone_id, title) VALUES (:author_id, :milestone_id, :title)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("author_id", issue.getAuthorId())
                .addValue("milestone_id", issue.getMilestoneId())
                .addValue("title", issue.getTitle());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, params, keyHolder, new String[]{"id"});
        return keyHolder.getKey().longValue();
    }

    public void deleteIssue(Long issueId) {
        String sql = "UPDATE issue SET is_deleted = TRUE WHERE id = :issueId";
        template.update(sql, Map.of("issueId", issueId));
    }

    public void updateIssue(IssueUpdateVO vo) {
        String sql = "UPDATE issue SET title = :title, milestone_id = :milestone_id " +
                "WHERE id = :issueId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", vo.getTitle())
                .addValue("milestone_id", vo.getMilestoneId())
                .addValue("issueId", vo.getIssueId());
        template.update(sql, params);
    }
}
