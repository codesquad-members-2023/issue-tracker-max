package com.issuetracker.issue.infrastrucure;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.assignedlabel.AssignedLabel;
import com.issuetracker.issue.domain.assignedlabel.AssignedLabelRepository;
import com.issuetracker.label.domain.Label;

@Repository
public class JdbcAssignedLabelRepository implements AssignedLabelRepository {

	private static final String SAVE_SQL = "INSERT INTO assigned_label(issue_id, label_id) VALUES(:issueId, :labelId)";
	private static final String DELETE_SQL = "DELETE FROM assigned_label WHERE id = :id";
	private static final String FIND_ALL_SEARCH_SQL = "SELECT DISTINCT label.id, label.title, label.color, label.description FROM label INNER JOIN assigned_label ON label.id = assigned_label.label_id WHERE label.is_deleted = 0 ORDER BY label.id";
	private static final String FIND_ALL_ASSIGNED_TO_ISSUE
		= "SELECT label.id, label.title, label.color, label.description "
		+ "FROM assigned_label "
		+ "LEFT JOIN label "
		+ "ON assigned_label.label_id = label.id "
		+ "WHERE assigned_label.issue_id = :issueId "
		+ "AND label.is_deleted = false "
		+ "ORDER BY label.title ";
	private static final String FIND_ALL_UNASSIGNED_TO_ISSUE
		= "SELECT label.id, label.title, label.color, label.description "
		+ "FROM label "
		+ "WHERE label.is_deleted = false "
		+ "AND label.id NOT IN( "
		+ "    SELECT assigned_label.label_id "
		+ "    FROM assigned_label "
		+ "    WHERE assigned_label.issue_id = :issueId) "
		+ "ORDER BY label.title ";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcAssignedLabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public long save(AssignedLabel assignedLabel) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(assignedLabel);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SAVE_SQL, param, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public int[] saveAll(List<AssignedLabel> assignedLabels) {
		BeanPropertySqlParameterSource[] params = assignedLabels.stream()
			.map(BeanPropertySqlParameterSource::new)
			.toArray(BeanPropertySqlParameterSource[]::new);
		return jdbcTemplate.batchUpdate(SAVE_SQL, params);
	}

	@Override
	public List<Label> findAll() {
		return jdbcTemplate.query(FIND_ALL_SEARCH_SQL, LABEL_ROW_MAPPER);
	}

	@Override
	public List<Label> findAllAssignedToIssue(long issueId) {
		return jdbcTemplate.query(FIND_ALL_ASSIGNED_TO_ISSUE, Map.of("issueId", issueId), LABEL_ROW_MAPPER);
	}

	@Override
	public List<Label> findAllUnassignedToIssue(long issueId) {
		return jdbcTemplate.query(FIND_ALL_UNASSIGNED_TO_ISSUE, Map.of("issueId", issueId), LABEL_ROW_MAPPER);
	}

	@Override
	public int delete(Long id) {
		return jdbcTemplate.update(DELETE_SQL, Map.of("id", id));
	}

	private static final RowMapper<Label> LABEL_ROW_MAPPER = (rs, rowNum) ->
		Label.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.color(rs.getString("color"))
			.description(rs.getString("description"))
			.build();
}
