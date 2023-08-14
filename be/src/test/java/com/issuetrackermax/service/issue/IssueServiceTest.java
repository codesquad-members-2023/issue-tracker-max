package com.issuetrackermax.service.issue;

import static com.issuetrackermax.fixture.EntityFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.IssueException;
import com.issuetrackermax.common.exception.domain.LabelException;
import com.issuetrackermax.common.exception.domain.MemberException;
import com.issuetrackermax.common.exception.domain.MilestoneException;
import com.issuetrackermax.controller.filter.dto.response.AssigneeResponse;
import com.issuetrackermax.controller.filter.dto.response.LabelResponse;
import com.issuetrackermax.controller.issue.dto.request.IssueApplyRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuePostRequest;
import com.issuetrackermax.controller.issue.dto.request.IssueTitleRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuesStatusRequest;
import com.issuetrackermax.controller.issue.dto.response.IssueDetailsResponse;
import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.label.LabelRepository;
import com.issuetrackermax.domain.label.entity.Label;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;
import com.issuetrackermax.domain.milestone.MilestoneRepository;
import com.issuetrackermax.domain.milestone.entity.Milestone;
import com.issuetrackermax.util.DatabaseCleaner;

@Transactional
public class IssueServiceTest extends IntegrationTestSupport {
	@Autowired
	private DatabaseCleaner databaseCleaner;
	@Autowired
	private IssueService issueService;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private MilestoneRepository milestoneRepository;
	@Autowired
	private MemberRepository memberRepository;
	private Long labelId;
	private Long memberId;
	private Long memberId2;
	private Long milestoneId;
	private Long issueId;

	@AfterEach
	void cleaner() {
		databaseCleaner.execute();
	}

	@BeforeEach
	void setup() {
		String labelTitle = "새로운 라벨";
		String labelDescription = "라벨 설명";
		String textColor = "color";
		String backgroundColor = "anotherColor";

		Label label = makeLabel(labelTitle, labelDescription, textColor, backgroundColor);
		labelId = labelRepository.save(label);

		String loginId = "junejune";
		String password = "12345678";
		String nickname = "june";

		String loginId2 = "moviemovie";
		String password2 = "12345678";
		String nickname2 = "movie";

		LoginType loginType = LoginType.LOCAL;

		Member member = makeMember(loginId, password, nickname, loginType);
		Member member2 = makeMember(loginId2, password2, nickname2, loginType);
		memberId = memberRepository.save(member);
		memberId2 = memberRepository.save(member2);

		Boolean milestoneIsOpen = true;
		String milestoneTitle = "마일스톤";
		String milestoneDescription = "설명";

		Milestone milestone = makeMilestone(milestoneIsOpen, milestoneTitle, milestoneDescription);
		milestoneId = milestoneRepository.save(milestone);

		IssuePostRequest request = IssuePostRequest.builder()
			.title("title")
			.content("content")
			.assigneeIds(Arrays.asList(memberId, memberId2))
			.labelIds(Arrays.asList(labelId))
			.milestoneId(milestoneId)
			.build();

		issueId = issueService.post(request, memberId).getId();
	}

	@DisplayName("새로운 이슈를 등록하고 id를 반환한다.")
	@Test()
	void post() {
		//then
		assertThat(issueId).isNotNull();
	}

	@DisplayName("이슈 id에 해당하는 이슈 상세 정보를 가져온다.")
	@Test
	void show() {
		//when
		IssueDetailsResponse issueDetailsResponse = issueService.show(issueId);

		//then
		assertAll(
			() -> assertThat(issueDetailsResponse.getId()).isEqualTo(issueId),
			() -> assertThat(issueDetailsResponse.getIsOpen()).isTrue(),
			() -> assertThat(issueDetailsResponse.getTitle()).isEqualTo("title"),
			() -> assertThat(issueDetailsResponse.getLabels()
				.stream()
				.map(LabelResponse::getId)
				.collect(Collectors.toList()))
				.asList()
				.containsExactly(labelId),
			() -> assertThat(issueDetailsResponse.getAssignees()
				.stream()
				.map(AssigneeResponse::getId)
				.collect(Collectors.toList()))
				.asList()
				.containsExactly(memberId, memberId2),
			() -> assertThat(issueDetailsResponse.getWriter().getId()).isEqualTo(memberId),
			() -> assertThat(issueDetailsResponse.getMilestone().getId()).isEqualTo(milestoneId)
		);
	}

