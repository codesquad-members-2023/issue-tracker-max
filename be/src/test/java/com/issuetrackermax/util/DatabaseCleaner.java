package com.issuetrackermax.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {
	private static final String MYSQL_FK_REFERENCES = "SET FOREIGN_KEY_CHECKS = ";
	private static final String H2_FK_REFERENCES = "SET REFERENTIAL_INTEGRITY ";
	private static final String MYSQL = "MySQL";
	private static final String H2 = "H2";

	private final JdbcTemplate jdbcTemplate;
	private final List<String> tableNames;
	private final String driverName;

	@Autowired
	public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
		Connection con = null;
		try {
			this.jdbcTemplate = jdbcTemplate;
			con = jdbcTemplate.getDataSource().getConnection();
			this.tableNames = jdbcTemplate.query("Show tables", (rs, nums) -> rs.getString(1));
			this.driverName = con.getMetaData().getDatabaseProductName();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			connectionClose(con);
		}
	}

	private static void connectionClose(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional
	public void execute() {
		jdbcTemplate.execute(getFkReferencesSql(driverName, false));
		tableNames.forEach(name -> {
			jdbcTemplate.execute(String.format("TRUNCATE TABLE %s", name));

			if (driverName.equals(H2)) {
				jdbcTemplate.execute(String.format("ALTER TABLE %s ALTER COLUMN ID RESTART WITH 1", name));
			}
		});
		jdbcTemplate.execute(getFkReferencesSql(driverName, true));
	}

	private String getFkReferencesSql(String driverName, Boolean type) {
		return driverName.equals(MYSQL) ? MYSQL_FK_REFERENCES + (type ? "1" : "0")
			: H2_FK_REFERENCES + (type ? "TRUE" : "FALSE");
	}
}
