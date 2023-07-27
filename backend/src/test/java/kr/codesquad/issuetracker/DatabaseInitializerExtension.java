package kr.codesquad.issuetracker;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.codesquad.issuetracker.acceptance.DatabaseInitializer;

public class DatabaseInitializerExtension implements AfterEachCallback {

	@Override
	public void afterEach(ExtensionContext context) {
		DatabaseInitializer databaseInitializer = (DatabaseInitializer)SpringExtension
			.getApplicationContext(context).getBean("databaseInitializer");
		databaseInitializer.truncateTables();
	}
}
