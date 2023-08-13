package codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.Member;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.vo.MemberDetailsVO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


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

    public void addIssueAssignees(Long issueId, List<Long> assigneeIds) {
        String sql = "INSERT INTO issue_assignee (issue_id, assignee_id) VALUES (:issue_id, :assignee_id)";
        SqlParameterSource[] batchParams = generateParameters(issueId, assigneeIds);
        template.batchUpdate(sql, batchParams);
    }

    public void updateIssueAssignees(Long issueId, List<Long> assigneeIds) {
        String sql = "DELETE FROM issue_assignee WHERE issue_id = :issueId";
        template.update(sql, Map.of("issueId", issueId));
        addIssueAssignees(issueId, assigneeIds);
    }

    private SqlParameterSource[] generateParameters(Long issueId, List<Long> assigneeIds) {
        return assigneeIds.stream()
                .map(assigneeId -> generateParameter(issueId, assigneeId))
                .toArray(SqlParameterSource[]::new);
    }

    private SqlParameterSource generateParameter(Long issueId, Long assigneeId) {
        return new MapSqlParameterSource()
                .addValue("issue_id", issueId)
                .addValue("assignee_id", assigneeId);
    }

    public List<MemberDetailsVO> findAllFilters() {
        String sql = "SELECT id, name, profile " +
                "FROM member " +
                "ORDER BY name";
        return template.query(sql, new MapSqlParameterSource(), memberDetailsVORowMapper());
    }

    public Member findByEmail(String email) {
        String sql = "SELECT id, email, name, password, profile FROM member WHERE email = :email";
        try {
            return template.queryForObject(sql, Map.of("email", email), memberRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public void saveMember(Member member) {
        String sql = "INSERT INTO member(name, email, password, profile) VALUES(:name, :email, :password, :profile) ";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", member.getName())
                .addValue("email", member.getEmail())
                .addValue("password", member.getPassword())
                .addValue("profile", member.getProfile());
        template.update(sql, params);
    }

    private final RowMapper<MemberDetailsVO> memberDetailsVORowMapper() {
        return ((rs, rowNum) -> MemberDetailsVO.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .profile(rs.getString("profile"))
                .build());
    }

    private final RowMapper<Member> memberRowMapper() {
        return ((rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .name(rs.getString("name"))
                .password(rs.getString("password"))
                .profile(rs.getString("profile"))
                .build());
    }
}
