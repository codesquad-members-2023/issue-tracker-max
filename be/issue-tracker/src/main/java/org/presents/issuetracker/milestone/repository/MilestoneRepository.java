package org.presents.issuetracker.milestone.repository;

import org.presents.issuetracker.milestone.entity.vo.MilestoneInfo;
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

	// 마일스톤 목록 조회용
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

	// 마일스톤 선택용 목록 조회용
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
}
