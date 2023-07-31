package codesquard.app.milestone.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import codesquard.app.milestone.entity.Milestone;

@Repository
public class JdbcMilestoneRepository implements MilestoneRepository {

	private final NamedParameterJdbcTemplate template;
	private final SimpleJdbcInsert simpleJdbcInsert;

	public JdbcMilestoneRepository(NamedParameterJdbcTemplate template, DataSource dataSource) {
		this.template = template;
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("milestone")
			.usingColumns("name", "description", "deadline")
			.usingGeneratedKeyColumns("id");
	}

	@Override
	public Long save(Milestone milestone) {
		return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(milestone)).longValue();
	}

	@Override
	public List<Milestone> findAll() {
		return null;
	}

	@Override
	public Milestone findById(Long id) {
		return null;
	}

	@Override
	public Long modify(Milestone milestone) {
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		return null;
	}
}
