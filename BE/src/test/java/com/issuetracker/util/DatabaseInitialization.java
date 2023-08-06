package com.issuetracker.util;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.util.fixture.AssigneeFixture;
import com.issuetracker.util.fixture.IssueCommentFixture;
import com.issuetracker.util.fixture.IssueFixture;
import com.issuetracker.util.fixture.IssueLabelMappingFixture;
import com.issuetracker.util.fixture.LabelFixture;
import com.issuetracker.util.fixture.MemberFixture;
import com.issuetracker.util.fixture.MilestoneFixture;

@Component
public class DatabaseInitialization {
	private final JdbcTemplate jdbcTemplate;
	private final List<String> tableNames;

	public DatabaseInitialization(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.tableNames = jdbcTemplate.query("Show tables", (rs, nums) -> rs.getString(1));
	}

	@Transactional
	public void initialization() {
		jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
		tableNames.forEach(name -> {
			jdbcTemplate.execute(String.format("TRUNCATE TABLE %s", name));
			jdbcTemplate.execute(String.format("ALTER TABLE %s AUTO_INCREMENT = 1", name));
		});
		jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
	}

	@Transactional
	public void loadData() {
		jdbcTemplate.update(MemberFixture.createInsertSQL());
		jdbcTemplate.update(LabelFixture.createInsertSQL());
		jdbcTemplate.update(MilestoneFixture.createInsertSQL());
		jdbcTemplate.update(IssueFixture.createInsertSQL());
		jdbcTemplate.update(IssueCommentFixture.createInsertSQL());
		jdbcTemplate.update(AssigneeFixture.createInsertSQL());
		jdbcTemplate.update(IssueLabelMappingFixture.createInsertSQL());
	}
}
