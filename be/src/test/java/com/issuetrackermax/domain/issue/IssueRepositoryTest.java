package com.issuetrackermax.domain.issue;

import static com.issuetrackermax.fixture.EntityFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.assignee.AssigneeRepository;
import com.issuetrackermax.domain.assignee.entity.Assignee;
import com.issuetrackermax.domain.issue.entity.Issue;
import com.issuetrackermax.domain.issue.entity.IssueResultVO;
import com.issuetrackermax.domain.label.LabelRepository;
import com.issuetrackermax.domain.label.entity.Label;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;
import com.issuetrackermax.domain.milestone.MilestoneRepository;
import com.issuetrackermax.domain.milestone.entity.Milestone;

@Transactional
public class IssueRepositoryTest extends IntegrationTestSupport {
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private IssueLabelRepository issueLabelRepository;
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private AssigneeRepository assigneeRepository;
	@Autowired
	private MilestoneRepository milestoneRepository;
	@Autowired
	private MemberRepository memberRepository;
	private Issue issue;
	private Long issueId;

	@BeforeEach
	void setup() {
		Boolean isOpen = true;
		String title = "새로운 이슈";
		Long milestoneId = 1L;
		Long writerId = 1L;
		issue = makeIssue(isOpen, title, milestoneId, writerId);
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
		Boolean isOpen = true;
		String title = "두번째 이슈";
		Long milestoneId = 1L;
		Long writerId = 2L;

		Issue issue2 = makeIssue(isOpen, title, milestoneId, writerId);
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
		Boolean isOpen = true;
		String title = "두번째 이슈";
		Long milestoneId = 1L;
		Long writerId = 2L;

		Issue issue2 = makeIssue(isOpen, title, milestoneId, writerId);
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

	@DisplayName("수정된 라벨을 기존 이슈에 적용하고 1을 반환한다.")
	@Test
	void applyLabels() {
		//given
		String title = "새로운 라벨";
		String description = "라벨 설명";
		String textColor = "color";
		String backgroundColor = "anotherColor";

		Label label = makeLabel(title, description, textColor, backgroundColor);
		Long labelId = labelRepository.save(label);

		//when
		int count = issueLabelRepository.applyLabels(issueId, labelId);
		IssueResultVO modifiedIssue = issueRepository.findIssueDetailsById(issueId);

		//then
		assertAll(
			() -> assertThat(modifiedIssue.getLabelIds()).isEqualTo(String.valueOf(labelId)),
			() -> assertThat(count).isEqualTo(1)
		);
	}

	@DisplayName("수정된 담당자를 기존 이슈에 적용하고 1을 반환한다.")
	@Test
	void applyAssignees() {
		//given
		String loginId = "junejune";
		String password = "12345678";
		String nickname = "june";
		LoginType loginType = LoginType.LOCAL;

		Member member = makeMember(loginId, password, nickname, loginType);
		Long memberId = memberRepository.save(member);
		Assignee assignee = makeAssignee(issueId, memberId);
		Long assigneeId = assigneeRepository.save(assignee);

		//when
		int count = assigneeRepository.applyAssigneesToIssue(issueId, assigneeId);
		IssueResultVO modifiedIssue = issueRepository.findIssueDetailsById(issueId);

		//then
		assertAll(
			() -> assertThat(modifiedIssue.getAssigneeIds()).isEqualTo(String.valueOf(assigneeId)),
			() -> assertThat(count).isEqualTo(1)
		);
	}

	@DisplayName("수정된 마일스톤 번호를 기존 이슈에 적용하고 1을 반환한다.")
	@Test
	void applyMilestone() {
		//given
		Boolean isOpen = true;
		String title = "마일스톤";
		String description = "설명";

		Milestone milestone = makeMilestone(isOpen, title, description);
		Long milestoneId = milestoneRepository.save(milestone);

		//when
		int count = milestoneRepository.applyMilestoneToIssue(issueId, milestoneId);
		IssueResultVO modifiedIssue = issueRepository.findIssueDetailsById(issueId);

		//then
		assertAll(
			() -> assertThat(modifiedIssue.getMilestoneId()).isEqualTo(milestoneId),
			() -> assertThat(count).isEqualTo(1)
		);

	}

	@DisplayName("상태가 open인 이슈를 반환한다.")
	@Test
	void getOpenIssue() {
		//given
		Boolean isOpen2 = true;
		String title2 = "두번째 이슈";
		Long milestoneId2 = 1L;
		Long writerId2 = 2L;

		Boolean isOpen3 = false;
		String title3 = "세번째 이슈";
		Long milestoneId3 = 1L;
		Long writerId3 = 2L;

		Issue issue2 = makeIssue(isOpen2, title2, milestoneId2, writerId2);
		Issue issue3 = makeIssue(isOpen3, title3, milestoneId3, writerId3);
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
		Boolean isOpen2 = true;
		String title2 = "두번째 이슈";
		Long milestoneId2 = 1L;
		Long writerId2 = 2L;

		Boolean isOpen3 = false;
		String title3 = "세번째 이슈";
		Long milestoneId3 = 1L;
		Long writerId3 = 2L;

		Issue issue2 = makeIssue(isOpen2, title2, milestoneId2, writerId2);
		Issue issue3 = makeIssue(isOpen3, title3, milestoneId3, writerId3);
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

	@DisplayName("이슈 아이디가 존재하는지 검사한다.")
	@Test
	void existById() {
		//given
		Long invalidId = 7000L;

		//when
		Boolean isExist = issueRepository.existById(issueId);
		Boolean isExist2 = issueRepository.existById(invalidId);

		//then
		assertAll(
			() -> assertThat(isExist).isTrue(),
			() -> assertThat(isExist2).isFalse()
		);
	}
}
