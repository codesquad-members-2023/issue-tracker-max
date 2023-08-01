package com.issuetracker.unit;

import static com.issuetracker.util.LabelFixture.LABEL1;
import static com.issuetracker.util.LabelFixture.LABEL5;
import static com.issuetracker.util.MemberFixture.USER1;
import static com.issuetracker.util.MemberFixture.USER4;
import static com.issuetracker.util.MilestoneFixture.MILESTONR1;
import static com.issuetracker.util.MilestoneFixture.MILESTONR3;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.issue.application.dto.IssueSearchInputData;
import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.util.DatabaseInitialization;
import com.issuetracker.util.MyBatisMapperTest;

@MyBatisMapperTest
public class IssueMapperTest {

	@Autowired
	private IssueMapper issueMapper;

	private DatabaseInitialization databaseInitialization;

	@Autowired
	public IssueMapperTest(JdbcTemplate jdbcTemplate) {
		this.databaseInitialization = new DatabaseInitialization(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseInitialization.initialization();
		databaseInitialization.loadData();
	}

	@Test
	void 이슈_목록_조회() {
		// given
		IssueSearchInputData issueSearchData = new IssueSearchInputData(
			true,
			null,
			List.of(LABEL5.getId()),
			MILESTONR1.getId(),
			USER1.getId(),
			null
		);

		// when
		List<Issue> issues = issueMapper.search(issueSearchData);

		// then
		assertThat(issues).hasSize(2);
	}

	@Test
	void 조건에_맞지_않는_이슈_목록_조회() {
		// given
		IssueSearchInputData issueSearchData = new IssueSearchInputData(
			false,
			null,
			List.of(LABEL1.getId()),
			MILESTONR3.getId(),
			USER4.getId(),
			null
		);

		// when
		List<Issue> issues = issueMapper.search(issueSearchData);

		// then
		assertThat(issues).isEmpty();
	}
}
