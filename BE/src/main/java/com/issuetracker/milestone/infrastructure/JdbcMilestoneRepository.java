package com.issuetracker.milestone.infrastructure;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.milestone.domain.MilestoneCountMetadata;
import com.issuetracker.milestone.domain.MilestoneRepository;

@Repository
public class JdbcMilestoneRepository implements MilestoneRepository {

	private static final String EXIST_BY_ID_SQL = "SELECT EXISTS(SELECT 1 FROM milestone WHERE is_deleted = false AND id = :id)";
	private static final String FIND_ALL_FOR_FILTER = "SELECT id, title FROM milestone WHERE milestone.is_deleted = false ORDER BY is_open DESC";
	private static final String SAVE_SQL = "INSERT INTO milestone(title, description, deadline, is_open) VALUE(:title, :description, :deadline, true)";
	private static final String UPDATE_SQL = "UPDATE milestone SET title = :title, description = :description, deadline = :deadline WHERE id = :id";
	private static final String UPDATE_OPEN_STATUS_SQL = "UPDATE milestone SET is_open = :isOpen WHERE id = :id";
	private static final String DELETE_SQL = "UPDATE milestone SET is_deleted = true WHERE id = :id";
	private static final String FIND_ALL_SQL
		= "SELECT "
		+ "    milestone.id, "
		+ "    milestone.title, "
		+ "    milestone.description, "
		+ "    milestone.deadline, "
		+ "    milestone.is_open, "
		+ "    COALESCE( ROUND( COUNT( CASE WHEN issue.is_open = false THEN 1 ELSE null END ) / COUNT(issue.id) * 100, 0 ), 0 ) AS progress "
		+ "FROM milestone "
		+ "LEFT JOIN issue "
		+ "ON milestone.id = issue.milestone_id "
		+ "WHERE milestone.is_deleted = false "
		+ "AND milestone.is_open = :isOpen "
		+ "GROUP BY milestone.id "
		+ "ORDER BY milestone.id DESC ";
	private static final String FIND_ALL_ASSIGNED_TO_ISSUE
		= "SELECT "
		+ "    milestone.id, "
		+ "    milestone.title, "
		+ "    milestone.description, "
		+ "    milestone.deadline, "
		+ "    COALESCE( ROUND( COUNT( CASE WHEN issue.is_open = false THEN 1 ELSE null END ) / COUNT(issue.id) * 100, 0 ), 0 ) AS progress "
		+ "FROM milestone "
		+ "RIGHT JOIN issue "
		+ "ON milestone.id = issue.milestone_id "
		+ "WHERE milestone.is_deleted = false "
		+ "AND milestone_id = (SELECT sub_issue.milestone_id FROM issue sub_issue WHERE sub_issue.id = :issueId) "
		+ "GROUP BY milestone.id "
		+ "ORDER BY milestone.title ";
	private static final String FIND_ALL_UNASSIGNED_TO_ISSUE
		= "SELECT "
		+ "    milestone.id, "
		+ "    milestone.title, "
		+ "    milestone.description, "
		+ "    milestone.deadline, "
		+ "    COALESCE( ROUND( COUNT( CASE WHEN issue.is_open = false THEN 1 ELSE null END ) / COUNT(issue.id) * 100, 0 ), 0 ) AS progress "
		+ "FROM milestone "
		+ "LEFT JOIN issue "
		+ "ON milestone.id = issue.milestone_id "
		+ "WHERE milestone.is_deleted = false "
		+ "AND milestone.id NOT IN (SELECT sub_issue.milestone_id FROM issue sub_issue WHERE sub_issue.id = :issueId) "
		+ "GROUP BY milestone.id "
		+ "ORDER BY milestone.title";
	private static final String CALCULATE_COUNT_METADATA
		= "SELECT "
		+ "    (SELECT COUNT(id) FROM label WHERE is_deleted = false) AS total_label_count, "
		+ "    (SELECT COUNT(id) FROM milestone WHERE is_deleted = false) AS total_milestone_count, "
		+ "    (SELECT COUNT(id) FROM milestone WHERE is_deleted = false AND is_open = true) open_milestone_count, "
		+ "    (SELECT COUNT(id) FROM milestone WHERE is_deleted = false AND is_open = false) close_milestone_count";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcMilestoneRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public boolean existById(Long id) {
		return jdbcTemplate.queryForObject(EXIST_BY_ID_SQL, Map.of("id", id), Boolean.class);
	}

	@Override
	public List<Milestone> findAllForFilter() {

		RowMapper<Milestone> milestoneRowMapper =
			(rs, rowNum) ->
				Milestone.builder()
					.id(rs.getLong("id"))
					.title(rs.getString("title"))
					.build();

		return jdbcTemplate.query(FIND_ALL_FOR_FILTER, milestoneRowMapper);
	}

	@Override
	public Long save(Milestone milestone) {
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("title", milestone.getTitle())
			.addValue("description", milestone.getDescription())
			.addValue("deadline", milestone.getDeadline());
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(SAVE_SQL, param, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public int update(Milestone milestone) {
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("id", milestone.getId())
			.addValue("title", milestone.getTitle())
			.addValue("description", milestone.getDescription())
			.addValue("deadline", milestone.getDeadline());

		return jdbcTemplate.update(UPDATE_SQL, param);
	}

	@Override
	public int updateOpenStatus(Milestone milestone) {
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("id", milestone.getId())
			.addValue("isOpen", milestone.getIsOpen());

		return jdbcTemplate.update(UPDATE_OPEN_STATUS_SQL, param);
	}

	@Override
	public int delete(Milestone milestone) {
		Map<String, Long> param = Map.of("id", milestone.getId());

		return jdbcTemplate.update(DELETE_SQL, param);
	}

	@Override
	public List<Milestone> findAll(Milestone milestone) {
		return jdbcTemplate.query(FIND_ALL_SQL, Map.of("isOpen", milestone.getIsOpen()), MILESTONE_ROW_MAPPER);
	}

	private static LocalDate convertFrom(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(dateString, formatter);
	}

	@Override
	public List<Milestone> findAllAssignedToIssue(long issueId) {
		return jdbcTemplate.query(FIND_ALL_ASSIGNED_TO_ISSUE, Map.of("issueId", issueId), MILESTONE_ROW_MAPPER);
	}

	@Override
	public List<Milestone> findAllUnassignedToIssue(long issueId) {
		return jdbcTemplate.query(FIND_ALL_UNASSIGNED_TO_ISSUE, Map.of("issueId", issueId), MILESTONE_ROW_MAPPER);
	}

	@Override
	public MilestoneCountMetadata calculateMetadata() {
		return jdbcTemplate.queryForObject(CALCULATE_COUNT_METADATA, Map.of(), MILESTONE_METADATA_MAPPER);
	}

	private static final RowMapper<Milestone> MILESTONE_ROW_MAPPER = (rs, rowNum) ->
		Milestone.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.description(rs.getString("description"))
			.deadline(convertFrom(rs.getString("deadline")))
			.isOpen(rs.getBoolean("is_open"))
			.progress(rs.getInt("progress"))
			.build();

	private static final RowMapper<MilestoneCountMetadata> MILESTONE_METADATA_MAPPER = (rs, rowNum) ->
		MilestoneCountMetadata.builder()
			.totalLabelCount(rs.getInt("total_label_count"))
			.totalMilestoneCount(rs.getInt("total_milestone_count"))
			.openMilestoneCount(rs.getInt("open_milestone_count"))
			.closeMilestoneCount(rs.getInt("close_milestone_count"))
			.build();
}
