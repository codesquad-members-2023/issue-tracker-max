package codesquard.app.authenticate_user.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquard.app.jwt.Jwt;
import codesquard.app.user.entity.User;

@Repository
public class JdbcAuthenticateUserRepository implements AuthenticateUserRepository {

	private static final Logger logger = LoggerFactory.getLogger(JdbcAuthenticateUserRepository.class);

	private final NamedParameterJdbcTemplate template;

	public JdbcAuthenticateUserRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	public boolean isExistRefreshToken(User user) {
		String sql = "SELECT EXISTS(SELECT 1 FROM authenticate_user WHERE id = :id) AS count";
		return template.query(sql, user.createSaveParamSource(), (rs, rowNum) -> rs.getBoolean("count"))
			.stream()
			.anyMatch(aBoolean -> aBoolean);
	}

	@Override
	public void updateRefreshToken(User user, Jwt jwt) {
		String sql = "UPDATE authenticate_user SET refreshToken = :refreshToken WHERE id = :id";
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValues(user.createSaveParamSource().getValues());
		parameterSource.addValues(jwt.createParamSource().getValues());

		int update = template.update(sql, parameterSource);
		logger.info("updateRefreshToken update query row : {}", update);
	}

	@Override
	public void saveRefreshToken(User user, Jwt jwt) {
		String sql = "INSERT INTO authenticate_user (id, refreshToken) VALUES (:id, :refreshToken)";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValues(user.createSaveParamSource().getValues());
		paramSource.addValues(jwt.createParamSource().getValues());
		template.update(sql, paramSource);
	}
}
