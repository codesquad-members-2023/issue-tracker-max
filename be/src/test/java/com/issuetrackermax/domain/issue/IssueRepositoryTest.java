package com.issuetrackermax.domain.issue;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
	private Long issueId;

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
		issueId = issueRepository.save(issue);
	}

	@DisplayName("이슈 아이디를 통해 이슈를 찾을 수 있다.")
	@Test
	void findById() {
		//when
		IssueResultVO issue1 = issueRepository.findIssueDetailsById(issueId);

		//then
		assertThat(issue1.getId()).isEqualTo(issueId);
	}

	@DisplayName("이슈를 저장하고 새로 생성된 이슈 아이디를 반환받는다.")
	@Test
	void save() {
		//then
		assertAll(
			() -> assertThat(issueId).isNotNull()
		);

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
		Long issue2Id = issueRepository.save(issue2);
		List<Long> issueIds = Arrays.asList(issueId, issue2Id);

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
		Long issue2Id = issueRepository.save(issue2);
		List<Long> issueIds = Arrays.asList(issueId, issue2Id);

		//when
		int count = issueRepository.closeByIds(issueIds);
		IssueResultVO newIssue1 = issueRepository.findIssueDetailsById(issueId);
		IssueResultVO newIssue2 = issueRepository.findIssueDetailsById(issue2Id);

		//then
		assertAll(
			() -> assertThat(count).isEqualTo(issueIds.size()),
			() -> assertThat(newIssue1.getIsOpen()).isFalse(),
			() -> assertThat(newIssue2.getIsOpen()).isFalse()
		);
	}

	@DisplayName("이슈를 삭제하고 1을 반환한다")
	@Test
	void deleteById() {
		//when
		int count = issueRepository.deleteById(issueId);

		//then
		assertAll(
			() -> assertThat(issueRepository.existById(issueId)).isFalse(),
			() -> assertThat(count).isEqualTo(1)
		);

	}

	@DisplayName("수정된 이슈 제목을 기존 이슈에 적용하고 1를 반환한다.")
	@Test
	void modifyTitle() {
		//given
		String modifiedTitle = "수정된 제목";

		//when
		int count = issueRepository.modifyTitle(issueId, modifiedTitle);
		IssueResultVO modifiedIssue = issueRepository.findIssueDetailsById(issueId);

		//then
		assertAll(
			() -> assertThat(modifiedIssue.getTitle()).isEqualTo(modifiedTitle),
			() -> assertThat(count).isEqualTo(1)
		);
	}

	void applyLabels() {

	}

	void deleteAppliedLabels() {

	}

	void applyAssignees() {
	}

	void deleteAppliedAssignees() {

	}

	@DisplayName("수정된 마일스톤 번호를 기존 이슈에 적용하고 1을 반환한다.")
	@Test
	void applyMilestone() {
		//given
		Long newMilestoneId = 2L;

		//when
		int count = issueRepository.applyMilestone(issueId, newMilestoneId);
		IssueResultVO modifiedIssue = issueRepository.findIssueDetailsById(issueId);

		//then
		assertAll(
			() -> assertThat(modifiedIssue.getMilestoneId()).isEqualTo(newMilestoneId),
			() -> assertThat(count).isEqualTo(1)
		);

	}

	@DisplayName("상태가 open인 이슈를 반환한다.")
	@Test
	void getOpenIssue() {
		//given
		Issue issue2 = Issue.builder()
			.title("열린 이슈")
			.isOpen(true)
			.writerId(2L)
			.milestoneId(1L)
			.build();
		Issue issue3 = Issue.builder()
			.title("닫힌 이슈")
			.isOpen(false)
			.writerId(2L)
			.milestoneId(1L)
			.build();
		Long issue2Id = issueRepository.save(issue2);
		Long issue3Id = issueRepository.save(issue3);

		//when
		List<Issue> openIssue = issueRepository.getOpenIssue();
		List<Long> openIssueIds = openIssue.stream()
			.map(Issue::getId)
			.collect(Collectors.toList());

		//then
		assertAll(
			() -> assertThat(openIssue.size()).isEqualTo(2),
			() -> assertThat(openIssueIds).asList().containsExactly(issueId, issue2Id)
		);

	}

	@DisplayName("상태가 closed인 이슈를 반환한다.")
	@Test
	void getClosedIssue() {
		//given
		Issue issue2 = Issue.builder()
			.title("열린 이슈")
			.isOpen(true)
			.writerId(2L)
			.milestoneId(1L)
			.build();
		Issue issue3 = Issue.builder()
			.title("닫힌 이슈")
			.isOpen(false)
			.writerId(2L)
			.milestoneId(1L)
			.build();
		Long issue2Id = issueRepository.save(issue2);
		Long issue3Id = issueRepository.save(issue3);

		//when
		List<Issue> closedIssue = issueRepository.getClosedIssue();
		List<Long> closedIssueIds = closedIssue.stream()
			.map(Issue::getId)
			.collect(Collectors.toList());

		//then
		assertAll(
			() -> assertThat(closedIssue.size()).isEqualTo(1),
			() -> assertThat(closedIssueIds).asList().containsExactly(issue3Id)
		);
	}

	void existById() {
		//when
		Boolean isExist = issueRepository.existById(issueId);

		//then
		assertThat(isExist).isTrue();
	}
}

