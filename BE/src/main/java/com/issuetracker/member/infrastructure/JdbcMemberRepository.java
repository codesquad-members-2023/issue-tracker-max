package com.issuetracker.member.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.issuetracker.member.domain.Member;
import com.issuetracker.member.domain.MemberRepository;

@Repository
public class JdbcMemberRepository implements MemberRepository {

	private static final String EXIST_BY_ID_SQL = "SELECT EXISTS(SELECT 1 FROM member WHERE id = :id)";
	private static final String EXIST_BY_IDS_SQL = "SELECT IF(COUNT(id) = :size, TRUE, FALSE) FROM member WHERE id IN(:memberIds)";
	private static final String SEARCH_SQL = "SELECT DISTINCT member.id, member.nickname, member.profile_image_url FROM issue JOIN member ON issue.author_id = member.id";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public boolean existById(Long id) {
		return jdbcTemplate.queryForObject(EXIST_BY_ID_SQL, Map.of("id", id), Boolean.class);
	}

	@Override
	public boolean existByIds(List<Long> Ids) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberIds", Ids)
			.addValue("size", Ids.size());
		return jdbcTemplate.queryForObject(EXIST_BY_IDS_SQL, params, Boolean.class);
	}

	@Override
	public List<Member> search() {
		return jdbcTemplate.query(SEARCH_SQL, AUTHOR_ROW_MAPPER);
	}

	private static final RowMapper<Member> AUTHOR_ROW_MAPPER = (rs, rowNum) ->
		Member.builder()
			.id(rs.getLong("id"))
			.nickname(rs.getString("nickname"))
			.profileImageUrl(rs.getString("profile_image_url"))
			.build();
}
