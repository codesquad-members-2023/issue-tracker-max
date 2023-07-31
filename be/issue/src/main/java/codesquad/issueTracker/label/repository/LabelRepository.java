package codesquad.issueTracker.label.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LabelRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public LabelRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
