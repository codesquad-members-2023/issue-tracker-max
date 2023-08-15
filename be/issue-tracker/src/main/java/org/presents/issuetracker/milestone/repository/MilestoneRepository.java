package org.presents.issuetracker.milestone.repository;

import java.util.List;

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
	private final NamedParameterJdbcTemplate jdbcTemplate;

	private final RowMapper<MilestoneInfo> milestoneInfoRowMapper =
		(rs, rowNum) -> MilestoneInfo.builder()
			.id(rs.getLong("milestone_id"))
			.name(rs.getString("name"))
			.deadline(rs.getTimestamp("deadline").toLocalDateTime())
			.description(rs.getString("description"))
			.status(rs.getString("status"))
			.openIssueCount(rs.getInt("open_issue_count"))
			.closedIssueCount(rs.getInt("closed_issue_count"))
			.progress(rs.getInt("progress"))
			.build();

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
}
