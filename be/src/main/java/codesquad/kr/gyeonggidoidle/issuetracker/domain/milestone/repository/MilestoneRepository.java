package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public List<MilestoneDetailsVO> findAllFilters() {
        String sql = "SELECT id, name " +
                "FROM milestone " +
                "ORDER BY name";
        return template.query(sql, new MapSqlParameterSource(), milestoneSipmleVORowMapper());
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
}
