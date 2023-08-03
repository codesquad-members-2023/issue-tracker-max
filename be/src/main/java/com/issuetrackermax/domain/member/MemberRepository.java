package com.issuetrackermax.domain.member;

import java.sql.Types;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;

@Repository
public class MemberRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MemberRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Optional<Member> findByMemberLoginId(String loginId) {
		String sql = "SELECT id, password, nick_name, login_id, login_type FROM member WHERE login_id = :loginId ";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("loginId", loginId), MEMBER_ROW_MAPPER)));
	}

	public Long save(Member member) {
		String sql = "INSERT INTO member(nick_name, password, login_id, login_type) VALUES (:nickName,:password, :loginId, :loginType)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("nickName", member.getNickName(), Types.VARCHAR)
			.addValue("password", member.getPassword(), Types.VARCHAR)
			.addValue("loginId", member.getLoginId(), Types.VARCHAR)
			.addValue("loginType", member.getLoginType().getName(), Types.VARCHAR);

		jdbcTemplate.update(sql, parameters, keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	public Boolean existsLoginId(String loginId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM member WHERE login_id = :loginId)";
		return jdbcTemplate.queryForObject(sql, Map.of("loginId", loginId), Boolean.class);
	}

	private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, rowNum) ->
		new Member(rs.getLong("id"), rs.getString("login_id"), rs.getString("password"), rs.getString("nick_name"),
			LoginType.valueOf(rs.getString("login_type")));
}
