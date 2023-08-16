package com.issuetrackermax.domain.milestone;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.milestone.entity.Milestone;

@Repository
public class MilestoneRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MilestoneRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Milestone findbyId(Long id) {
		String sql = "SELECT id, title, is_open, duedate, description FROM milestone WHERE id = :id ";
		return DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("id", id), MILESTONE_ROW_MAPPER));
	}

	public Long save(Milestone milestone) {
		String sql = "INSERT INTO milestone(title, description,is_open, duedate) VALUES (:title,:description,:isOpen, :dueDate)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("title", milestone.getTitle(), Types.VARCHAR)
			.addValue("description", milestone.getDescription(), Types.VARCHAR)
			.addValue("isOpen", milestone.getIsOpen(), Types.TINYINT)
			.addValue("dueDate", milestone.getDuedate(), Types.DATE);

		jdbcTemplate.update(sql, parameters, keyHolder, new String[] {"id"});
		return keyHolder.getKey().longValue();

	}

	public Long update(Long id, Milestone milestone) {
		String sql = "UPDATE milestone SET title = :title, description = :description, duedate = :dueDate WHERE id = :milestoneId";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("milestoneId", id)
			.addValue("title", milestone.getTitle(), Types.VARCHAR)
			.addValue("description", milestone.getDescription(), Types.VARCHAR)
			.addValue("dueDate", milestone.getDuedate(), Types.DATE);
		jdbcTemplate.update(sql, parameters, keyHolder, new String[] {"id"});
		return keyHolder.getKey().longValue();

	}

	public Long getMilestoneCount() {
		String sql = "SELECT COUNT(*) FROM milestone";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
	}

	public List<Milestone> getOpenMilestone() {
		String sql = "SELECT id, title, is_open, description,duedate FROM milestone WHERE is_open = :isOpen";
		return jdbcTemplate.query(sql, Map.of("isOpen", 1), MILESTONE_ROW_MAPPER);
	}

	public List<Milestone> getClosedMilestone() {
		String sql = "SELECT id, title, is_open, description,duedate FROM milestone WHERE is_open = :isOpen";
		return jdbcTemplate.query(sql, Map.of("isOpen", 0), MILESTONE_ROW_MAPPER);
	}

	public Boolean existById(Long id) {
		String sql = "SELECT EXISTS (SELECT 1 FROM milestone WHERE id = :id)";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("id", id), Boolean.class);
	}

	public Boolean existByTitle(String title) {
		String sql = "SELECT EXISTS(SELECT 1 FROM milestone WHERE title =:title)";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("title", title), Boolean.class);
	}

	public int deleteById(Long id) {
		String sql = "DELETE FROM milestone WHERE id = :id";
		return jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}

	private static final RowMapper<Milestone> MILESTONE_ROW_MAPPER = (rs, rowNum) ->
		Milestone.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.isOpen(rs.getBoolean("is_open"))
			.description(rs.getString("description"))
			.duedate(rs.getTimestamp("duedate").toLocalDateTime())
			.build();

	public int updateStatus(Long id) {
		String sql = "UPDATE milestone SET is_open = NOT is_open WHERE id = :id";
		return jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}
}
