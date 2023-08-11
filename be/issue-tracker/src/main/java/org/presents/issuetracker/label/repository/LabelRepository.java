package org.presents.issuetracker.label.repository;

import java.util.List;

import javax.sql.DataSource;

import org.presents.issuetracker.label.entity.Label;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class LabelRepository {

    private static final int OPEN_FLAG = 0;
    private static final int DELETED_FLAG = 1;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public LabelRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("label")
            .usingColumns("name", "description", "background_color", "text_color")
            .usingGeneratedKeyColumns("label_id");
    }

    public Long save(final Label label) {
        return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(label)).longValue();
    }

    public void update(final Label label) {
        String sql = "UPDATE label " +
            "SET name = :name, " +
            "    description = :description, " +
            "    background_color = :background_color, " +
            "    text_color = :text_color " +
            "WHERE label_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", label.getName())
            .addValue("description", label.getDescription())
            .addValue("background_color", label.getBackgroundColor())
            .addValue("text_color", label.getTextColor())
            .addValue("id", label.getId());

        jdbcTemplate.update(sql, params);
    }

    public void deleteById(Long id) {
        String sql = "UPDATE label SET is_deleted = :deletedFlag WHERE label_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("deletedFlag", DELETED_FLAG)
            .addValue("id", id);

        jdbcTemplate.update(sql, params);
    }

    public Label findById(final Long labelId) {
        String sql = "SELECT * FROM label WHERE label_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource("id", labelId);

        RowMapper<Label> mapper = (rs, rowNum) -> Label.of(
            rs.getLong("label_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getString("background_color"),
            rs.getString("text_color")
        );

        return jdbcTemplate.queryForObject(sql, params, mapper);
    }

    public List<Label> findAll() {
        String sql = "SELECT label_id, name, description, background_color, text_color " +
            "FROM label " +
            "WHERE is_deleted = :openFlag " +
            "ORDER BY label_id";

        MapSqlParameterSource params = new MapSqlParameterSource("openFlag", OPEN_FLAG);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            long id = rs.getLong("label_id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            String backgroundColor = rs.getString("background_color");
            String textColor = rs.getString("text_color");
            return Label.of(id, name, description, backgroundColor, textColor);
        });
    }

    public List<Label> findPreviews() {
        String sql = "SELECT label_id, name, background_color, text_color " +
            "FROM label " +
            "WHERE is_deleted = :openFlag " +
            "ORDER BY label_id";

        MapSqlParameterSource params = new MapSqlParameterSource("openFlag", OPEN_FLAG);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            long id = rs.getLong("label_id");
            String name = rs.getString("name");
            String backgroundColor = rs.getString("background_color");
            String textColor = rs.getString("text_color");
            return Label.of(id, name, backgroundColor, textColor);
        });
    }

    public List<Label> findByIssueId(Long issueId) {
        String sql = "SELECT l.label_id, l.name, l.background_color, l.text_color "
            + "FROM label l JOIN issue_label il ON l.label_id = il.label_id "
            + "WHERE il.issue_id = :issueId";

        MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            long id = rs.getLong("label_id");
            String name = rs.getString("name");
            String backgroundColor = rs.getString("background_color");
            String textColor = rs.getString("text_color");
            return Label.of(id, name, backgroundColor, textColor);
        });
    }
}