	@DisplayName("유효하지 않는 이슈 id 조회 시 예외를 발생한다.")
	@Test
	void showAndThrowException() {
		//given
		Long invalidIssueId = 7000L;

		//when & then
		assertThatThrownBy(() -> issueService.show(invalidIssueId))
			.isInstanceOf(ApiException.class)
			.hasMessageContaining(IssueException.NOT_FOUND_ISSUE.getMessage());
	}

	@DisplayName("이슈 id에 해당하는 이슈를 삭제한다.")
	@Test
	void delete() {
		//when
		issueService.delete(issueId);

		//then
		assertThatThrownBy(() -> issueService.show(issueId))
			.isInstanceOf(ApiException.class)
			.hasMessageContaining(IssueException.NOT_FOUND_ISSUE.getMessage());
	}

	@DisplayName("이슈 id에 해당하는 이슈의 상태를 open으로 변경한다.")
	@Test
	void updateStatusOpen() {
		//given
		IssuePostRequest issuePostRequest = IssuePostRequest.builder()
			.title("title2")
			.content("content2")
			.assigneeIds(Arrays.asList(memberId2))
			.labelIds(Arrays.asList(labelId))
			.milestoneId(milestoneId)
			.build();

		Long issueId2 = issueService.post(issuePostRequest, memberId2).getId();
		List<Long> ids = Arrays.asList(issueId, issueId2);
		issueRepository.openByIds(ids);

		IssuesStatusRequest request = IssuesStatusRequest.builder()
			.issueIds(ids)
			.issueStatus("open")
			.build();

		//when
		issueService.updateStatus(request, memberId2);
		IssueDetailsResponse modifiedIssue1 = issueService.show(issueId);
		IssueDetailsResponse modifiedIssue2 = issueService.show(issueId2);

		//then
		assertAll(
			() -> assertThat(modifiedIssue1.getIsOpen()).isTrue(),
			() -> assertThat(modifiedIssue2.getIsOpen()).isTrue()
		);
	}

	@DisplayName("이슈 id에 해당하는 이슈의 상태를 closed로 변경한다.")
	@Test
	void updateStatusClosed() {
		//given
		IssuePostRequest issuePostRequest = IssuePostRequest.builder()
			.title("title2")
			.content("content2")
			.assigneeIds(Arrays.asList(memberId2))
			.labelIds(Arrays.asList(labelId))
			.milestoneId(milestoneId)
			.build();

		Long issueId2 = issueService.post(issuePostRequest, memberId2).getId();
		List<Long> ids = Arrays.asList(issueId, issueId2);

		IssuesStatusRequest request = IssuesStatusRequest.builder()
			.issueIds(ids)
			.issueStatus("closed")
			.build();

		//when
		issueService.updateStatus(request, memberId2);
		IssueDetailsResponse modifiedIssue1 = issueService.show(issueId);
		IssueDetailsResponse modifiedIssue2 = issueService.show(issueId2);

		//then
		assertAll(
			() -> assertThat(modifiedIssue1.getIsOpen()).isFalse(),
			() -> assertThat(modifiedIssue2.getIsOpen()).isFalse()
		);
	}

	@DisplayName("정해진 상태 외 다른 상태로 변경할 시 예외를 발생한다.")
	@Test
	void updateStatusAndThrowException() {
		//given

		List<Long> ids = Arrays.asList(issueId);
		issueRepository.openByIds(ids);

		IssuesStatusRequest request = IssuesStatusRequest.builder()
			.issueIds(ids)
			.issueStatus("in progress")
			.build();

		//when & then
		assertThatThrownBy(() -> issueService.updateStatus(request, memberId2))
			.isInstanceOf(ApiException.class)
			.hasMessageContaining(IssueException.INVALID_ISSUE_STATUS.getMessage());
	}

	@DisplayName("수정된 이슈 제목을 적용한다.")
	@Test
	void modifyTitle() {
		//given
		String modifiedTitle = "modified title";

		IssueTitleRequest request = IssueTitleRequest.builder()
			.title(modifiedTitle)
			.build();

		//when
		issueService.modifyTitle(issueId, request);
		IssueDetailsResponse modifiedIssue = issueService.show(issueId);

		//then
		assertThat(modifiedIssue.getTitle()).isEqualTo(modifiedTitle);
	}

