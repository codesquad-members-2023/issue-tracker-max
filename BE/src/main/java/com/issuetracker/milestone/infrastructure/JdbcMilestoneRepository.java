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
import com.issuetracker.milestone.domain.MilestoneRepository;

@Repository
public class JdbcMilestoneRepository implements MilestoneRepository {

	private static final String EXIST_BY_ID_SQL = "SELECT EXISTS(SELECT 1 FROM milestone WHERE id = :id)";
	private static final String FIND_ALL_FOR_FILTER = "SELECT id, title FROM milestone ORDER BY is_open DESC";
	private static final String SAVE_SQL = "INSERT INTO milestone(title, description, deadline) VALUE(:title, :description, :deadline)";
	private static final String UPDATE_SQL = "UPDATE milestone SET title = :title, description = :description, deadline = :deadline WHERE id = :id";
	private static final String DELETE_SQL = "UPDATE milestone SET is_deleted = true WHERE id = :id";
	private static final String FIND_ALL_SQL
		= "SELECT "
		+ "    milestone.id, "
		+ "    milestone.title, "
		+ "    milestone.description, "
		+ "    milestone.deadline, "
		+ "    ROUND( COUNT( CASE WHEN issue.is_open = false THEN 1 ELSE null END ) / COUNT(issue.id) * 100, 2 ) AS progress "
		+ "FROM milestone "
		+ "LEFT JOIN issue "
		+ "ON milestone.id = issue.milestone_id "
		+ "WHERE milestone.is_deleted = false "
		+ "GROUP BY milestone.id";

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
	public int delete(Milestone milestone) {
		Map<String, Long> param = Map.of("id", milestone.getId());

		return jdbcTemplate.update(DELETE_SQL, param);
	}

	@Override
	public List<Milestone> findAll() {
		return jdbcTemplate.query(FIND_ALL_SQL, MILESTONE_ROW_MAPPER);
	}

	private static LocalDate convertFrom(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(dateString, formatter);
	}

	private static final RowMapper<Milestone> MILESTONE_ROW_MAPPER = (rs, rowNum) ->
		Milestone.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.description(rs.getString("description"))
			.deadline(convertFrom(rs.getString("deadline")))
			.progress(rs.getDouble("progress"))
			.build();
}
