package com.issuetracker.issue.infrastrucure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.IssueLabelMapping;

@Repository
public class IssueLabelMappingRepository {

	private static final String SAVE_ALL_SQL = "INSERT INTO issue_label_mapping(issue_id, label_id) VALUES(:issueId, :labelId)";
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueLabelMappingRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public int[] saveAll(List<IssueLabelMapping> issueLabelMappings) {
		BeanPropertySqlParameterSource[] params = issueLabelMappings.stream()
			.map(BeanPropertySqlParameterSource::new)
			.toArray(BeanPropertySqlParameterSource[]::new);
		return jdbcTemplate.batchUpdate(SAVE_ALL_SQL, params);
	}
}
