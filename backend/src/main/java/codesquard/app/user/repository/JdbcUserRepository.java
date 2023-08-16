package codesquard.app.user.repository;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquard.app.api.errors.errorcode.UserErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.user.entity.User;

@Repository
public class JdbcUserRepository implements UserRepository {

	private static final Logger logger = LoggerFactory.getLogger(JdbcUserRepository.class);

	private final NamedParameterJdbcTemplate template;

	public JdbcUserRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	public Long save(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO user(login_id, email, password, avatar_url) VALUES(:loginId, :email, :password, :avatarUrl)";
		int update = template.update(sql, user.createSaveParamSource(), keyHolder);
		logger.info("사용자 저장 결과 : {}", update);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	@Override
	public List<User> findAll() {
		String sql = "SELECT id, login_id, email, avatar_url FROM user";
		return template.query(sql, createUserRowMapper());
	}

	@Override
	public User findById(Long id) {
		String sql = "SELECT id, login_id, email, avatar_url FROM user WHERE id = :id";
		return template.query(sql, new MapSqlParameterSource("id", id), createUserRowMapper())
			.stream().findAny().orElseThrow(() -> new RestApiException(UserErrorCode.NOT_FOUND_USER));
	}

	private RowMapper<User> createUserRowMapper() {
		return (rs, rowNum) -> new User(
			rs.getLong("id"),
			rs.getString("login_id"),
			rs.getString("email"),
			null,
			rs.getString("avatar_url")
		);
	}

	@Override
	public Long modify(User user) {
		String sql = "UPDATE user SET email = :email, avatar_url = :avatarUrl WHERE id = :id";
		template.update(sql, user.createSaveParamSource());
		return user.getId();
	}

	@Override
	public Long deleteById(Long id) {
		return null;
	}

	@Override
	public boolean isExistLoginId(User user) {
		String sql = "SELECT EXISTS(SELECT 1 FROM user WHERE login_id = :loginId) AS count";
		return template.query(sql, user.createSaveParamSource(), (rs, rowNum) -> rs.getBoolean("count"))
			.stream()
			.anyMatch(aBoolean -> aBoolean);
	}

	@Override
	public boolean isExistEmail(User user) {
		String sql = "SELECT EXISTS(SELECT 1 FROM user WHERE email = :email) AS count";
		return template.query(sql, user.createSaveParamSource(), (rs, rowNum) -> rs.getBoolean("count"))
			.stream()
			.anyMatch(aBoolean -> aBoolean);
	}

	@Override
	public User findByLoginIdAndPassword(User user) {
		String sql = "SELECT id, login_id, email, avatar_url FROM user WHERE login_id = :loginId AND password = :password";
		return template.query(sql, user.createSaveParamSource(), createUserRowMapper()).stream()
			.findAny()
			.orElseThrow(() -> new RestApiException(UserErrorCode.NOT_FOUND_USER));
	}

	@Override
	public User findByRefreshToken(String refreshToken) {
		String sql = "SELECT u.id, u.login_id, u.email, u.avatar_url FROM user u INNER JOIN authenticate_user au ON u.id = au.id WHERE au.refreshToken = :refreshToken";
		MapSqlParameterSource paramSource = new MapSqlParameterSource("refreshToken", refreshToken);
		return template.query(sql, paramSource, createUserRowMapper())
			.stream()
			.findAny()
			.orElseThrow(() -> new RestApiException(UserErrorCode.NOT_FOUND_USER));
	}

	@Override
	public User findByEmail(String email) {
		String sql = "SELECT id, login_id, email, avatar_url FROM user WHERE email = :email";
		return template.query(sql, new MapSqlParameterSource("email", email), createUserRowMapper())
			.stream()
			.findAny()
			.orElse(null);
	}
}
