package com.issuetrackermax.domain.member;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.member.Entity.Member;

@Repository
public class MemberRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MemberRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Optional<Member> findByMemberEmail(String email) {
		String sql = "SELECT id, password, nick_name, email FROM member WHERE email = :email ";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("email", email), MEMBER_ROW_MAPPER)));
	}

	public Long save(Member member) {
		String sql = "INSERT INTO member(nick_name, password, email) VALUES (:nickName,:password, :email)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(member);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, rowNum) ->
		new Member(rs.getLong("id"), rs.getString("nick_name"), rs.getString("password"),
			rs.getString("email"));
}
