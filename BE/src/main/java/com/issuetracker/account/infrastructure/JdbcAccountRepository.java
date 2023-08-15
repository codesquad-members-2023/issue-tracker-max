package com.issuetracker.account.infrastructure;

import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.issuetracker.account.domain.Account;
import com.issuetracker.account.domain.AccountRepository;

@Repository
public class JdbcAccountRepository implements AccountRepository {

	private final String FIND_BY_EMAIL = "SELECT id, email, password, nickname, profile_image_url FROM member WHERE email = :email";

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

	private static final RowMapper<Account> ACCOUNT_ROW_MAPPER = (rs, rowNum) ->
		Account.builder()
			.id(rs.getLong("id"))
			.email(rs.getString("email"))
			.password(rs.getString("password"))
			.nickname(rs.getString("nickname"))
			.profileImageUrl(rs.getString("profile_image_url"))
			.build();

}
