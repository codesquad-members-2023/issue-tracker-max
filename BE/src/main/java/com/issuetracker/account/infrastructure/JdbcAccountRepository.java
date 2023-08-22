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

	private final String FIND_BY_EMAIL_SQL
		= "SELECT member.id, member.email, member.nickname, member.profile_image_url, git.oauth_id "
		+ "FROM member member "
		+ "LEFT JOIN git_member git "
		+ "ON member.id = git.member_id "
		+ "WHERE member.email = :email";
	private final String FIND_BY_MEMBER_ID_SQL
		= "SELECT member.id, member.email, member.nickname, member.profile_image_url, git.oauth_id "
		+ "FROM member member "
		+ "LEFT JOIN git_member git "
		+ "ON member.id = git.member_id "
		+ "WHERE member.id = :id";
	private final String FIND_BY_EMAIL_AND_PASSWORD_SQL
		= "SELECT member.id, member.email, member.nickname, member.profile_image_url, git.oauth_id "
		+ "FROM member member "
		+ "LEFT JOIN git_member git "
		+ "ON member.id = git.member_id "
		+ "WHERE member.email = :email "
		+ "AND member.password = :password";
	private final String FIND_BY_OAUTH_ID_SQL
		= "SELECT member.id, member.email, member.nickname, member.profile_image_url, git.oauth_id "
		+ "FROM member member "
		+ "LEFT JOIN git_member git "
		+ "ON member.id = git.member_id "
		+ "WHERE git.oauth_id = :oauthId";
	private final String SAVE_SQL = "INSERT INTO member(id, email, password, nickname, profile_image_url) VALUE (:id, :email, :password, :nickname, :profileImageUrl)";
	private final String EXIST_BY_EMAIL_SQL = "SELECT EXISTS(SELECT id FROM member WHERE email = :email) AS exist";
	private final String EXIST_BY_NICKNAME_SQL = "SELECT EXISTS(SELECT id FROM member WHERE nickname = :nickname) AS exist";
	private final String SAVE_GIT_MEMBER_SQL = "INSERT INTO git_member(oauth_id, member_id) VALUE(:oauthId, :memberId)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcAccountRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Account findByEmail(String email) {

		try {
			return jdbcTemplate.queryForObject(FIND_BY_EMAIL_SQL, Map.of("email", email), ACCOUNT_ROW_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return new Account();
		}
	}

	public Account findByEmailAndPassword(Account account) {
		Map<String, String> params = Map.of(
			"email", account.getEmail(),
			"password", account.getPassword());

		try {
			return jdbcTemplate.queryForObject(FIND_BY_EMAIL_AND_PASSWORD_SQL, params, ACCOUNT_ROW_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return new Account();
		}
	}

	public Account findByMemberId(Long memberId) {

		try {
			return jdbcTemplate.queryForObject(FIND_BY_MEMBER_ID_SQL, Map.of("id", memberId), ACCOUNT_ROW_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return new Account();
		}
	}

	public Account findByOauthId(Long oauthId) {

		try {
			return jdbcTemplate.queryForObject(FIND_BY_OAUTH_ID_SQL, Map.of("oauthId", oauthId), ACCOUNT_ROW_MAPPER);
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
			jdbcTemplate.queryForObject(EXIST_BY_EMAIL_SQL, Map.of("email", email), Boolean.class)
		);
	}

	public boolean existByNickname(String nickname) {
		return Boolean.TRUE.equals(
			jdbcTemplate.queryForObject(EXIST_BY_NICKNAME_SQL, Map.of("nickname", nickname), Boolean.class)
		);
	}

	public void saveGitMember(Long memberId, Long oauthId) {
		Map<String, Long> params = Map.of("memberId", memberId, "oauthId", oauthId);
		jdbcTemplate.update(SAVE_GIT_MEMBER_SQL, params);
	}

	private static final RowMapper<Account> ACCOUNT_ROW_MAPPER = (rs, rowNum) ->
		Account.builder()
			.id(rs.getLong("id"))
			.email(rs.getString("email"))
			.nickname(rs.getString("nickname"))
			.profileImageUrl(rs.getString("profile_image_url"))
			.oauthId(rs.getLong("oauth_id"))
			.build();

}
