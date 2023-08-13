package com.issuetracker.issue.infrastrucure;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.assignee.Assignee;
import com.issuetracker.issue.domain.assignee.AssigneeRepository;
import com.issuetracker.member.domain.Member;

@Repository
public class JdbcAssigneeRepository implements AssigneeRepository {

	private static final String SAVE_SQL = "INSERT INTO assignee(issue_id, member_id) VALUES(:issueId, :memberId)";
	private static final String DELETE_SQL = "DELETE FROM assignee WHERE id = :id";
	private static final String FIND_ALL_SQL = "SELECT DISTINCT member.id, member.nickname, member.profile_image_url FROM member INNER JOIN assignee ON member.id = assignee.member_id ORDER BY member.id";
	private static final String FIND_ALL_ASSIGNED_TO_ISSUE
		= "SELECT member.id, member.nickname, member.profile_image_url "
		+ "FROM assignee "
		+ "LEFT JOIN member "
		+ "ON assignee.member_id = member.id "
		+ "WHERE assignee.issue_id = :issueId";
	private static final String FIND_ALL_UNASSIGNED_TO_ISSUE
		= "SELECT member.id, member.nickname, member.profile_image_url "
		+ "FROM member "
		+ "WHERE member.id NOT IN( "
		+ "    SELECT assignee.member_id "
		+ "    FROM assignee "
		+ "    WHERE assignee.issue_id = :issueId)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcAssigneeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public long save(Assignee assignee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(assignee);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SAVE_SQL, param, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public int[] saveAll(List<Assignee> assignees) {
		BeanPropertySqlParameterSource[] params = assignees.stream()
			.map(BeanPropertySqlParameterSource::new)
			.toArray(BeanPropertySqlParameterSource[]::new);
		return jdbcTemplate.batchUpdate(SAVE_SQL, params);
	}

	@Override
	public List<Member> findAll() {
		return jdbcTemplate.query(FIND_ALL_SQL, MEMBER_ROW_MAPPER);
	}

	@Override
	public List<Member> findAllAssignedToIssue(long issueId) {
		return jdbcTemplate.query(FIND_ALL_ASSIGNED_TO_ISSUE, Map.of("issueId", issueId), MEMBER_ROW_MAPPER);
	}

	@Override
	public List<Member> findAllUnassignedToIssue(long issueId) {
		return jdbcTemplate.query(FIND_ALL_UNASSIGNED_TO_ISSUE, Map.of("issueId", issueId), MEMBER_ROW_MAPPER);
	}

	@Override
	public int delete(Long id) {
		return jdbcTemplate.update(DELETE_SQL, Map.of("id", id));
	}

	private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, rowNum) ->
		Member.builder()
			.id(rs.getLong("id"))
			.nickname(rs.getString("nickname"))
			.profileImageUrl(rs.getString("profile_image_url"))
			.build();
}
