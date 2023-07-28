package codesquard.app.milestone.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquard.app.milestone.entity.Milestone;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JdbcMilestoneRepository implements MilestoneRepository {

	private final NamedParameterJdbcTemplate template;

	@Override
	public Long save(Milestone milestone) {
		return null;
	}

	@Override
	public List<Milestone> findAll() {
		return null;
	}

	@Override
	public Milestone findById(Long id) {
		return null;
	}

	@Override
	public Long modify(Milestone milestone) {
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		return null;
	}
}
