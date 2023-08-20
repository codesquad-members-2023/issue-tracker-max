package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.Milestone;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneVO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class MilestoneRepository {

    private final NamedParameterJdbcTemplate template;

    public List<MilestoneDetailsVO> findOpenMilestones() {
        String sql = "SELECT id, name, description, due_date " +
                "FROM milestone " +
                "WHERE is_deleted = FALSE AND is_open = TRUE " +
                "ORDER BY name";
        return template.query(sql, new MapSqlParameterSource(), milestoneDetailsVORowMapper());
    }

    public List<MilestoneDetailsVO> findClosedMilestones() {
        String sql = "SELECT id, name, description, due_date " +
                "FROM milestone " +
                "WHERE is_deleted = FALSE AND is_open = FALSE " +
                "ORDER BY name";
        return template.query(sql, new MapSqlParameterSource(), milestoneDetailsVORowMapper());
    }

    public List<MilestoneDetailsVO> getMilestoneFilter() {
        String sql = "SELECT id, name " +
                "FROM milestone " +
                "ORDER BY name";
        return template.query(sql, new MapSqlParameterSource(), milestoneSipmleVORowMapper());
    }

    public MilestoneVO getMilestoneByIssueId(Long issueId) {
        String sql = "SELECT " +
                "milestone.name AS name, " +
                "SUM(CASE WHEN issue.is_open = TRUE THEN 1 ELSE 0 END) AS open_issue_count, " +
                "SUM(CASE WHEN issue.is_open = FALSE THEN 1 ELSE 0 END) AS closed_issue_count " +
                "FROM milestone " +
                "LEFT JOIN issue ON milestone.id = issue.milestone_id " +
                "WHERE issue.milestone_id = (SELECT milestone_id FROM issue WHERE id = :issueId) " +
                "GROUP BY milestone.name";

        return template.queryForObject(sql, Map.of("issueId", issueId), milestoneVORowMapper());
    }

    public boolean save(Milestone milestone) {
        String sql = "INSERT INTO milestone(name, description, due_date) "
                + "VALUES (:name, :description, :dueDate)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", milestone.getName())
                .addValue("description", milestone.getDescription())
                .addValue("dueDate", milestone.getDueDate());
        int result = template.update(sql, params);
        return result > 0;
    }

    public boolean update(Milestone milestone) {
        String sql = "UPDATE milestone SET name = :name, description = :description, due_date = :dueDate " +
                "WHERE id = :milestoneId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("milestoneId", milestone.getId())
                .addValue("name", milestone.getName())
                .addValue("description", milestone.getDescription())
                .addValue("dueDate", milestone.getDueDate());
        return template.update(sql, params) > 0;
    }

    public boolean delete(Long milestoneId) {
        String sql = "UPDATE milestone SET is_deleted = TRUE WHERE id = :milestoneId";
        return template.update(sql, Map.of("milestoneId", milestoneId)) > 0;
    }

    public boolean updateStatus(Long milestoneId, boolean isOpen) {
        String sql = "UPDATE milestone SET is_open = :isOpen WHERE id = :milestoneId";
        return template.update(sql, Map.of("milestoneId", milestoneId, "isOpen", isOpen)) > 0; // TODO: 이미 닫힌 마일스톤에 대해 닫기 요청 가능한지 확인하기
    }

    private final RowMapper<MilestoneDetailsVO> milestoneDetailsVORowMapper() {
        return ((rs, rowNum) -> MilestoneDetailsVO.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .dueDate(rs.getDate("due_date").toLocalDate())
                .build());
    }

    private final RowMapper<MilestoneDetailsVO> milestoneSipmleVORowMapper() {
        return ((rs, rowNum) -> MilestoneDetailsVO.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build());
    }

    private RowMapper<MilestoneVO> milestoneVORowMapper() {
        return (rs, rowNum) -> MilestoneVO.builder()
                .name(rs.getString("name"))
                .openIssueCount(rs.getInt("open_issue_count"))
                .closedIssueCount(rs.getInt("closed_issue_count"))
                .build();
    }
}
