package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Issue;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueStatusResult;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueUpdateResult;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class IssueRepository {

    private final NamedParameterJdbcTemplate template;

    public Long saveIssue(Issue issue) {
        String sql = "INSERT INTO issue (author_id, milestone_id, title) VALUES (:author_id, :milestone_id, :title)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("author_id", issue.getAuthorId())
                .addValue("milestone_id", issue.getMilestoneId())
                .addValue("title", issue.getTitle());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, params, keyHolder, new String[]{"id"});
        return keyHolder.getKey().longValue();
    }

    public void updateIssue(IssueUpdateResult vo) {
        String sql = "UPDATE issue SET title = :title, milestone_id = :milestone_id " +
                "WHERE id = :issueId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", vo.getTitle())
                .addValue("milestone_id", vo.getMilestoneId())
                .addValue("issueId", vo.getIssueId());
        template.update(sql, params);
    }

    public void updateIssuesStatus(IssueStatusResult vo) {
        String sql = "UPDATE issue SET is_open = :is_open WHERE id IN (:issueIds)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("is_open", vo.isOpen())
                .addValue("issueIds", vo.getIssueIds());
        template.update(sql, params);
    }

    public void deleteIssue(Long issueId) {
        String sql = "UPDATE issue SET is_deleted = TRUE WHERE id = :issueId";
        template.update(sql, Map.of("issueId", issueId));
    }

    private final RowMapper<IssueSearchResult> issueSearchResultRowMapper = (rs, rowNum) -> IssueSearchResult.builder()
            .id(rs.getLong("id"))
            .author(rs.getString("author_name"))
            .milestone(rs.getString("milestone_name"))
            .title(rs.getString("title"))
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
            .build();
}
