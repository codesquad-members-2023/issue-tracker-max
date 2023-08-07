package com.issuetracker.unit.infrastrucure;

import static com.issuetracker.util.fixture.LabelFixture.LABEL1;
import static com.issuetracker.util.fixture.LabelFixture.LABEL5;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER1;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER4;
import static com.issuetracker.util.fixture.MilestoneFixture.MILESTON1;
import static com.issuetracker.util.fixture.MilestoneFixture.MILESTON3;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.issue.domain.IssueRead;
import com.issuetracker.issue.domain.IssueSearch;
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
		IssueSearch issueSearch = IssueSearch.builder()
			.isOpen(true)
			.assigneeIds(Collections.emptyList())
			.labelIds(List.of(LABEL5.getId()))
			.milestoneId(MILESTON1.getId())
			.authorId(MEMBER1.getId())
			.commentAuthorId(null)
			.build();

		// when
		List<IssueRead> issues = issueMapper.search(issueSearch);

		// then
		assertThat(issues).hasSize(2);
	}

	@Test
	void 조건에_맞지_않는_이슈_목록_조회() {
		// given
		IssueSearch issueSearch = IssueSearch.builder()
			.isOpen(false)
			.assigneeIds(Collections.emptyList())
			.labelIds(List.of(LABEL1.getId()))
			.milestoneId(MILESTON3.getId())
			.authorId(MEMBER4.getId())
			.commentAuthorId(null)
			.build();

		// when
		List<IssueRead> issues = issueMapper.search(issueSearch);

		// then
		assertThat(issues).isEmpty();
	}
}
