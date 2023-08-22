package com.issuetracker.unit.infrastrucure;

import static com.issuetracker.util.fixture.IssueFixture.ISSUE2;
import static com.issuetracker.util.fixture.LabelFixture.LABEL1;
import static com.issuetracker.util.fixture.LabelFixture.LABEL5;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER1;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER4;
import static com.issuetracker.util.fixture.MilestoneFixture.MILESTON1;
import static com.issuetracker.util.fixture.MilestoneFixture.MILESTON3;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.issue.domain.IssueDetailRead;
import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.issue.domain.IssueRead;
import com.issuetracker.issue.domain.IssueSearch;
import com.issuetracker.util.MyBatisRepositoryTest;
import com.issuetracker.util.fixture.IssueCommentFixture;
import com.issuetracker.util.fixture.LabelFixture;
import com.issuetracker.util.fixture.MemberFixture;

public class IssueMapperTestJdbc extends MyBatisRepositoryTest {

	@Autowired
	private IssueMapper issueMapper;

	@Autowired
	public IssueMapperTestJdbc(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Test
	void 이슈_목록_조회() {
		// given
		IssueSearch issueSearch = IssueSearch.builder()
			.isOpen(true)
			.assigneeNames(Collections.emptyList())
			.labelTitles(List.of(LABEL5.getTitle()))
			.milestoneTitle(MILESTON1.getTitle())
			.authorName(MEMBER1.getNickname())
			.build();


		// when
		List<IssueRead> actual = issueMapper.search(issueSearch);

		// then
		assertThat(actual).hasSize(2);
	}

	@Test
	void 조건에_맞지_않는_이슈_목록_조회() {
		// given
		IssueSearch issueSearch = IssueSearch.builder()
			.isOpen(false)
			.assigneeNames(Collections.emptyList())
			.labelTitles(List.of(LABEL1.getTitle()))
			.milestoneTitle(MILESTON3.getTitle())
			.authorName(MEMBER4.getNickname())
			.build();

		// when
		List<IssueRead> actual = issueMapper.search(issueSearch);

		// then
		assertThat(actual).isEmpty();
	}

	@Test
	void 이슈_상세_조회() {
		// when
		IssueDetailRead actual = issueMapper.findById(ISSUE2.getId());

		// then
		Assertions.assertAll(
			() -> assertThat(actual.getId()).isEqualTo(ISSUE2.getId()),
			() -> assertThat(actual.getTitle()).isEqualTo(ISSUE2.getTitle()),
			() -> assertThat(actual.getContent()).isEqualTo(ISSUE2.getContent()),
			() -> assertThat(actual.getIsOpen()).isEqualTo(ISSUE2.getOpen()),
			() -> assertThat(actual.getAuthor().getId()).isEqualTo(ISSUE2.getAuthorId()),
			() -> assertThat(actual.getMilestone().getId()).isEqualTo(ISSUE2.getMilestoneId()),
			() -> assertThat(actual.getLabels()).hasSize(LabelFixture.findByIssueId(ISSUE2.getId()).size()),
			() -> assertThat(actual.getAssignees()).hasSize(MemberFixture.findByIssueId(ISSUE2.getId()).size()),
			() -> assertThat(actual.getComments()).hasSize(IssueCommentFixture.findByIssueId(ISSUE2.getId()).size())
		);
	}
}
