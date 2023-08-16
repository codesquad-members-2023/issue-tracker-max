package com.issuetracker.account.infrastructure;

import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.account.domain.Account;
import com.issuetracker.account.domain.AccountRepository;

@Repository
public class JdbcAccountRepository implements AccountRepository {

	private final String FIND_BY_EMAIL = "SELECT id, email, password, nickname, profile_image_url FROM member WHERE email = :email";
	private final String FIND_BY_MEMBER_ID = "SELECT id, email, password, nickname, profile_image_url FROM member WHERE id = :id";
	private final String FIND_BY_EMAIL_AND_PASSWORD = "SELECT id, email, password, nickname, profile_image_url FROM member WHERE email = :email AND password = :password";
	private final String SAVE_SQL = "INSERT INTO member(id, email, password, nickname, profile_image_url) VALUE (:id, :email, :password, :nickname, :profileImageUrl)";
	private final String EXIST_BY_EMAIL = "SELECT EXISTS(SELECT id FROM member WHERE email = :email) AS exist";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcAccountRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Account findByEmail(String email) {

		try {
			return jdbcTemplate.queryForObject(FIND_BY_EMAIL, Map.of("email", email), ACCOUNT_ROW_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return new Account();
		}
	}

	public Account findByEmailAndPassword(Account account) {
		Map<String, String> params = Map.of(
			"email", account.getEmail(),
			"password", account.getPassword());

		try {
			return jdbcTemplate.queryForObject(FIND_BY_EMAIL_AND_PASSWORD, params, ACCOUNT_ROW_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return new Account();
		}
	}

	public Account findByMemberId(Long memberId) {

		try {
			return jdbcTemplate.queryForObject(FIND_BY_MEMBER_ID, Map.of("id", memberId), ACCOUNT_ROW_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return new Account();
		}
	}

	public Long save(Account account) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource param = new BeanPropertySqlParameterSource(account);
		jdbcTemplate.update(SAVE_SQL, param, keyHolder);
		return keyHolder.getKey().longValue();
	}

	public boolean existByEmail(String email) {
		return Boolean.TRUE.equals(
			jdbcTemplate.queryForObject(EXIST_BY_EMAIL, Map.of("email", email), Boolean.class)
		);
	}

	private static final RowMapper<Account> ACCOUNT_ROW_MAPPER = (rs, rowNum) ->
		Account.builder()
			.id(rs.getLong("id"))
			.email(rs.getString("email"))
			.password(rs.getString("password"))
			.nickname(rs.getString("nickname"))
			.profileImageUrl(rs.getString("profile_image_url"))
			.build();

}
