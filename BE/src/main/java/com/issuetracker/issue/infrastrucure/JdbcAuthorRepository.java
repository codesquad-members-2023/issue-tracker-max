package com.issuetracker.issue.infrastrucure;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.Author;
import com.issuetracker.issue.domain.AuthorRepository;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

	private static final String SEARCH_SQL = "SELECT DISTINCT member.id, member.nickname, member.profile_image_url "
		+ "FROM issue "
		+ "JOIN member ON issue.author_id = member.id";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcAuthorRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public List<Author> search() {

		RowMapper<Author> authorRowMapper =
			(rs, rowNum) ->
				Author.builder()
					.id(rs.getLong("id"))
					.nickname(rs.getString("nickname"))
					.profileImageUrl(rs.getString("profile_image_url"))
					.build();

		return jdbcTemplate.query(SEARCH_SQL, authorRowMapper);
	}
}
