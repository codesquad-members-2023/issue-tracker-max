package com.issuetrackermax.domain.milestone;

import java.sql.Types;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.milestone.entity.Milestone;

@Repository
public class MilestoneRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MilestoneRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(Milestone milestone) {
		String sql = "INSERT INTO milestone(title, description,is_open) VALUES (:title,:description,:isOpen)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("title", milestone.getTitle(), Types.VARCHAR)
			.addValue("description", milestone.getDescription(), Types.VARCHAR)
			.addValue("isOpen", milestone.getIsOpen(), Types.TINYINT);

		jdbcTemplate.update(sql, parameters, keyHolder);
		return (Long)Objects.requireNonNull(keyHolder.getKeys().get("ID"));
	}

	public Long getMilestoneCount() {
		String sql = "SELECT COUNT(*) FROM milestone";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
	}

	public Boolean existById(Long id) {
		String sql = "SELECT EXISTS (SELECT 1 FROM milestone WHERE id = :id)";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("id", id), Boolean.class);
	}

}
