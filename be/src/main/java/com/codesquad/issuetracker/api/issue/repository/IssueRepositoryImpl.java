package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.Issue;
import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class IssueRepositoryImpl implements IssueRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public Optional<Long> countIssuesBy(Long organizationId) {
        String sql = "SELECT COUNT(id) FROM issue WHERE organization_id = :organizationId";
        return Optional.ofNullable(template.queryForObject(sql, Map.of("organizationId", organizationId), Long.class));
    }

    @Override
    public Optional<Long> save(Issue issue) {
        String queryForIssueSaved =
                "INSERT INTO issue (organization_id, milestone_id, member_id, title, number, is_closed, created_time) "
                        + "VALUES (:organizationId, :milestoneId, :memberId, :title, :number, :isClosed, now())";

        SqlParameterSource params = new BeanPropertySqlParameterSource(issue);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(queryForIssueSaved, params, keyHolder);
        return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
    }

    @Override
    public void save(List<?> options) {
        if (options.get(0).getClass() == IssueAssignee.class) {
            saveIssueAssignees(options);
        }
        if (options.get(0).getClass() == IssueLabel.class) {
            saveIssueLabels(options);
        }
    }

    private void saveIssueAssignees(List<?> issueAssignees) {
        String sql = "INSERT INTO issue_assignee (issue_id, member_id) VALUES (:issueId, :memberId)";
        template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(issueAssignees));
    }

    private void saveIssueLabels(List<?> issueLabels) {
        String sql = "INSERT INTO issue_label (issue_id, label_id) VALUES (:issueId, :labelId)";
        template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(issueLabels));
    }

    @Override
    @Transactional
    public void delete(Long issueId) {
        String queryForDeleteIssue = "DELETE FROM issue WHERE id = :issueId";
        String queryForDeleteIssueAssignees = "DELETE FROM issue_assignee WHERE issue_id = :issueId";
        String queryForDeleteIssueLabels = "DELETE FROM issue_label WHERE issue_id = :issueId";

        Map<String, Long> param = Collections.singletonMap("issueId", issueId);

        template.update(queryForDeleteIssue, param);
        template.update(queryForDeleteIssueAssignees, param);
        template.update(queryForDeleteIssueLabels, param);
    }

}
