package org.presents.issuetracker.milestone.repository;

import org.presents.issuetracker.milestone.entity.vo.MilestonePreview;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MilestoneRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	private final RowMapper<MilestonePreview> milestonePreviewRowMapper =
		(rs, rowNum) -> MilestonePreview.builder()
			.id(rs.getLong("milestone_id"))
			.name(rs.getString("name"))
			.progress(rs.getInt("progress"))
			.build();

	public MilestonePreview findByIssueId(Long issueId) {
		final String sql = "SELECT m.milestone_id, m.name, "
			+ "(SELECT FLOOR(COUNT(CASE WHEN status = 'close' THEN 1 END) / COUNT(*) * 100) "
			+ "FROM issue "
			+ "WHERE milestone_id = i.milestone_id AND status IN ('open', 'close')) progress "
			+ "FROM milestone m JOIN issue i ON m.milestone_id = i.milestone_id "
			+ "WHERE i.issue_id = :issueId";

		MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

		return jdbcTemplate.queryForObject(sql, params, milestonePreviewRowMapper);
	}
}
