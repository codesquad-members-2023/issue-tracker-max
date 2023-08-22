package com.issuetracker.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.util.fixture.AssigneeFixture;
import com.issuetracker.util.fixture.IssueCommentFixture;
import com.issuetracker.util.fixture.IssueFixture;
import com.issuetracker.util.fixture.AssignedLabelFixture;
import com.issuetracker.util.fixture.LabelFixture;
import com.issuetracker.util.fixture.MemberFixture;
import com.issuetracker.util.fixture.MilestoneFixture;

@Component
public class DatabaseLoader {
	private final JdbcTemplate jdbcTemplate;

	public DatabaseLoader(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void loadData() {
		jdbcTemplate.update(MemberFixture.createInsertSQL());
		jdbcTemplate.update(LabelFixture.createInsertSQL());
		jdbcTemplate.update(MilestoneFixture.createInsertSQL());
		jdbcTemplate.update(IssueFixture.createInsertSQL());
		jdbcTemplate.update(IssueCommentFixture.createInsertSQL());
		jdbcTemplate.update(AssigneeFixture.createInsertSQL());
		jdbcTemplate.update(AssignedLabelFixture.createInsertSQL());
	}
}
