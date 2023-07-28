package codesquard.app.label.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquard.app.label.entity.Label;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JdbcLabelRepository implements LabelRepository {

	private final NamedParameterJdbcTemplate template;

	@Override
	public Long save(Label label) {
		return null;
	}

	@Override
	public List<Label> findAll() {
		return null;
	}

	@Override
	public Label findById(Long id) {
		return null;
	}

	@Override
	public Long modify(Label label) {
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		return null;
	}
}
