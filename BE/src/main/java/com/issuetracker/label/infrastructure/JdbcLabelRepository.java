package com.issuetracker.label.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.label.domain.Label;
import com.issuetracker.label.domain.LabelCountMetadata;
import com.issuetracker.label.domain.LabelRepository;

@Repository
public class JdbcLabelRepository implements LabelRepository {

	private static final String EXIST_BY_ID_SQL = "SELECT EXISTS(SELECT 1 FROM label WHERE id = :id AND is_deleted = false)";
	private static final String EXIST_BY_TITLE_SQL = "SELECT EXISTS(SELECT 1 FROM label WHERE is_deleted = false AND title = :title)";
	private static final String EXIST_BY_IDS_SQL = "SELECT IF(COUNT(id) = :size, TRUE, FALSE) FROM label WHERE is_deleted = false AND id IN(:labelIds)";
	private static final String SAVE_SQL = "INSERT INTO label(title, description, background_color, text_color) VALUE(:title, :description, :backgroundColor, :textColor)";
	private static final String UPDATE_SQL = "UPDATE label SET title = :title, description = :description, background_color = :backgroundColor, text_color =:textColor WHERE id = :id";
	private static final String DELETE_SQL = "UPDATE label SET is_deleted = true WHERE id = :id";
	private static final String FIND_ALL_SQL = "SELECT id, title, description, background_color, text_color FROM label WHERE is_deleted = false ORDER BY id desc";
	private static final String FIND_ALL_SEARCH_SQL = "SELECT id, title, background_color, text_color, description FROM label WHERE label.is_deleted = 0 ORDER BY title";
	private static final String CALCULATE_COUNT_METADATA
		= "SELECT "
		+ "    (SELECT COUNT(id) FROM label WHERE is_deleted = false) AS total_label_count, "
		+ "    (SELECT COUNT(id) FROM milestone WHERE is_deleted = false) AS total_milestone_count";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcLabelRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public boolean existById(Long id) {
		return jdbcTemplate.queryForObject(EXIST_BY_ID_SQL, Map.of("id", id), Boolean.class);
	}

	@Override
	public boolean existByIds(List<Long> labelIds) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("labelIds", labelIds)
			.addValue("size", labelIds.size());
		return jdbcTemplate.queryForObject(EXIST_BY_IDS_SQL, params, Boolean.class);
	}

	@Override
	public boolean existsByTitle(String title) {
		return jdbcTemplate.queryForObject(EXIST_BY_TITLE_SQL, Map.of("title", title), Boolean.class);
	}

	@Override
	public Long save(Label label) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(label);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(SAVE_SQL, param, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public int update(Label label) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(label);
		return jdbcTemplate.update(UPDATE_SQL, param);
	}

	@Override
	public int delete(Label label) {
		Map<String, Long> param = Map.of("id", label.getId());

		return jdbcTemplate.update(DELETE_SQL, param);
	}

	@Override
	public List<Label> findAll() {
		return jdbcTemplate.query(FIND_ALL_SQL, LABEL_ROW_MAPPER);
	}

	@Override
	public List<Label> searchOrderByTitle() {
		return jdbcTemplate.query(FIND_ALL_SEARCH_SQL, LABEL_ROW_MAPPER);
	}

	@Override
	public LabelCountMetadata calculateMetadata() {
		return jdbcTemplate.queryForObject(CALCULATE_COUNT_METADATA, Map.of(), LABEL_METADATA_MAPPER);
	}

	private static final RowMapper<Label> LABEL_ROW_MAPPER = (rs, rowNum) ->
		Label.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.description(rs.getString("description"))
			.backgroundColor(rs.getString("background_color"))
			.textColor(rs.getString("text_color"))
			.build();

	private static final RowMapper<LabelCountMetadata> LABEL_METADATA_MAPPER = (rs, rowNum) ->
		LabelCountMetadata.builder()
			.totalLabelCount(rs.getInt("total_label_count"))
			.totalMilestoneCount(rs.getInt("total_milestone_count"))
			.build();
}