	@DisplayName("기존 라벨을 삭제하고 새로운 라벨을 적용한다.")
	@Test
	void applyLabels() {
		//given
		String labelTitle = "새로운 라벨2";
		String labelDescription = "라벨 설명2";
		String textColor = "color2";
		String backgroundColor = "anotherColor2";

		Label label = makeLabel(labelTitle, labelDescription, textColor, backgroundColor);
		Long labelId2 = labelRepository.save(label);
		List<Long> ids = Arrays.asList(labelId, labelId2);
		IssueApplyRequest request = IssueApplyRequest.builder()
			.ids(ids)
			.build();

		//when
		issueService.applyLabels(issueId, request);
		IssueDetailsResponse modifiedIssue = issueService.show(issueId);

		//then
		assertAll(
			() -> assertThat(modifiedIssue.getLabels()
				.stream()
				.map(LabelResponse::getId)
				.collect(Collectors.toList()))
				.asList()
				.containsExactly(labelId, labelId2),
			() -> assertThat(modifiedIssue.getLabels()
				.stream()
				.map(LabelResponse::getTitle)
				.collect(Collectors.toList()))
				.asList()
				.containsExactly("새로운 라벨", "새로운 라벨2"),
			() -> assertThat(modifiedIssue.getLabels()
				.stream()
				.map(LabelResponse::getTextColor)
				.collect(Collectors.toList()))
				.asList()
				.containsExactly("color", "color2"),
			() -> assertThat(modifiedIssue.getLabels()
				.stream()
				.map(LabelResponse::getBackgroundColor)
				.collect(Collectors.toList()))
				.asList()
				.containsExactly("anotherColor", "anotherColor2")
		);
	}

	@DisplayName("존재하지 않는 라벨 적용 시 예외를 발생한다.")
	@Test
	void applyLabelsAndThrowException() {
		//given
		Long invalidLabelId = 7000L;
		List<Long> ids = Arrays.asList(labelId, invalidLabelId);
		IssueApplyRequest request = IssueApplyRequest.builder()
			.ids(ids)
			.build();

		//when & then
		assertThatThrownBy(() -> issueService.applyLabels(issueId, request))
			.isInstanceOf(ApiException.class)
			.hasMessageContaining(LabelException.NOT_FOUND_LABEL.getMessage());
	}

	@DisplayName("기존 담당자를 삭제하고 새로운 담당자를 적용한다.")
	@Test
	void applyAssignees() {
		//given
		List<Long> ids = Arrays.asList(memberId2);
		IssueApplyRequest request = IssueApplyRequest.builder()
			.ids(ids)
			.build();

		//when
		issueService.applyAssignees(issueId, request);
		IssueDetailsResponse modifiedIssue = issueService.show(issueId);

		//then
		assertAll(
			() -> assertThat(modifiedIssue.getAssignees()
				.stream()
				.map(AssigneeResponse::getId)
				.collect(Collectors.toList()))
				.asList()
				.containsExactly(memberId2),
			() -> assertThat(modifiedIssue.getAssignees()
				.stream()
				.map(AssigneeResponse::getName)
				.collect(Collectors.toList()))
				.asList()
				.containsExactly("movie")
		);
	}

	@DisplayName("존재하지 않는 담당자 적용 시 예외를 발생한다.")
	@Test
	void applyAssigneesAndThrowException() {
		//given
		Long invalidMemberId = 7000L;
		List<Long> ids = Arrays.asList(invalidMemberId);
		IssueApplyRequest request = IssueApplyRequest.builder()
			.ids(ids)
			.build();

		//when & then
		assertThatThrownBy(() -> issueService.applyAssignees(issueId, request))
			.isInstanceOf(ApiException.class)
			.hasMessageContaining(MemberException.NOT_FOUND_MEMBER.getMessage());
	}

	@DisplayName("새로운 마일스톤을 적용한다.")
	@Test
	void applyMilestone() {
		//given
		Boolean newMilestoneIsOpen = true;
		String newMilestoneTitle = "마일스톤2";
		String newMilestoneDescription = "설명2";

		Milestone milestone = makeMilestone(newMilestoneIsOpen, newMilestoneTitle, newMilestoneDescription);
		Long milestoneId2 = milestoneRepository.save(milestone);

		//when
		issueService.applyMilestone(issueId, milestoneId2);
		IssueDetailsResponse modifiedIssue = issueService.show(issueId);

		//then
		assertAll(
			() -> assertThat(modifiedIssue.getMilestone().getId()).isEqualTo(milestoneId2),
			() -> assertThat(modifiedIssue.getMilestone().getTitle()).isEqualTo(newMilestoneTitle)
		);

	}

	@DisplayName("존재하지 않는 마일스톤 적용 시 예외를 발생한다.")
	@Test
	void applyMilestoneAndThrowException() {
		//given
		Long invalidMilestoneId = 7000L;

		//when & then
		assertThatThrownBy(() -> issueService.applyMilestone(issueId, invalidMilestoneId))
			.isInstanceOf(ApiException.class)
			.hasMessageContaining(MilestoneException.NOT_FOUND_MILESTONE.getMessage());
	}
}
