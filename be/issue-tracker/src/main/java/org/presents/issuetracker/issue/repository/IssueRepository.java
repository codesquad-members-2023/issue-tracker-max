package org.presents.issuetracker.issue.repository;

import org.presents.issuetracker.issue.entity.Assignee;
import org.presents.issuetracker.issue.entity.Issue;
import org.presents.issuetracker.issue.entity.IssueLabel;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class IssueRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public IssueRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long save(Issue issue) {
        final String SQL =
                "INSERT INTO issue(author_id, title, contents) " +
                        "VALUES (:authorId, :title, :contents)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("authorId", issue.getAuthorId())
                .addValue("title", issue.getTitle())
                .addValue("contents", issue.getContents());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SQL, params, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Long> addAssignee(List<Assignee> assignees) {
        final String SQL =
                "INSERT INTO assignee(issue_id, user_id) " +
                        "VALUES (:issueId, :userId)";

        return Arrays.stream(
                        jdbcTemplate.batchUpdate(SQL, SqlParameterSourceUtils.createBatch(assignees)))
                .mapToLong(Long::valueOf)
                .boxed()
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteAllAssignee(Long issueId) {
        final String SQL =
                "DELETE FROM assignee " +
                        "WHERE issue_id = :issueId";

        jdbcTemplate.update(SQL, Map.of("issueId", issueId));
    }

    public List<Assignee> findAssigneeByIssueId(Long issueId) {
        final String sql =
                "SELECT * FROM assignee " +
                        "WHERE issue_id = :issueId";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("issueId", issueId);

        return jdbcTemplate.query(sql, params, (rs, rowNum) ->
                Assignee.builder()
                        .userId(rs.getLong("user_id"))
                        .build()
        );
    }

    public List<Long> addLabel(List<IssueLabel> issueLabels) {
        final String SQL =
                "INSERT INTO issue_label(issue_id, label_id) " +
                        "VALUES (:issueId, :labelId)";

        return Arrays.stream(
                        jdbcTemplate.batchUpdate(SQL, SqlParameterSourceUtils.createBatch(issueLabels)))
                .mapToLong(Long::valueOf)
                .boxed()
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteAllLabel(Long issueId) {
        final String SQL =
                "DELETE FROM issue_label " +
                        "WHERE issue_id = :issueId";

        jdbcTemplate.update(SQL, Map.of("issueId", issueId));
    }

    public List<IssueLabel> findLabelByIssueId(Long issueId) {
        final String SQL =
                "SELECT * FROM issue_label " +
                        "WHERE issue_id = :issueId";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("issueId", issueId);

        return jdbcTemplate.query(SQL, params, (rs, rowNum) ->
                IssueLabel.builder()
                        .labelId(rs.getLong("label_id"))
                        .build()
        );
    }

    public void setMilestone(Long issueId, Long milestoneId) {
        final String SQL =
                "UPDATE issue SET milestone_id = :milestoneId " +
                        "WHERE issue_id = :issueId";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("milestoneId", milestoneId)
                .addValue("issueId", issueId);

        jdbcTemplate.update(SQL, params);
    }

    public void deleteMilestone(Long issueId) {
        final String SQL =
                "UPDATE issue SET milestone_id = NULL " +
                        "WHERE issue_id = :issueId";

        jdbcTemplate.update(SQL, Map.of("issueId", issueId));
    }

    public List<Issue> findById(Long issueId) {
        final String SQL =
                "SELECT * FROM issue " +
                        "WHERE issue_id = :issueId";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("issueId", issueId);

        return jdbcTemplate.query(SQL, params, (rs, rowNum) ->
                Issue.builder()
                        .milestoneId(rs.getLong("milestone_id"))
                        .title(rs.getString("title"))
                        .contents(rs.getString("contents"))
                        .build()
        );
    }

}
