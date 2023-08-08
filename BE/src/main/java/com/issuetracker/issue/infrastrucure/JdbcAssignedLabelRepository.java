package com.issuetracker.issue.infrastrucure;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.AssignedLabel;
import com.issuetracker.issue.domain.AssignedLabelRepository;
import com.issuetracker.label.domain.Label;

@Repository
public class JdbcAssignedLabelRepository implements AssignedLabelRepository {

	private static final String SAVE_ALL_SQL = "INSERT INTO assigned_label(issue_id, label_id) VALUES(:issueId, :labelId)";
	private static final String FIND_ALL_SEARCH_SQL = "SELECT DISTINCT label.id, label.title, label.color FROM label INNER JOIN assigned_label ON label.id = assigned_label.label_id ORDER BY label.id";
	private static final String FIND_ALL_ASSIGNED_TO_ISSUE
		= "SELECT label.id, label.title, label.color "
		+ "FROM assigned_label "
		+ "LEFT JOIN label "
		+ "ON assigned_label.label_id = label.id "
		+ "WHERE assigned_label.issue_id = :issueId";
	private static final String FIND_ALL_UNASSIGNED_TO_ISSUE
		= "SELECT label.id, label.title, label.color "
		+ "FROM label "
		+ "WHERE label.id NOT IN( "
		+ "    SELECT assigned_label.label_id "
		+ "    FROM assigned_label "
		+ "    WHERE assigned_label.issue_id = :issueId)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcAssignedLabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public int[] saveAll(List<AssignedLabel> assignedLabels) {
		BeanPropertySqlParameterSource[] params = assignedLabels.stream()
			.map(BeanPropertySqlParameterSource::new)
			.toArray(BeanPropertySqlParameterSource[]::new);
		return jdbcTemplate.batchUpdate(SAVE_ALL_SQL, params);
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

	private static final RowMapper<Label> LABEL_ROW_MAPPER = (rs, rowNum) ->
		Label.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.color(rs.getString("color"))
			.build();
}
