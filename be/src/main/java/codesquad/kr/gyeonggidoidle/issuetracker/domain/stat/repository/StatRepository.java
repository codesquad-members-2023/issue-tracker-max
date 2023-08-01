package codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class StatRepository {

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public StatRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public StatVO countOverallStats() {
        String sql = "SELECT " +
                "(SELECT COUNT(*) FROM issue WHERE (is_open = TRUE AND is_deleted = FALSE)) AS open_issue_count, " +
                "(SELECT COUNT(*) FROM issue WHERE (is_open = FALSE AND is_deleted = FALSE)) AS closed_issue_count, " +
                "(SELECT COUNT(*) FROM milestone WHERE is_deleted = FALSE) AS milestone_count, " +
                "(SELECT COUNT(*) FROM label WHERE is_deleted = FALSE) AS label_count;";
        SqlParameterSource parameterSource = new MapSqlParameterSource();

        return template.queryForObject(sql, parameterSource, statVORowMapper);
    }

    private final RowMapper<StatVO> statVORowMapper = (rs, rowNum) -> StatVO.builder()
            .openIssueCount(rs.getInt("open_issue_count"))
            .closedIssueCount(rs.getInt("closed_issue_count"))
            .milestoneCount((rs.getInt("milestone_count")))
            .labelCount(rs.getInt("label_count"))
            .build();
}
