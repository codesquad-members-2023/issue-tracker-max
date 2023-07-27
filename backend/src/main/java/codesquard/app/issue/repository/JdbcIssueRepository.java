package codesquard.app.issue.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquard.app.issue.entity.Issue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JdbcIssueRepository implements IssueRepository {

	private final NamedParameterJdbcTemplate template;

	@Override
	public Long save(Issue issue) {
		return null;
	}

	@Override
	public List<Issue> findAll() {
		return null;
	}

	@Override
	public Issue findById(Long id) {
		return null;
	}

	@Override
	public Long modify(Issue issue) {
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		return null;
	}
}
