package com.issuetrackermax.domain.member;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

	private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, rowNum) ->
		Member.builder()
			.id(rs.getLong("id"))
			.loginId(rs.getString("login_id"))
			.password(rs.getString("password"))
			.nickName(rs.getString("nick_name"))
			.imageUrl(rs.getString("image_url"))
			.loginType(LoginType.valueOf(rs.getString("login_type")))
			.build();
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MemberRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public List<Member> findAll() {
		String sql = "SELECT id, password, nick_name, login_id, image_url, login_type "
			+ "FROM member";
		return jdbcTemplate.query(sql, MEMBER_ROW_MAPPER);
	}

	public Member findByMemberLoginId(String loginId) {
		String sql = "SELECT id, password, nick_name, login_id, image_url ,login_type "
			+ "FROM member WHERE login_id = :loginId ";
		return DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("loginId", loginId), MEMBER_ROW_MAPPER));
	}

	public Member findById(Long id) {
		String sql = "SELECT id, password, nick_name, login_id, image_url, login_type FROM member WHERE id = :id ";
		return DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("id", id), MEMBER_ROW_MAPPER));
	}

	public Long save(Member member) {
		String sql = "INSERT INTO member(nick_name, password, login_id, login_type, image_url) "
			+ "VALUES (:nickName,:password, :loginId, :loginType, :imageUrl)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("nickName", member.getNickName(), Types.VARCHAR)
			.addValue("password", member.getPassword(), Types.VARCHAR)
			.addValue("loginId", member.getLoginId(), Types.VARCHAR)
			.addValue("loginType", member.getLoginType().getName(), Types.VARCHAR)
			.addValue("imageUrl", member.getImageUrl(), Types.VARCHAR);

		jdbcTemplate.update(sql, parameters, keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	public Boolean existsLoginId(String loginId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM member WHERE login_id = :loginId)";
		return jdbcTemplate.queryForObject(sql, Map.of("loginId", loginId), Boolean.class);
	}

	public Boolean existByIds(List<Long> ids) {
		String sql = "SELECT COUNT(*) FROM member WHERE id IN (:ids)";
		Integer count = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("ids", ids), Integer.class);
		return count != null && count.equals(ids.size());
	}

	public Boolean existById(Long id) {
		String sql = "SELECT EXISTS(SELECT 1 FROM member WHERE id =:id)";
		return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
			.addValue("id", id), Boolean.class);
	}
}
