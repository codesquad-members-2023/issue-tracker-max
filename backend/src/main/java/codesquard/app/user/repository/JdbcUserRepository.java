package codesquard.app.user.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import codesquard.app.user.entity.User;

@Repository
public class JdbcUserRepository implements UserRepository {

	private final NamedParameterJdbcTemplate template;
	private final SimpleJdbcInsert simpleJdbcInsert;

	public JdbcUserRepository(NamedParameterJdbcTemplate template, DataSource dataSource) {
		this.template = template;
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("user")
			.usingColumns("user_id", "email", "password", "avatar_url")
			.usingGeneratedKeyColumns("id");
	}

	@Override
	public Long save(User user) {
		return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(user)).longValue();
	}

	@Override
	public List<User> findAll() {
		return null;
	}

	@Override
	public User findById(Long id) {
		return null;
	}

	@Override
	public Long modify(User user) {
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		return null;
	}
}
