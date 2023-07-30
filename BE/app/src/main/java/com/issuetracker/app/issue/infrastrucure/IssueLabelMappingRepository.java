package com.issuetracker.app.issue.infrastrucure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.app.issue.domain.IssueLabelMapping;

@Repository
public class IssueLabelMappingRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueLabelMappingRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(IssueLabelMapping issueLabelMapping) {
		String sql = "INSERT INTO issue_label_mapping(issue_id, label_id) "
			+ "VALUES(:issueId, :labelId)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(issueLabelMapping), keyHolder);

		return keyHolder.getKey().longValue();
	}

	public List<Long> saveAll(List<IssueLabelMapping> issueLabelMappings) {
		return issueLabelMappings.stream()
			.map(this::save)
			.collect(Collectors.toUnmodifiableList());
	}
}
