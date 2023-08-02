package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
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
public class LabelRepository {

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public LabelRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public Map<Long, List<LabelVO>> findAllByIssueIds(List<Long> issueIds) {
        return issueIds.stream()
                .collect(Collectors.toUnmodifiableMap(
                        issueId -> issueId,
                        this::findAllByIssueId
                ));
    }

    public List<LabelVO> findAllByIssueId(Long issueId) {
        String sql = "SELECT l.name, l.background_color, l.text_color " +
                "FROM issue_label AS i " +
                "LEFT JOIN label AS l " +
                "ON l.id = i.label_id " +
                "WHERE i.issue_id = :issueId " +
                "AND l.is_deleted = FALSE";

        return template.query(sql, Map.of("issueId", issueId), labelRowMapper());
    }

    public List<LabelDetailsVO> findAll() {
        String sql = "SELECT id, name, description, background_color, text_color " +
                "FROM label " +
                "WHERE is_deleted = FALSE " +
                "ORDER BY name";
        return template.query(sql, new MapSqlParameterSource(), labelDetailsVORowMapper());
    }

    private final RowMapper<LabelVO> labelRowMapper() {
        return ((rs, rowNum) -> LabelVO.builder()
                .name(rs.getString("name"))
                .backgroundColor(rs.getString("background_color"))
                .textColor(rs.getString("text_color"))
                .build());
    }

    private final RowMapper<LabelDetailsVO> labelDetailsVORowMapper() {
        return ((rs, rowNum) -> LabelDetailsVO.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .backgroundColor(rs.getString("background_color"))
                .textColor(rs.getString("text_color"))
                .build());
    }
}
