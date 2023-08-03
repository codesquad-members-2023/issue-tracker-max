package com.issuetracker.issue.infrastrucure;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.Milestone;

@Repository
public class MilestoneRepository {

	private static final String EXIST_BY_ID_SQL = "SELECT EXISTS(SELECT 1 FROM milestone WHERE id = :id)";
	private static final String FIND_ALL_FOR_FILTER = "SELECT id, title FROM milestone ORDER BY is_open DESC";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MilestoneRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public boolean existById(Long id) {
		return jdbcTemplate.queryForObject(EXIST_BY_ID_SQL, Map.of("id", id), Boolean.class);
	}

	public List<Milestone> findAllForFilter() {

		RowMapper<Milestone> milestoneRowMapper =
			(rs, rowNum) ->
				Milestone.builder()
					.id(rs.getLong("id"))
					.title(rs.getString("title"))
					.build();

		return jdbcTemplate.query(FIND_ALL_FOR_FILTER, milestoneRowMapper);
	}
}
