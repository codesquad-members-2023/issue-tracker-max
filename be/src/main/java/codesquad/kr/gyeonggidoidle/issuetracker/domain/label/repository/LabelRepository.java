package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.Label;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

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

    public void addIssueLabels(Long issueId, List<Long> labelIds) {
        String sql = "INSERT INTO issue_label (issue_id, label_id) VALUES (:issue_id, :label_id)";
        SqlParameterSource[] batchParams = generateParameters(issueId, labelIds);
        template.batchUpdate(sql, batchParams);
    }

    public void updateIssueLabels(Long issueId, List<Long> labelIds) {
        String sql = "DELETE FROM issue_label WHERE issue_id = :issueId";
        template.update(sql, Map.of("issueId", issueId));
        addIssueLabels(issueId, labelIds);
    }

    private SqlParameterSource[] generateParameters(Long issueId, List<Long> labelIds) {
        return labelIds.stream()
                .map(labelId -> generateParameter(issueId, labelId))
                .toArray(SqlParameterSource[]::new);
    }

    private SqlParameterSource generateParameter(Long issueId, Long labelId) {
        return new MapSqlParameterSource()
                .addValue("issue_id", issueId)
                .addValue("label_id", labelId);
    }

    public List<LabelDetailsVO> findAllFilters() {
        String sql = "SELECT id, name, background_color, text_color " +
                "FROM label " +
                "ORDER BY name";
        return template.query(sql, new MapSqlParameterSource(), labelSimpleVORowMapper());
    }

    public boolean save(Label label) {
        String sql = "INSERT INTO label(name, description, background_color, text_color) "
                + "VALUES (:name, :description, :backgroundColor, :textColor)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", label.getName())
                .addValue("description", label.getDescription())
                .addValue("backgroundColor", label.getBackgroundColor())
                .addValue("textColor", label.getTextColor());
        int result = template.update(sql, params);
        return result > 0;
    }

    public LabelDetailsVO findById(Long labelId) {
        String sql = "SELECT id, name, description, background_color, text_color " +
                "FROM label " +
                "WHERE id = :labelId ";
        try {
            return template.queryForObject(sql, Map.of("labelId", labelId), labelDetailsVORowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public boolean update(Label label) {
        String sql = "UPDATE label SET name = :name, description = :description, background_color = :backgroundColor, text_color = :textColor " +
                "WHERE id = :labelId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("labelId", label.getId())
                .addValue("name", label.getName())
                .addValue("description", label.getDescription())
                .addValue("backgroundColor", label.getBackgroundColor())
                .addValue("textColor", label.getTextColor());
        return template.update(sql, params) > 0;
    }

    public boolean delete(Long labelId) {
        String sql = "UPDATE label SET is_deleted = TRUE WHERE id = :labelId";
        return template.update(sql, Map.of("labelId", labelId)) > 0;
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
