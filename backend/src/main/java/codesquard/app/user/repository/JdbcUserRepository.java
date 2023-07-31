package codesquard.app.user.repository;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquard.app.issue.entity.Issue;
import codesquard.app.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JdbcUserRepository implements UserRepository {

	private final NamedParameterJdbcTemplate template;

	@Override
	public Long save(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO user(login_id, email, password, avatar_url) VALUES(:loginId, :email, :password, :avatarUrl)";
		template.update(sql, getSaveRequestParamSource(user), keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	private MapSqlParameterSource getSaveRequestParamSource(User user) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("loginId", user.getLoginId());
		parameterSource.addValue("email", user.getEmail());
		parameterSource.addValue("password", user.getPassword());
		parameterSource.addValue("avatarUrl", user.getAvatarUrl());
		return parameterSource;
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
