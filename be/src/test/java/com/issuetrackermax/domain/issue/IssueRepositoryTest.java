package com.issuetrackermax.domain.issue;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.issue.entity.Issue;
import com.issuetrackermax.domain.issue.entity.IssueResultVO;

@Transactional
public class IssueRepositoryTest extends IntegrationTestSupport {
	private IssueRepository issueRepository;
	private Issue issue;

	@Autowired
	public IssueRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.issueRepository = new IssueRepository(jdbcTemplate);
	}

	@BeforeEach
	void setup() {
		issue = Issue.builder()
			.title("새로운 이슈")
			.isOpen(true)
			.writerId(1L)
			.milestoneId(1L)
			.build();
	}

	@DisplayName("이슈 아이디를 통해 이슈를 찾을 수 있다.")
	@Test
	void findById() {
		//given
		Long issueId = issueRepository.save(issue);

		//when
		IssueResultVO issue1 = issueRepository.findIssueDetailsById(issueId);

		//then
		assertThat(issue1.getId()).isEqualTo(issueId);
	}

	@DisplayName("이슈를 저장하고 새로 생성된 이슈 아이디를 반환받는다.")
	@Test
	void save() {
		//when
		Long issueId = issueRepository.save(issue);

		//then
		assertThat(issueId).isNotNull();
	}

	@DisplayName("1개 이상의 이슈 아이디를 전달하여 이슈 상태를 열림으로 바꾼다.")
	@Test
	void openByIds() {
		//given
		Issue issue2 = Issue.builder()
			.title("두번째 이슈")
			.isOpen(true)
			.writerId(2L)
			.milestoneId(1L)
			.build();
		Long issue1Id = issueRepository.save(issue);
		Long issue2Id = issueRepository.save(issue2);
		List<Long> issueIds = Arrays.asList(issue1Id, issue2Id);

		//when
		int count = issueRepository.openByIds(issueIds);

		//then
		assertAll(
			() -> assertThat(count).isEqualTo(issueIds.size()),
			() -> assertThat(issue.getIsOpen()).isTrue(),
			() -> assertThat(issue2.getIsOpen()).isTrue()
		);
	}

	@DisplayName("1개 이상의 이슈 아이디를 전달하여 이슈 상태를 닫힘으로 바꾼다.")
	@Test
	void closedByIds() {
		//given
		Issue issue2 = Issue.builder()
			.title("두번째 이슈")
			.isOpen(true)
			.writerId(2L)
			.milestoneId(1L)
			.build();
		Long issue1Id = issueRepository.save(issue);
		Long issue2Id = issueRepository.save(issue2);
		List<Long> issueIds = Arrays.asList(issue1Id, issue2Id);

		//when
		int count = issueRepository.closeByIds(issueIds);
		IssueResultVO newIssue1 = issueRepository.findIssueDetailsById(issue1Id);
		IssueResultVO newIssue2 = issueRepository.findIssueDetailsById(issue2Id);

		//then
		assertAll(
			() -> assertThat(count).isEqualTo(issueIds.size()),
			() -> assertThat(newIssue1.getIsOpen()).isFalse(),
			() -> assertThat(newIssue2.getIsOpen()).isFalse()
		);
	}

	void deleteById() {

	}

	void modifyTitle() {

	}

	void applyLabels() {

	}

	void deleteAppliedLabels() {

	}

	void applyAssignees() {
	}

	void deleteAppliedAssignees() {

	}

	void applyMilestone() {

	}

	void getOpenIssue() {

	}

	void getClosedIssue() {

	}

	void existById() {

	}
}

