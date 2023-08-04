package com.issuetracker.issue.infrastrucure;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.Assignee;
import com.issuetracker.issue.domain.AssigneeRepository;
import com.issuetracker.member.domain.Member;

@Repository
public class JdbcAssigneeRepository implements AssigneeRepository {

	private static final String SAVE_ALL_SQL = "INSERT INTO assignee(issue_id, member_id) VALUES(:issueId, :memberId)";
	private static final String FIND_ALL_SQL = "SELECT DISTINCT member.id, member.nickname, member.profile_image_url FROM member INNER JOIN assignee ON member.id = assignee.member_id ORDER BY member.id";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcAssigneeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public int[] saveAll(List<Assignee> assignees) {
		BeanPropertySqlParameterSource[] params = assignees.stream()
			.map(BeanPropertySqlParameterSource::new)
			.toArray(BeanPropertySqlParameterSource[]::new);
		return jdbcTemplate.batchUpdate(SAVE_ALL_SQL, params);
	}

	@Override
	public List<Member> findAll() {
		return jdbcTemplate.query(FIND_ALL_SQL, MEMBER_ROW_MAPPER);
	}

	private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, rowNum) ->
		Member.builder()
			.id(rs.getLong("id"))
			.nickname(rs.getString("nickname"))
			.proFileImageUrl(rs.getString("profile_image_url"))
			.build();
}
