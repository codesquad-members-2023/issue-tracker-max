package com.issuetrackermax.domain.issue;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.IssueException;
import com.issuetrackermax.domain.issue.entity.Issue;
import com.issuetrackermax.domain.issue.entity.IssueResultVO;

@Repository
public class IssueRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public IssueResultVO findIssueDetailsById(Long id) {
		String sql = "SELECT i.id, i.is_open, i.title, "
			+ "GROUP_CONCAT(DISTINCT l.id ORDER BY l.id SEPARATOR ',') AS label_ids, "
			+ "m.id AS writer_id, m.nick_name AS writer, m.image_url AS image_url, "
			+ "GROUP_CONCAT(DISTINCT m2.id ORDER BY a.id SEPARATOR ',') AS assignee_ids, "
			+ "GROUP_CONCAT(DISTINCT m2.nick_name ORDER BY a.id SEPARATOR ',') "
			+ "AS assignee_names, ms.id AS milestone_id, ms.title AS milestone_title "
			+ "FROM issue as i "
			+ "LEFT JOIN issue_label il ON i.id = il.issue_id "
			+ "LEFT JOIN label l ON il.label_id = l.id "
			+ "LEFT JOIN assignee a ON i.id = a.issue_id "
			+ "LEFT JOIN member m ON i.writer_id = m.id "
			+ "LEFT JOIN member m2 ON a.member_id = m2.id "
			+ "LEFT JOIN milestone ms ON i.milestone_id = ms.id "
			+ "WHERE i.id = :id "
			+ "GROUP BY i.id, i.is_open, i.title, m.id, m.nick_name, ms.id, ms.title";
		return jdbcTemplate.query(sql, Map.of("id", id), ISSUE_RESULT_VO_ROW_MAPPER).stream()
			.findAny()
			.orElseThrow(() -> new ApiException(IssueException.NOT_FOUND_ISSUE));
	}

	public List<Issue> findByMilestoneId(Long milestoneId) {
		String sql = "SELECT id, title, is_open, writer_id, milestone_id,created_at FROM issue WHERE milestone_id = :milestoneId";
		return jdbcTemplate.query(sql, Map.of("milestoneId", milestoneId), ISSUE_ROW_MAPPER);
	}

	public Long save(Issue issue) {
		String sql = "INSERT INTO issue(title, is_open, writer_id, milestone_id) "
			+ "VALUES (:title, :isOpen, :writerId, :milestoneId)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, new MapSqlParameterSource()
			.addValue("title", issue.getTitle())
			.addValue("isOpen", issue.getIsOpen())
			.addValue("writerId", issue.getWriterId())
			.addValue("milestoneId", issue.getMilestoneId()), keyHolder, new String[] {"id"});
		return keyHolder.getKey().longValue();

	}

	public int openByIds(List<Long> ids) {
		String sql = "UPDATE issue SET is_open = true WHERE id IN (:ids)";
		return jdbcTemplate.update(sql, new MapSqlParameterSource("ids", ids));
	}

	public int closeByIds(List<Long> ids) {
		String sql = "UPDATE issue SET is_open = false WHERE id IN (:ids)";
		return jdbcTemplate.update(sql, new MapSqlParameterSource("ids", ids));
	}

	public int deleteById(Long id) {
		String sql = "DELETE FROM issue WHERE id = :id";
		return jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}

	public int modifyTitle(Long issueId, String title) {
		String sql = "UPDATE issue SET title = :title WHERE id = :issueId";
		return jdbcTemplate.update(sql, new MapSqlParameterSource()
			.addValue("issueId", issueId)
			.addValue("title", title));
	}

	public int applyMilestoneToIssue(Long issueId, Long milestoneId) {
		String sql = "UPDATE issue SET milestone_id = :milestoneId WHERE id = :issueId";
		return jdbcTemplate.update(sql, new MapSqlParameterSource()
			.addValue("issueId", issueId)
			.addValue("milestoneId", milestoneId));
	}

	public List<Issue> getOpenIssue() {
		String sql = "SELECT id, title, is_open, writer_id, milestone_id,created_at FROM issue WHERE is_open = :isOpen";
		return jdbcTemplate.query(sql, Map.of("isOpen", 1), ISSUE_ROW_MAPPER);
	}

	public List<Issue> getClosedIssue() {
		String sql = "SELECT id, title, is_open, writer_id, milestone_id,created_at FROM issue WHERE is_open = :isOpen";
		return jdbcTemplate.query(sql, Map.of("isOpen", 0), ISSUE_ROW_MAPPER);
	}

	public Boolean existById(Long id) {
		String sql = "SELECT EXISTS (SELECT 1 FROM issue WHERE id = :id)";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("id", id), Boolean.class);
	}

	public Boolean existByIds(List<Long> ids) {
		String sql = "SELECT COUNT(*) FROM issue WHERE id IN (:ids)";
		Integer count = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("ids", ids), Integer.class);
		return count != null && count.equals(ids.size());
	}

	private static final RowMapper<Issue> ISSUE_ROW_MAPPER = (rs, rowNum) ->
		Issue.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.isOpen(rs.getBoolean("is_open"))
			.writerId(rs.getLong("writer_id"))
			.milestoneId(rs.getLong("milestone_id"))
			.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
			.build();

	private static final RowMapper<IssueResultVO> ISSUE_RESULT_VO_ROW_MAPPER = (((rs, rowNum) ->
		IssueResultVO.builder()
			.id(rs.getLong("id"))
			.isOpen(rs.getBoolean("is_open"))
			.title(rs.getString("title"))
			.labelIds(rs.getString("label_ids"))
			.writerId(rs.getLong("writer_id"))
			.writer(rs.getString("writer"))
			.writerImageUrl(rs.getString("image_url"))
			.assigneeIds(rs.getString("assignee_ids"))
			.assigneeNames(rs.getString("assignee_names"))
			.milestoneId(rs.getLong("milestone_id"))
			.milestoneTitle(rs.getString("milestone_title"))
			.build()));
}
