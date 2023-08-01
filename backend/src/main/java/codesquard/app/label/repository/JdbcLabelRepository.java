package codesquard.app.label.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquard.app.label.entity.Label;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JdbcLabelRepository implements LabelRepository {

	private final NamedParameterJdbcTemplate template;

	@Override
	public Optional<Long> save(Label label) {
		String sql = "INSERT INTO `label` (`name`, `color`, `background`, `description`) "
			+ "VALUES (:name, :color, :background, :description)";

		SqlParameterSource param = new BeanPropertySqlParameterSource(label);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		template.update(sql, param, keyHolder);

		return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
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
