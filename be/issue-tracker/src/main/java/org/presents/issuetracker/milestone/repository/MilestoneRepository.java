package org.presents.issuetracker.milestone.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.presents.issuetracker.milestone.dto.response.MilestoneResponse;
import org.presents.issuetracker.milestone.entity.Milestone;
import org.presents.issuetracker.milestone.entity.vo.MilestoneInfo;
import org.presents.issuetracker.milestone.entity.vo.MilestonePreview;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MilestoneRepository {
	private static final String OPEN_FLAG = "open";
	private static final String CLOSED_FLAG = "closed";
	private static final String DELETED_FLAG = "deleted";
	private static final int LABEL_OPEN_FLAG = 0;

	private final NamedParameterJdbcTemplate jdbcTemplate;

	private final RowMapper<MilestonePreview> milestonePreviewRowMapper =
		(rs, rowNum) -> MilestonePreview.builder()
			.id(rs.getLong("milestone_id"))
			.name(rs.getString("name"))
			.progress(rs.getInt("progress"))
			.build();

	public MilestonePreview findByIssueId(Long issueId) {
		final String sql = "SELECT m.milestone_id, m.name, "
			+ "(SELECT FLOOR(COUNT(CASE WHEN status = 'closed' THEN 1 END) / COUNT(*) * 100) "
			+ "FROM issue "
			+ "WHERE milestone_id = i.milestone_id AND status IN ('open', 'closed')) progress "
			+ "FROM milestone m JOIN issue i ON m.milestone_id = i.milestone_id "
			+ "WHERE i.issue_id = :issueId";

		MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

		return jdbcTemplate.query(sql, params, milestonePreviewRowMapper)
			.stream()
			.findFirst()
			.orElse(null);
	}

	public Milestone findById(Long milestoneId) {
		final String sql = "SELECT milestone_id, name, deadline, description, status FROM milestone WHERE milestone_id = :id";

		MapSqlParameterSource params = new MapSqlParameterSource("id", milestoneId);

		RowMapper<Milestone> mapper = (rs, rowNum) -> Milestone.of(
			rs.getLong("milestone_id"),
			rs.getString("name"),
			rs.getTimestamp("deadLine").toLocalDateTime(),
			rs.getString("description"),
			rs.getString("status")
		);

		return jdbcTemplate.queryForObject(sql, params, mapper);
	}

	public List<MilestonePreview> findPreviews() {
		final String sql = "SELECT milestone_id, name, "
			+ "(SELECT IFNULL(FLOOR(COUNT(CASE WHEN status = 'closed' THEN 1 END) / COUNT(*) * 100), 0) "
			+ "FROM issue i "
			+ "WHERE i.milestone_id = m.milestone_id AND status IN ('open', 'closed')) progress "
			+ "FROM milestone m "
			+ "WHERE status != 'deleted'";

		return jdbcTemplate.query(sql, milestonePreviewRowMapper);
	}

	public Long save(Milestone milestone) {
		final String sql = "INSERT INTO milestone(name, deadline, description) VALUES (:name, :deadline, :description)";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", milestone.getName());
		params.addValue("deadline", milestone.getDeadline());
		params.addValue("description", milestone.getDescription());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, params, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public void update(Milestone milestone) {
		final String sql = "UPDATE milestone " +
			"SET name = :name, " +
			"deadline = :deadline, " +
			"description = :description " +
			"WHERE milestone_id = :id";

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("name", milestone.getName())
			.addValue("deadline", milestone.getDeadline())
			.addValue("description", milestone.getDescription())
			.addValue("id", milestone.getId());

		jdbcTemplate.update(sql, params);
	}

	public void updateStatus(Long id, String status) {
		final String sql = "UPDATE milestone SET status = :status WHERE milestone_id = :id";

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("status", status)
			.addValue("id", id);

		jdbcTemplate.update(sql, params);
	}

	public void deleteById(Long id) {
		final String sql = "UPDATE milestone SET status = :deletedFlag WHERE milestone_id = :id";

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("deletedFlag", DELETED_FLAG)
			.addValue("id", id);

		jdbcTemplate.update(sql, params);
	}

	public MilestoneInfo findDetailsByStatus(String status) {
		List<MilestoneResponse> milestoneResponses = fetchMilestoneResponsesWithProgress(status);

		int labelCount = countLabels();
		int openMilestoneCount = countMilestonesByStatus(OPEN_FLAG);
		int closedMilestoneCount = countMilestonesByStatus(CLOSED_FLAG);
		int totalMilestoneCount = openMilestoneCount + closedMilestoneCount;

		return MilestoneInfo.builder()
			.labelCount(labelCount)
			.milestoneCount(totalMilestoneCount)
			.openMilestoneCount(openMilestoneCount)
			.closedMilestoneCount(closedMilestoneCount)
			.milestones(milestoneResponses)
			.build();
	}

	private List<MilestoneResponse> fetchMilestoneResponsesWithProgress(String status) {
		final String sql = "SELECT milestone_id, name, deadline, description, status " +
			"FROM milestone " +
			"WHERE status = :status " +
			"ORDER BY milestone_id";

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("status", status);

		return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
			Long id = rs.getLong("milestone_id");
			String name = rs.getString("name");
			Optional<Timestamp> timestamp = Optional.ofNullable(rs.getTimestamp("deadline"));
			LocalDateTime deadline = timestamp.map(Timestamp::toLocalDateTime).orElse(null);
			String description = rs.getString("description");
			int progress = calculateMilestoneProgress(id);
			return MilestoneResponse.of(id, name, deadline, description, status, progress);
		});
	}

	private int calculateMilestoneProgress(Long milestoneId) {
		int openIssueCount = countIssuesByStatusAndMilestoneId(OPEN_FLAG, milestoneId);
		int closedIssueCount = countIssuesByStatusAndMilestoneId(CLOSED_FLAG, milestoneId);
		int totalIssueCount = openIssueCount + closedIssueCount;
		return totalIssueCount > 0 ? (int)((double)closedIssueCount / totalIssueCount * 100) : 0;
	}

	private int countLabels() {
		return jdbcTemplate.queryForObject(
			"SELECT COALESCE(COUNT(*), 0) FROM label WHERE is_deleted = :openFlag",
			Map.of("openFlag", LABEL_OPEN_FLAG),
			Integer.class
		);
	}

	private int countMilestonesByStatus(String status) {
		return jdbcTemplate.queryForObject(
			"SELECT COALESCE(COUNT(*), 0) FROM milestone WHERE status = :status",
			Map.of("status", status, "OPEN_FLAG", OPEN_FLAG),
			Integer.class
		);
	}

	private int countIssuesByStatusAndMilestoneId(String status, Long milestoneId) {
		return jdbcTemplate.queryForObject(
			"SELECT COALESCE(COUNT(*), 0) FROM issue WHERE milestone_id = :milestoneId AND status = :status",
			Map.of("milestoneId", milestoneId, "status", status),
			Integer.class
		);
	}
}
