package codesquad.issueTracker.milestone.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquad.issueTracker.milestone.domain.Milestone;

@Repository
public class MilestoneRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MilestoneRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long insert(Milestone milestone) {

		String sql = "INSERT INTO milestones(name, description,done_date) VALUES (:name,:description,:doneDate)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("name", milestone.getName())
			.addValue("description", milestone.getDescription())
			.addValue("doneDate", milestone.getDoneDate());
		jdbcTemplate.update(sql, parameters, keyHolder);
		return keyHolder.getKey().longValue();

	}
}
