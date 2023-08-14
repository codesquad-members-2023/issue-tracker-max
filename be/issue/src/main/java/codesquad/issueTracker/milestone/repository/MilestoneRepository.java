package codesquad.issueTracker.milestone.repository;

import codesquad.issueTracker.issue.vo.IssueMilestoneVo;
import java.time.LocalDate;
import java.util.List;

import java.util.Map;
import java.util.Optional;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquad.issueTracker.milestone.domain.Milestone;
import codesquad.issueTracker.milestone.vo.MilestoneVo;

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

	public Long update(Long id, Milestone milestone) {
		String sql = "UPDATE milestones SET name = :name , description = :description ,done_date = :doneDate WHERE id = :id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("name", milestone.getName())
			.addValue("description", milestone.getDescription())
			.addValue("doneDate", milestone.getDoneDate());
		jdbcTemplate.update(sql, parameters);
		return id;

	}

	public Long delete(Long id) {
		String sql = "UPDATE milestones SET is_deleted = 1 where id = :id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		jdbcTemplate.update(sql, parameters);
		return id;
	}

	public Boolean isExist(Long id) {
		String sql = "SELECT COUNT(*) FROM milestones WHERE id = :id AND is_deleted = 0";
		SqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue("id", id);
		return jdbcTemplate.queryForObject(sql, parameterSource, Boolean.class);
	}

	public Long updateStatus(Long id, Boolean status) {
		String sql = "UPDATE milestones SET is_closed = :status  where id = :id AND is_deleted = 0";
		SqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("status", status);
		jdbcTemplate.update(sql, parameterSource);
		return id;
	}

	public List<MilestoneVo> findAll(Boolean status) {
		String sql = "SELECT "
			+ " SUM(CASE WHEN i.is_closed = false THEN 1 ELSE 0 END) AS issueOpenCount, "
			+ " SUM(CASE WHEN i.is_closed = true THEN 1 ELSE 0 END) AS issueClosedCount, "
			+ " m.id AS id, m.name AS name, m.description AS description, m.done_date AS donDate "
			+ " FROM milestones m "
			+ " LEFT JOIN issues i ON m.id = i.milestone_id AND i.is_deleted = 0 "
			+ "WHERE m.is_deleted = 0  AND m.is_closed = :status "
			+ "GROUP BY m.id, m.name, m.description, m.done_date";
		SqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue("status", status);
		return jdbcTemplate.query(sql, parameterSource, milestoneRowMapper);
	}

	public int getAnotherCount(Boolean status) {
		String sql = "SELECT COUNT(*) FROM milestones WHERE is_closed = :status AND is_deleted = 0";
		SqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue("status", status);
		return jdbcTemplate.queryForObject(sql, parameterSource, Integer.class);
	}

	public int getLabelCount() {
		String sql = "SELECT COUNT(*) FROM labels WHERE is_deleted = 0";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
	}

	private final RowMapper<MilestoneVo> milestoneRowMapper = (rs, rowNum) -> MilestoneVo.builder()
		.id(rs.getLong("id"))
		.name(rs.getString("name"))
		.description(rs.getString("description"))
		.doneDate(rs.getObject("donDate", LocalDate.class))
		.issueOpenCount(rs.getInt("issueOpenCount"))
		.issueClosedCount(rs.getInt("issueClosedCount"))
		.build();

	public Optional<IssueMilestoneVo> findByIssueId(Long issueId) {
		String sql = "select m.id, m.name "
				+ "from milestones m "
				+ "    join issues i on m.id = i.milestone_id "
				+ "where i.id = :issueId "
				+ "and i.is_deleted = false "
				+ "and m.is_deleted = false";

		return Optional.ofNullable(
				DataAccessUtils.singleResult(
						jdbcTemplate.query(sql, Map.of("issueId", issueId), issueMilestoneVoRowMapper)));
	}

	private final RowMapper<IssueMilestoneVo> issueMilestoneVoRowMapper = ((rs, rowNum) -> IssueMilestoneVo.builder()
			.id(rs.getLong("id"))
			.name(rs.getString("name"))
			.build());
}
