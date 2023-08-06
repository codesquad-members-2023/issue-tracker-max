package codesquad.issueTracker.milestone.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MilestoneRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MilestoneRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
