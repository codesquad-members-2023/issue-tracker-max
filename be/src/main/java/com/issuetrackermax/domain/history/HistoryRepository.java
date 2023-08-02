package com.issuetrackermax.domain.history;

import java.sql.Types;
import java.util.Map;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.history.entity.History;

@Repository
public class HistoryRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<History> historyRowMapper = ((rs, rowNum) -> History.builder()
		.id(rs.getLong("id"))
		.editor(rs.getString("editor"))
		.issueId(rs.getLong("issue_id"))
		.issueIsOpen(rs.getBoolean("issue_is_open"))
		.modifiedAt(rs.getTimestamp("modified_at").toLocalDateTime())
		.build());

	public HistoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	// todo : 예외처리
	public History findLatestByIssueId(Long issueId) {
		String sql = "SELECT h.editor, h.issue_is_open, h.modified_at FROM history h "
			+ "WHERE h.id = ("
			+ "SELECT MAX(id) "
			+ "FROM history "
			+ "WHERE issue_id = :issueId)";
		return jdbcTemplate.query(sql, Map.of("issueId", issueId), historyRowMapper).stream()
			.findAny()
			.orElseThrow(() -> new RuntimeException());
	}

	public Long save(History history) {
		String sql = "INSERT INTO history(editor ,issue_id, issue_is_open) VALUES (:editor,:issueId,:issueIsOpen)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("editor", history.getEditor(), Types.BIGINT)
			.addValue("issueId", history.getIssueId(), Types.BIGINT)
			.addValue("issueIsOpen", history.getIssueIsOpen(), Types.TINYINT);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return (Long)Objects.requireNonNull(keyHolder.getKeys().get("ID"));
	}
}
