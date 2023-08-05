package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class IssueRepository {

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public IssueRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<IssueVO> findOpenIssues() {
        String sql = "SELECT i.id, " +
                "m.name AS milestone_name, " +
                "me.name AS author_name, " +
                "i.title, " +
                "i.created_at " +
                "FROM issue i " +
                "LEFT JOIN member me ON i.author_id = me.id " +
                "LEFT JOIN milestone m ON i.milestone_id = m.id " +
                "WHERE i.is_open = true " +
                "ORDER BY i.id DESC";

        return template.query(sql, issueVOMapper);
    }

    public List<IssueVO> findClosedIssues() {
        String sql = "SELECT i.id, " +
                "m.name AS milestone_name, " +
                "me.name AS author_name, " +
                "i.title, " +
                "i.created_at " +
                "FROM issue i " +
                "LEFT JOIN member me ON i.author_id = me.id " +
                "LEFT JOIN milestone m ON i.milestone_id = m.id " +
                "WHERE i.is_open = false " +
                "ORDER BY i.id DESC";

        return template.query(sql, issueVOMapper);
    }

    private final RowMapper<IssueVO> issueVOMapper = (rs, rowNum) -> IssueVO.builder()
            .id(rs.getLong("id"))
            .author(rs.getString("author_name"))
            .milestone(rs.getString("milestone_name"))
            .title(rs.getString("title"))
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
            .build();
}
