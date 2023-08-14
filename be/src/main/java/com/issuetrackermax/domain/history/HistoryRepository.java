package com.issuetrackermax.domain.history;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.HistoryException;
import com.issuetrackermax.domain.history.entity.History;

@Repository
public class HistoryRepository {
	private static final RowMapper<History> HISTORY_ROW_MAPPER = ((rs, rowNum) -> History.builder()
		.id(rs.getLong("id"))
		.editor(rs.getString("editor"))
		.issueId(rs.getLong("issue_id"))
		.issueIsOpen(rs.getBoolean("issue_is_open"))
		.modifiedAt(rs.getTimestamp("modified_at").toLocalDateTime())
		.build());
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public HistoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public List<History> findAll() {
		String sql = "SELECT id, issue_id, editor, issue_is_open, modified_at FROM history";
		return jdbcTemplate.query(sql, HISTORY_ROW_MAPPER);
	}

	public History findLatestByIssueId(Long issueId) {
		String sql = "SELECT h.id, h.issue_id, h.editor, h.issue_is_open, h.modified_at FROM history h "
			+ "WHERE h.id = ("
			+ "SELECT MAX(id) "
			+ "FROM history "
			+ "WHERE issue_id = :issueId)";
		return jdbcTemplate.query(sql, Map.of("issueId", issueId), HISTORY_ROW_MAPPER).stream()
			.findAny()
			.orElseThrow(() -> new ApiException(HistoryException.NOT_FOUND_HISTORY));
	}

	public Long save(History history) {
		String sql = "INSERT INTO history(editor ,issue_id, issue_is_open) VALUES (:editor,:issueId,:issueIsOpen)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("editor", history.getEditor(), Types.VARCHAR)
			.addValue("issueId", history.getIssueId(), Types.BIGINT)
			.addValue("issueIsOpen", history.getIssueIsOpen(), Types.TINYINT);
		jdbcTemplate.update(sql, parameters, keyHolder, new String[] {"id"});
		return keyHolder.getKey().longValue();
	}
}
