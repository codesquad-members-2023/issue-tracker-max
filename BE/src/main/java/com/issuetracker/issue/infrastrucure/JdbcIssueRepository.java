package com.issuetracker.issue.infrastrucure;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.IssueRepository;
import com.issuetracker.issue.domain.IssuesCountData;

@Repository
public class JdbcIssueRepository implements IssueRepository {

	private static final String SAVE_SQL = "INSERT INTO issue(title, content, is_open, create_at, milestone_id, author_id) VALUES(:title, :content, :isOpen, :createAt, :milestoneId, :authorId)";
	private static final String FIND_ALL_COUNT_SQL = "SELECT SUM(CASE WHEN is_open = false THEN true ELSE false END) AS issue_close_count, SUM(CASE WHEN is_open = 1 THEN 1 ELSE 0 END) AS issue_open_count, (SELECT COUNT(id) FROM label WHERE label.is_deleted = false) AS label_count, (SELECT COUNT(id) FROM milestone WHERE milestone.is_deleted = false) AS milestone_count FROM issue WHERE issue.is_deleted = false";
	private static final String UPDATE_IS_OPEN_SQL = "UPDATE issue SET is_open = :isOpen WHERE id = :id";
	private static final String UPDATE_TITLE_SQL = "UPDATE issue SET title = :title WHERE id = :id";
	private static final String UPDATE_CONTENT_SQL = "UPDATE issue SET content = :content WHERE id = :id";
	private static final String UPDATE_MILESTONE_SQL = "UPDATE issue SET milestone_id = :milestoneId WHERE id = :id";
	private static final String DELETE_SQL = "UPDATE issue SET is_deleted = true WHERE id = :id";
	private static final String EXIST_BY_ID_SQL = "SELECT EXISTS(SELECT 1 FROM issue WHERE id = :id AND is_deleted = false)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcIssueRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public Long save(Issue issue) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource param = new BeanPropertySqlParameterSource(issue);
		jdbcTemplate.update(SAVE_SQL, param, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public IssuesCountData findAllCount() {
		return jdbcTemplate.queryForObject(FIND_ALL_COUNT_SQL, Map.of(), ISSUE_MAIN_PAGE_COUNT_ROW_MAPPER);
	}

	@Override
	public int updateOpen(long id, boolean isOpen) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("isOpen", isOpen);
		return jdbcTemplate.update(UPDATE_IS_OPEN_SQL, param);
	}

	@Override
	public int updateTitle(long id, String title) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("title", title);
		return jdbcTemplate.update(UPDATE_TITLE_SQL, param);
	}

	@Override
	public int updateContent(long id, String content) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("content", content);
		return jdbcTemplate.update(UPDATE_CONTENT_SQL, param);
	}

	@Override
	public int updateMilestone(long id, Long milestoneId) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("milestoneId", milestoneId);
		return jdbcTemplate.update(UPDATE_MILESTONE_SQL, param);
	}

	@Override
	public int delete(long id) {
		return jdbcTemplate.update(DELETE_SQL, Map.of("id", id));
	}

	@Override
	public boolean existById(long id) {
		return jdbcTemplate.queryForObject(EXIST_BY_ID_SQL, Map.of("id", id), Boolean.class);
	}

	private static final RowMapper<IssuesCountData> ISSUE_MAIN_PAGE_COUNT_ROW_MAPPER = (rs, rowNum) ->
		IssuesCountData.builder()
			.issueOpenCount(rs.getInt("issue_open_count"))
			.issueCloseCount(rs.getInt("issue_close_count"))
			.labelCount(rs.getInt("label_count"))
			.milestoneCount(rs.getInt("milestone_count"))
			.build();
}
