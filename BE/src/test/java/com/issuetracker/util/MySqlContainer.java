package com.issuetracker.util;

import org.testcontainers.containers.MySQLContainer;

public abstract class MySqlContainer {

	 private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:8.0.34")
		.withDatabaseName("test")
		.withUsername("root")
		.withPassword("root");

	 static {
		 MY_SQL_CONTAINER.start();
	 }
}
