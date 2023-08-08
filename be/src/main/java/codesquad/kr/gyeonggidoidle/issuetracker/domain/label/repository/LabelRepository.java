package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
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
public class LabelRepository {

    private final NamedParameterJdbcTemplate template;

    public Map<Long, List<LabelVO>> findAllByIssueIds(List<Long> issueIds) {
        return issueIds.stream()
                .collect(Collectors.toUnmodifiableMap(
                        issueId -> issueId,
                        this::findAllByIssueId
                ));
    }

    public List<LabelVO> findAllByIssueId(Long issueId) {
        String sql = "SELECT label.name, label.background_color, label.text_color " +
                "FROM issue_label " +
                "LEFT JOIN label " +
                "ON label.id = issue_label.label_id " +
                "WHERE issue_label.issue_id = :issueId " +
                "AND label.is_deleted = FALSE";

        return template.query(sql, Map.of("issueId", issueId), labelRowMapper());
    }

    public List<LabelDetailsVO> findAll() {
        String sql = "SELECT id, name, description, background_color, text_color " +
                "FROM label " +
                "WHERE is_deleted = FALSE " +
                "ORDER BY name";
        return template.query(sql, new MapSqlParameterSource(), labelDetailsVORowMapper());
    }

    public List<LabelDetailsVO> findAllFilters() {
        String sql = "SELECT id, name, background_color, text_color " +
                "FROM label " +
                "ORDER BY name";
        return template.query(sql, new MapSqlParameterSource(), labelSimpleVORowMapper());
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

    private final RowMapper<LabelDetailsVO> labelSimpleVORowMapper() {
        return ((rs, rowNum) -> LabelDetailsVO.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .backgroundColor(rs.getString("background_color"))
                .textColor(rs.getString("text_color"))
                .build());
    }
}
