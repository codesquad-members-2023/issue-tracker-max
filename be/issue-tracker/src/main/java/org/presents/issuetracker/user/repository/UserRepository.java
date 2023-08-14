package org.presents.issuetracker.user.repository;

import java.util.List;
import java.util.Optional;

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

	private final RowMapper<User> userRowMapper =
		(rs, rowNum) -> User.builder()
			.userId(rs.getLong("user_id"))
			.loginId(rs.getString("login_id"))
			.password(rs.getString("password"))
			.image(rs.getString("image"))
			.build();

	public List<User> findByIssueId(Long issueId) {
		final String sql = "SELECT u.user_id, u.login_id, u.image "
			+ "FROM user u JOIN assignee a ON u.user_id = a.user_id "
			+ "WHERE a.issue_id = :issueId";

		MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

		return jdbcTemplate.query(sql, params, userPreviewRowMapper);
	}

	public List<User> findAll() {
		final String sql = "SELECT user_id, login_id, image FROM user";

		return jdbcTemplate.query(sql, userPreviewRowMapper);
	}

	public Optional<User> findByLoginId(String loginId) {
		final String sql = "SELECT user_id, login_id, password, image "
			+ "FROM user WHERE login_id = :loginId";

		MapSqlParameterSource params = new MapSqlParameterSource("loginId", loginId);

		return jdbcTemplate.query(sql, params, userRowMapper)
			.stream()
			.findFirst();
	}

	public void save(User user) {
		final String sql = "INSERT INTO user(login_id, password) VALUES(:loginId, :password)";

		MapSqlParameterSource params = new MapSqlParameterSource("loginId", user.getLoginId())
			.addValue("password", user.getPassword());

		jdbcTemplate.update(sql, params);
	}
}
