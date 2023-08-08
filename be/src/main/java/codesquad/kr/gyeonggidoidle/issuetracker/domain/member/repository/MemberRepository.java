package codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.vo.MemberDetailsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final NamedParameterJdbcTemplate template;

    public Map<Long, List<String>> findAllProfilesByIssueIds(List<Long> issueIds) {
        return issueIds.stream()
                .collect(Collectors.toUnmodifiableMap(
                        issueId -> issueId,
                        this::findAllProfilesByIssueId
                ));
    }

    public List<String> findAllProfilesByIssueId(Long issueId) {
        String sql = "SELECT member.profile " +
                "FROM member " +
                "JOIN issue_assignee ON member.id = issue_assignee.assignee_id " +
                "WHERE issue_assignee.issue_id = :issueId";

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
