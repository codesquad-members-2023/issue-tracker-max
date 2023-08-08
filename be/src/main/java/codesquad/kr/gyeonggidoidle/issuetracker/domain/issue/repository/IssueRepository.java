package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
