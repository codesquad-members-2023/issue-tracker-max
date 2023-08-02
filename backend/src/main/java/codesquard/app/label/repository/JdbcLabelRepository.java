package codesquard.app.label.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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

		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("name", label.getName())
			.addValue("color", label.getColor().getNameToLowerCase())
			.addValue("background", label.getBackground())
			.addValue("description", label.getDescription());

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
	public void updateBy(Long labelId, Label label) {
		String sql = "UPDATE `label` " +
			"SET `name` = :name, `color` = :color, `background` = :background, `description` = :description " +
			"WHERE id = :id";

		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("name", label.getName())
			.addValue("color", label.getColor().getNameToLowerCase())
			.addValue("background", label.getBackground())
			.addValue("description", label.getDescription())
			.addValue("id", labelId);

		template.update(sql, param);
	}

	@Override
	public Long deleteById(Long id) {
		return null;
	}
}
