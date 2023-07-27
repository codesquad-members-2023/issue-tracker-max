package kr.codesquad.issuetracker.acceptance;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseInitializer {

	private static final String TRUNCATE_QUERY = "TRUNCATE TABLE %s";
	private static final String AUTO_INCREMENT_INIT_QUERY = "ALTER TABLE %s AUTO_INCREMENT = 1";

	@Autowired
	private DataSource dataSource;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	private final List<String> tableNames = new ArrayList<>();

	@PostConstruct
	public void afterConstruct() {
		try {
			DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
			ResultSet tables = metaData.getTables(null, null, null, new String[] {"TABLE"});

			while (tables.next()) {
				String tableName = tables.getString("TABLE_NAME");
				tableNames.add(tableName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public void initTables() {
		jdbcTemplate.update(
			"INSERT INTO user_account(login_id, password, profile_url) VALUES('iambruni', '312433c28349f63c4f387953ff337046e794bea0f9b9ebfcb08e90046ded9c76', 'default_url')",
			Map.of());
	}

	@Transactional
	public void truncateTables() {
		for (String tableName : tableNames) {
			truncateTable(tableName);
		}
	}

	private void truncateTable(final String tableName) {
		jdbcTemplate.update(String.format(TRUNCATE_QUERY, tableName), Map.of());
		jdbcTemplate.update(String.format(AUTO_INCREMENT_INIT_QUERY, tableName), Map.of());
	}
}
