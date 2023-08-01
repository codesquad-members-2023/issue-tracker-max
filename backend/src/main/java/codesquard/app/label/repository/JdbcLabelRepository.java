package codesquard.app.label.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import codesquard.app.label.entity.Label;

@Repository
public class JdbcLabelRepository implements LabelRepository {

	private final NamedParameterJdbcTemplate template;

	public JdbcLabelRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	public Optional<Long> save(Label label) {
		String sql = "INSERT INTO `label` (`name`, `color`, `background`, `description`) "
			+ "VALUES (:name, :color, :background, :description)";

		// TODO: enum 타입은 자동으로 못 가져오니 하나씩 내가 직접 넣어줘야 할 듯
		// SqlParameterSource param = new BeanPropertySqlParameterSource(label);
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("name", label.getName());
		param.addValue("color", label.getColor().getNameToLowerCase());
		param.addValue("background", label.getBackground());
		param.addValue("description", label.getDescription());
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
