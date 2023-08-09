package org.presents.issuetracker.user.repository;

import java.util.List;

import org.presents.issuetracker.user.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	private final RowMapper<User> userPreviewRowMapper =
		(rs, rowNum) -> User.builder()
			.userId(rs.getLong("user_id"))
			.loginId(rs.getString("login_id"))
			.image(rs.getString("image"))
			.build();

	public List<User> findByIssueId(Long issueId) {
		final String sql = "SELECT u.user_id, u.login_id, u.image "
			+ "FROM user u JOIN assignee a ON u.user_id = a.user_id "
			+ "WHERE a.issue_id = :issueId";

		MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

		return jdbcTemplate.query(sql, params, userPreviewRowMapper);
	}
}
