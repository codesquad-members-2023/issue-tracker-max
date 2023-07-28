package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.UserAccount;

@Repository
public class UserAccountRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public UserAccountRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("user_account")
			.usingColumns("login_id", "password", "profile_url")
			.usingGeneratedKeyColumns("id");
	}

	public void save(UserAccount userAccount) {
		jdbcInsert.execute(new BeanPropertySqlParameterSource(userAccount));
	}

	public Boolean existsByLoginId(String loginId) {
		return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM user_account WHERE login_id = :loginId)",
			Map.of("loginId", loginId), Boolean.class);
	}

	public Optional<UserAccount> findByLoginId(String loginId) {
		return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(
			"SELECT id, login_id, password FROM user_account WHERE login_id = :loginId AND is_deleted = FALSE",
			Map.of("loginId", loginId), (rs, rowNum) -> new UserAccount(rs.getInt("id"),
				rs.getString("login_id"),
				rs.getString("password")))));
	}
}
