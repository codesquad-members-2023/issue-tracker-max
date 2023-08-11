package com.issuetrackermax.domain.jwt;

import java.util.Map;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JwtRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JwtRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long findByRefreshToken(String refreshToken) {
		String sql = "SELECT member_id FROM token WHERE refresh_token = :refreshToken ";
		return jdbcTemplate.queryForObject(sql, Map.of("refreshToken", refreshToken), Long.class);
	}

	public Long saveRefreshToken(String refreshToken, Long id) {
		String sql = "INSERT INTO token (refresh_token, member_id) VALUES (:refreshToken,:id)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, new MapSqlParameterSource()
			.addValue("refreshToken", refreshToken)
			.addValue("id", id), keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	public int deleteRefreshToken(String refreshToken, Long id) {
		String sql = "DELETE FROM token WHERE refresh_token = :refreshToken and id = :id";
		return jdbcTemplate.update(sql, new MapSqlParameterSource()
			.addValue("refreshToken", refreshToken)
			.addValue("id", id));
	}

	public Boolean existsRefreshToken(String refreshToken) {
		String sql = "SELECT EXISTS (SELECT 1 FROM token WHERE refresh_token = :refreshToken)";
		return jdbcTemplate.queryForObject(sql, Map.of("refreshToken", refreshToken), Boolean.class);
	}
}
