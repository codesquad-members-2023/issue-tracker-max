package codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.vo.MemberDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MemberRepository {

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public MemberRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public Map<Long, List<String>> findAllProfilesByIssueIds(List<Long> issueIds ) {
        return issueIds.stream()
                .collect(Collectors.toUnmodifiableMap(
                        issueId -> issueId,
                        this::findAllProfilesByIssueId
                ));
    }

    public List<String> findAllProfilesByIssueId(Long issueId) {
        String sql = "SELECT m.profile " +
                    "FROM member AS m " +
                    "JOIN issue_assignee AS ia ON m.id = ia.assignee_id " +
                    "WHERE ia.issue_id = :issueId";

        return template.queryForList(sql, Map.of("issueId", issueId), String.class);
    }

    public List<MemberDetailsVO> findAllFilters() {
        String sql = "SELECT id, name, profile " +
                "FROM member " +
                "ORDER BY name";
        return template.query(sql, new MapSqlParameterSource(), memberDetailsVORowMapper());
    }

    private final RowMapper<MemberDetailsVO> memberDetailsVORowMapper() {
        return ((rs, rowNum) -> MemberDetailsVO.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .profile(rs.getString("profile"))
                .build());
    }
}
