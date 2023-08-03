package org.presents.issuetracker.label.repository;

import org.presents.issuetracker.label.entity.Label;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class LabelRepository {

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

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", label.getName());
        params.addValue("description", label.getDescription());
        params.addValue("background_color", label.getBackgroundColor());
        params.addValue("text_color", label.getTextColor());
        params.addValue("id", label.getId());

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
        String sql = "SELECT label_id, name, background_color, text_color FROM label " +
                "UNION ALL " +
                "SELECT 0 AS label_id, 'none' AS name, '' AS background_color, '' AS label " +
                "ORDER BY label_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("label_id");
            String name = rs.getString("name");
            String backgroundColor = rs.getString("background_color");
            String textColor = rs.getString("text_color");
            return Label.of(id, name, backgroundColor, textColor);
        });
    }
}
