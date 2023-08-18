package codesquad.issueTracker.user.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquad.issueTracker.jwt.domain.Token;
import codesquad.issueTracker.user.domain.LoginType;
import codesquad.issueTracker.user.domain.User;

@Repository
public class UserRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long insert(User user) {
		String sql = "INSERT INTO users (email,password,name, login_type, profile_img) VALUES (:email,:password,:name,:loginType,:profileImg)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", user.getEmail());
		params.addValue("password", user.getPassword());
		params.addValue("name", user.getName());
		params.addValue("loginType", user.getLoginType().getType());
		params.addValue("profileImg", user.getProfileImg());

		jdbcTemplate.update(sql, params, keyHolder);
		return keyHolder.getKey().longValue();
	}

	public Optional<User> findByEmail(String email) {
		String sql = "SELECT id, email, password, profile_img, name, login_type FROM users WHERE email = :email";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("email", email), userRowMapper)));
	}

	public Long insertRefreshToken(Long userId, String refreshToken) {
		String sql = "INSERT INTO tokens (user_id,refresh_token) VALUES(:userId,:refreshToken)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userId", userId);
		params.addValue("refreshToken", refreshToken);

		jdbcTemplate.update(sql, params, keyHolder);
		return keyHolder.getKey().longValue();
	}

	public Optional<Token> findTokenByUserId(Long userId) {
		String sql = "SELECT id, user_id, refresh_token FROM tokens WHERE user_id = :userId";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("userId", userId), tokenRowMapper)));
	}

	public int updateRefreshToken(Long userId, String refreshToken) {
		String sql = "UPDATE tokens SET refresh_token = :refreshToken WHERE user_Id = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userId", userId);
		params.addValue("refreshToken", refreshToken);
		return jdbcTemplate.update(sql, params);
	}

	public Optional<Token> findTokenByUserToken(String refreshToken) {
		String sql = "SELECT id, user_id, refresh_token FROM tokens WHERE refresh_token = :refreshToken";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(
				jdbcTemplate.query(sql, Map.of("refreshToken", refreshToken), tokenRowMapper)));
	}

	public Optional<Integer> deleteTokenByUserId(Long userId) {
		String sql = "DELETE FROM tokens WHERE user_id = :userId";
		int result = jdbcTemplate.update(sql, Map.of("userId", userId));

		return result > 0 ? Optional.of(result) : Optional.of(null);
	}

	private final RowMapper<User> userRowMapper = (((rs, rowNum) -> User.builder()
		.id(rs.getLong("id"))
		.email(rs.getString("email"))
		.password(rs.getString("password"))
		.profileImg(rs.getString("profile_img"))
		.name(rs.getString("name"))
		.loginType(LoginType.findByTypeString(rs.getString("login_type")))
		.build()
	));

	private final RowMapper<Token> tokenRowMapper = (((rs, rowNum) -> Token.builder()
		.id(rs.getLong("id"))
		.userId(rs.getLong("user_id"))
		.refreshToken(rs.getString("refresh_token"))
		.build()
	));

	public Long updateUserLoginType(User existUser, User user) {
		String sql = "UPDATE users SET login_type = :loginType WHERE id = :userId";
		jdbcTemplate.update(sql, Map.of("loginType", user.getLoginType().getType(), "userId", existUser.getId()));
		return existUser.getId();
	}

	public Optional<User> findById(Long id) {
		String sql = "SELECT id, email, password, profile_img, name, login_type FROM users WHERE id = :id";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(
				jdbcTemplate.query(sql, Map.of("id", id), userRowMapper)));
	}

	public List<User> findAll() {
		String sql = "SELECT * FROM users";
		return jdbcTemplate.query(sql, userRowMapper);
	}
}

