package com.issuetracker.unit.infrastrucure;

import static com.issuetracker.util.fixture.IssueFixture.ISSUE1;
import static com.issuetracker.util.fixture.MilestoneFixture.MILESTON3;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.IssueRepository;
import com.issuetracker.issue.domain.IssuesCountData;
import com.issuetracker.issue.infrastrucure.JdbcIssueRepository;
import com.issuetracker.util.JdbcRepositoryTest;

class IssueJdbcRepositoryTest extends JdbcRepositoryTest {

	private IssueRepository issueRepository;

	@Autowired
	public IssueJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.issueRepository = new JdbcIssueRepository(jdbcTemplate);
	}

	@Test
	void 이슈를_생성할_수_있다() {
		// given
		Issue issue = Issue.builder()
			.title("제목")
			.content("내용")
			.isOpen(true)
			.createAt(LocalDateTime.now())
			.milestoneId(1L)
			.authorId(1L)
			.build();

		// when
		Long issueId = issueRepository.save(issue);

		// then
		assertThat(issueId).isNotNull();
	}

	@Test
	void 열린_이슈_닫힌_이슈_라벨_마일스톤_개수를_조회한다() {
		// when
		IssuesCountData actual = issueRepository.findAllCount();

		// then
		assertThat(actual.getIssueOpenCount()).isEqualTo(6);
		assertThat(actual.getIssueCloseCount()).isEqualTo(6);
		assertThat(actual.getLabelCount()).isEqualTo(7);
		assertThat(actual.getMilestoneCount()).isEqualTo(4);
	}

	@Test
	void 이슈_열림_닫힘을_수정한다() {
		// when
		int actual = issueRepository.updateOpen(false, ISSUE1.getId());

		// then
		assertThat(actual).isEqualTo(1);
	}

	@Test
	void 이슈_제목을_수정한다() {
		// when
		int actual = issueRepository.updateTitle(ISSUE1.getId(), "수정한 제목");

		// then
		assertThat(actual).isEqualTo(1);
	}

	@Test
	void 이슈_내용을_수정한다() {
		// when
		int actual = issueRepository.updateContent(ISSUE1.getId(), "## 수정한 내용");

		// then
		assertThat(actual).isEqualTo(1);
	}

	@Test
	void 이슈_마일스톤을_수정한다() {
		// when
		int actual = issueRepository.updateMilestone(ISSUE1.getId(), MILESTON3.getId());

		// then
		assertThat(actual).isEqualTo(1);
	}

	@Test
	void 이슈를_삭제한다() {
		// when
		int actual = issueRepository.delete(ISSUE1.getId());

		// then
		assertThat(actual).isEqualTo(1);
	}

	@Test
	void 이슈_아이디를_입력하면_존재하는_이슈인지_확일할_수_있다() {
		// when
		boolean actual = issueRepository.existById(ISSUE1.getId());

		// then
		assertThat(actual).isTrue();
	}
}
