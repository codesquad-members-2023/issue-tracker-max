package codesquard.app.label.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
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

	public JdbcLabelRepository(final NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	public Optional<Long> save(final Label label) {
		String sql = "INSERT INTO `label` (`name`, `color`, `background`, `description`) "
			+ "VALUES (:name, :color, :background, :description)";
		SqlParameterSource param = Label.makeParam(label);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		template.update(sql, param, keyHolder);

		return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
	}

	@Override
	public List<Label> findAll() {
		String sql = "SELECT `id`, `name`, `color`, `background`, `description` " +
			"FROM `label`";

		return Optional.of(template.query(sql, labelRowMapper())).orElse(Collections.emptyList());
	}

	private RowMapper<Label> labelRowMapper() {
		return (rs, rowNum) -> {
			Long id = rs.getLong("id");
			String name = rs.getString("name");
			String color = rs.getString("color");
			String background = rs.getString("background");
			String description = rs.getString("description");

			return new Label(id, name, color, background, description);
		};
	}

	@Override
	public void updateBy(final Long labelId, final Label label) {
		String sql = "UPDATE `label` " +
			"SET `name` = :name, `color` = :color, `background` = :background, `description` = :description " +
			"WHERE `id` = :id";
		SqlParameterSource param = Label.makeParam(labelId, label);

		template.update(sql, param);
	}

	@Override
	public void deleteBy(final Long labelId) {
		String sql = "DELETE FROM `label` " +
			"WHERE `id` = :id";

		template.update(sql, Map.of("id", labelId));
	}
}
