package codesquard.app.milestone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquard.app.milestone.entity.Milestone;

@Repository
public class JdbcMilestoneRepository implements MilestoneRepository {

	private final NamedParameterJdbcTemplate template;

	public JdbcMilestoneRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	public Optional<Long> save(Milestone milestone) {
		String sql = "INSERT INTO `milestone` (`name`, `deadline`, `description`) "
			+ "VALUES (:name, :deadline, :description)";

		SqlParameterSource param = new BeanPropertySqlParameterSource(milestone);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		template.update(sql, param, keyHolder);

		return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
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
