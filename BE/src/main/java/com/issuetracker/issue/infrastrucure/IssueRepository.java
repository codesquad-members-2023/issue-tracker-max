package com.issuetracker.issue.infrastrucure;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.IssuesCountData;

@Repository
public class IssueRepository {

	private static final String SAVE_SQL = "INSERT INTO issue(title, content, is_open, create_at, milestone_id, author_id) VALUES(:title, :content, :isOpen, :createAt, :milestoneId, :authorId)";
	private static final String FIND_ALL_COUNT_SQL = "SELECT SUM(CASE WHEN is_open = 0 THEN 1 ELSE 0 END) AS issue_close_count, SUM(CASE WHEN is_open = 1 THEN 1 ELSE 0 END) AS issue_open_count, (SELECT COUNT(id) FROM label) AS label_count, (SELECT COUNT(id) FROM milestone) AS milestone_count FROM issue";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(Issue issue) {
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("title", issue.getTitle())
			.addValue("content", issue.getContent())
			.addValue("isOpen", issue.getIsOpen())
			.addValue("createAt", issue.getCreateAt())
			.addValue("milestoneId", issue.getMilestone().getId())
			.addValue("authorId", issue.getAuthor().getId());
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(SAVE_SQL, param, keyHolder);
		return keyHolder.getKey().longValue();
	}

	public IssuesCountData findAllCount() {
		return jdbcTemplate.queryForObject(FIND_ALL_COUNT_SQL, Map.of(), ISSUE_MAIN_PAGE_COUNT_ROW_MAPPER);
	}

	private static final RowMapper<IssuesCountData> ISSUE_MAIN_PAGE_COUNT_ROW_MAPPER = (rs, rowNum) ->
		IssuesCountData.builder()
			.issueOpenCount(rs.getInt("issue_open_count"))
			.issueCloseCount(rs.getInt("issue_close_count"))
			.labelCount(rs.getInt("label_count"))
			.milestoneCount(rs.getInt("milestone_count"))
			.build();
}
