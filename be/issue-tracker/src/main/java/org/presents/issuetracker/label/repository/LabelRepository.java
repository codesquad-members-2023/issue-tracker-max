package org.presents.issuetracker.label.repository;

import org.presents.issuetracker.label.entity.Label;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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
}
