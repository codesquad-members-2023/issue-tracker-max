package codesquard.app.milestone.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquard.app.milestone.entity.Milestone;
import codesquard.app.milestone.entity.MilestoneStatus;

@Repository
public class JdbcMilestoneRepository implements MilestoneRepository {

	private final NamedParameterJdbcTemplate template;

	public JdbcMilestoneRepository(final NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	public Optional<Long> save(final Milestone milestone) {
		String sql = "INSERT INTO `milestone` (`name`, `deadline`, `description`) " +
			"VALUES (:name, :deadline, :description)";

		SqlParameterSource param = new BeanPropertySqlParameterSource(milestone);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		template.update(sql, param, keyHolder);

		return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
	}

	@Override
	public Long countIssuesBy(final MilestoneStatus status) {
		String sql = "SELECT COUNT(*) FROM `issue` " +
			"WHERE `status` = :status";

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", status.getName());

		return Optional.ofNullable(template.queryForObject(sql, paramMap, Long.class)).orElse(0L);
	}

	@Override
	public Long countMilestonesBy(final MilestoneStatus status) {
		String sql = "SELECT COUNT(*) FROM `milestone` " +
			"WHERE `status` = :status";

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", status.getName());

		return Optional.ofNullable(template.queryForObject(sql, paramMap, Long.class)).orElse(0L);
	}

	@Override
	public Long countLabels() {
		String sql = "SELECT COUNT(*) FROM `label` ";

		Map<String, Object> paramMap = new HashMap<>();

		return Optional.ofNullable(template.queryForObject(sql, paramMap, Long.class)).orElse(0L);
	}

	@Override
	public List<Milestone> findAllBy(final MilestoneStatus status) {
		String sql = "SELECT `id`, `status`, `name`, `description`, `created_at`, `modified_at`, `deadline` " +
			"FROM `milestone` " +
			"WHERE `status` = :status";

		SqlParameterSource param = new MapSqlParameterSource("status", status.getName());

		return Optional.of(template.query(sql, param, milestoneRowMapper())).orElse(Collections.emptyList());
	}

	private RowMapper<Milestone> milestoneRowMapper() {
		return (rs, rowNum) -> {
			Long id = rs.getLong("id");
			Optional<LocalDateTime> createdAt = Optional.ofNullable(rs.getTimestamp("created_at"))
				.map(Timestamp::toLocalDateTime);
			Optional<LocalDateTime> modifiedAt = Optional.ofNullable(rs.getTimestamp("modified_at"))
				.map(Timestamp::toLocalDateTime);
			String name = rs.getString("name");
			String description = rs.getString("description");
			Optional<LocalDate> deadline = Optional.ofNullable(rs.getDate("deadline")).map(Date::toLocalDate);

			return new Milestone(id, createdAt.orElse(null), modifiedAt.orElse(null), name, description,
				deadline.orElse(null));
		};
	}

	@Override
	public void updateBy(final Long milestoneId, final Milestone milestone) {
		String sql = "UPDATE `milestone` " +
			"SET `name` = :name, `deadline` = :deadline, `description` = :description " +
			"WHERE `id` = :id";

		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("name", milestone.getName())
			.addValue("deadline", milestone.getDeadline())
			.addValue("description", milestone.getDescription())
			.addValue("id", milestoneId);

		template.update(sql, param);
	}

	@Override
	public void updateBy(final Long milestoneId, final MilestoneStatus status) {
		String sql = "UPDATE `milestone` " +
			"SET `status` = :status " +
			"WHERE `id` = :id";

		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("status", status.getName())
			.addValue("id", milestoneId);

		template.update(sql, param);
	}

	@Override
	public void deleteBy(final Long milestoneId) {
		String sql = "DELETE FROM `milestone` "
			+ "WHERE `id` = :id";

		template.update(sql, Map.of("id", milestoneId));
	}
}
