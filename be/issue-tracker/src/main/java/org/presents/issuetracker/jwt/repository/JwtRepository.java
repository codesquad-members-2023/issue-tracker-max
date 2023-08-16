package org.presents.issuetracker.jwt.repository;

import org.presents.issuetracker.global.error.exception.CustomException;
import org.presents.issuetracker.global.error.statuscode.JwtErrorCode;
import org.presents.issuetracker.jwt.entity.Token;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JwtRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public void save(Token token) {
		final String sql = "INSERT INTO token(refresh_token, login_id) VALUES(:refreshToken, :loginId)";

		MapSqlParameterSource params = new MapSqlParameterSource("refreshToken", token.getRefreshToken())
			.addValue("loginId", token.getLoginId());

		jdbcTemplate.update(sql, params);
	}

	public String findByRefreshToken(String refreshToken) {
		final String sql = "SELECT login_id FROM token WHERE refresh_token = :refreshToken";

		SqlParameterSource params = new MapSqlParameterSource("refreshToken", refreshToken);

		return jdbcTemplate.query(sql, params, (rs, rowNum) ->
				rs.getString("login_id"))
			.stream()
			.findFirst()
			.orElseThrow(() -> new CustomException(JwtErrorCode.INVALID_JWT_TOKEN));
	}
}
