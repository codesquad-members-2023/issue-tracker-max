package com.issuetrackermax.domain.filter;

import static com.issuetrackermax.fixture.entityFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.controller.filter.dto.FilterRequest;
import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.assignee.AssigneeRepository;
import com.issuetrackermax.domain.assignee.entity.Assignee;
import com.issuetrackermax.domain.history.HistoryRepository;
import com.issuetrackermax.domain.history.entity.History;
import com.issuetrackermax.domain.issue.IssueLabelRepository;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.issue.entity.Issue;
import com.issuetrackermax.domain.issue.entity.IssueWithLabel;
import com.issuetrackermax.domain.label.LabelRepository;
import com.issuetrackermax.domain.label.entity.Label;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;
import com.issuetrackermax.domain.milestone.MilestoneRepository;
import com.issuetrackermax.domain.milestone.entity.Milestone;
import com.issuetrackermax.util.DatabaseCleaner;

@Transactional
class FilterMapperTest extends IntegrationTestSupport {

	@Autowired
	private FilterMapper filterMapper;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private IssueLabelRepository issueLabelRepository;
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private MilestoneRepository milestoneRepository;
	@Autowired
	private HistoryRepository historyRepository;
	@Autowired
	private AssigneeRepository assigneeRepository;
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@BeforeEach
	void before() {
		databaseCleaner.execute();

		Member member = makeMember("june@codesquad.kr", "1234", "June", LoginType.LOCAL);
		Long memberId = memberRepository.save(member);

		Member member2 = makeMember("movie@codesquad.kr", "1234", "Movie", LoginType.LOCAL);
		Long memberId2 = memberRepository.save(member2);

		Label label = makeLabel("label_title1", "label_description1", "0#9999", "0#8888");
		Long labelId = labelRepository.save(label);

		Label label2 = makeLabel("label_title2", "label_description2", "0#9999", "0#8888");
		Long labelId2 = labelRepository.save(label2);

		Milestone milestone = makeMilestone(true, "milestone_title", "milestone_description_title");
		Long milestoneId = milestoneRepository.save(milestone);

		Issue issue = makeIssue(true, "issue_title", milestoneId, memberId);
		Long issueId = issueRepository.save(issue);

		Issue issue2 = makeIssue(false, "issue_title2", milestoneId, memberId);
		Long issueId2 = issueRepository.save(issue2);

		IssueWithLabel issueWithLabel = makeIssueWithLabel(labelId, issueId);
		Long issueLabelId = issueLabelRepository.save(issueWithLabel);

		IssueWithLabel issueWithLabel2 = makeIssueWithLabel(labelId2, issueId);
		Long issueLabelId2 = issueLabelRepository.save(issueWithLabel2);

		IssueWithLabel issueWithLabel3 = makeIssueWithLabel(labelId2, issueId2);
		Long issueLabelId3 = issueLabelRepository.save(issueWithLabel3);

		History history = makeHistory(memberId, issueId, true);
		Long historyId = historyRepository.save(history);

		History history2 = makeHistory(memberId2, issueId, false);
		Long historyId2 = historyRepository.save(history2);

		History history3 = makeHistory(memberId2, issueId2, false);
		Long historyId3 = historyRepository.save(history3);

		Assignee assignee = makeAssignee(issueId, memberId);
		Long issigneeId = assigneeRepository.save(assignee);

		Assignee assignee2 = makeAssignee(issueId, memberId2);
		Long issigneeId2 = assigneeRepository.save(assignee2);

		Assignee assignee3 = makeAssignee(issueId2, memberId);
		Long issigneeId3 = assigneeRepository.save(assignee3);

	}

	@DisplayName("열린 이슈가 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithIssueOpen() {
		// given
		FilterRequest issueIsOpen = FilterRequest.builder().issueIsOpen(true).build();

		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(issueIsOpen);
		// then
		assertExtractingWithParameter(filteredList, "issue_title", "Movie", "June", true, "1,2",
			"label_title1,label_title2", "1,2", "June,Movie", "milestone_title");
	}

	@DisplayName("닫힌 이슈가 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithIssueClosed() {
		// given
		FilterRequest issueIsOpen = FilterRequest.builder().issueIsOpen(false).build();
		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(issueIsOpen);
		// then
		assertExtractingWithParameter(filteredList, "issue_title2", "Movie", "June", false, "2",
			"label_title2", "1", "June", "milestone_title");
	}

	@DisplayName("담당자가 earth인 이슈가 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithAssigneeMovie() {
		// given

		Member member = makeMember("june@codesquad.kr", "1234", "earth", LoginType.LOCAL);
		Long memberId = memberRepository.save(member);

		Issue issue = makeIssue(true, "issue_title_test", 1L, memberId);
		Long issueId = issueRepository.save(issue);
		Assignee assignee = makeAssignee(issueId, memberId);
		Long issigneeId = assigneeRepository.save(assignee);
		FilterRequest issueIsOpen = FilterRequest.builder().assignee(memberId).build();
		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(issueIsOpen);
		// then
		assertExtractingWithParameter(filteredList, "issue_title_test", null, "earth", true, null,
			null, memberId.toString(), "earth", "milestone_title");
	}

	@DisplayName("새로운 레이블을 등록하고 해당 레이블의 id 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithLabelNewId() {

		// given
		Label label = makeLabel("label_title_test", "label_description3", "0#9999", "0#8888");
		Long labelId = labelRepository.save(label);

		Issue issue = makeIssue(false, "issue_title_test", 1L, 1L);
		Long issueId = issueRepository.save(issue);

		IssueWithLabel issueWithLabel = IssueWithLabel.builder()
			.issueId(issueId)
			.labelId(labelId)
			.build();
		Long issueLabelId2 = issueLabelRepository.save(issueWithLabel);
		FilterRequest issueIsOpen = FilterRequest.builder().label(labelId).issueIsOpen(false).build();
		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(issueIsOpen);
		// then
		assertExtractingWithParameter(filteredList, "issue_title_test", null, "June", false, labelId.toString(),
			"label_title_test", null, null, "milestone_title");
	}

	@DisplayName("마일스톤 id가 3이며 닫힌 이슈를 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithMileStoneFirstIdWithClosedIssue() {
		// given
		Milestone milestone = makeMilestone(true, "milestone_title3", "milestone_description");
		Long milestoneId = milestoneRepository.save(milestone);
		Issue issue = makeIssue(false, "issue_title_test", milestoneId, 1L);
		Long issueId2 = issueRepository.save(issue);
		FilterRequest issueIsOpen = FilterRequest.builder().milestone(milestoneId).issueIsOpen(false).build();
		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(issueIsOpen);
		// then

		assertExtractingWithParameter(filteredList, "issue_title_test", null, "June", false, null,
			null, null, null, "milestone_title3");
	}

	@DisplayName("마일스톤 id가 3이며 열린 이슈를 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithMileStoneFirstIdWithOpenIssue() {
		// given
		Milestone milestone = makeMilestone(true, "milestone_title_test", "milestone_description");
		Long milestoneId = milestoneRepository.save(milestone);
		Issue issue3 = makeIssue(true, "issue_title_test", milestoneId, 1L);
		Long issueId2 = issueRepository.save(issue3);
		FilterRequest issueIsOpen = FilterRequest.builder().milestone(milestoneId).issueIsOpen(true).build();

		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(issueIsOpen);

		// then
		assertExtractingWithParameter(filteredList, "issue_title_test", null, "June", true, null,
			null, null, null, "milestone_title_test");
	}

	@DisplayName("작성자가 June이며 열린 이슈를 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithWriterJuneWithOpenIssue() {
		// given

		Issue issue3 = makeIssue(true, "issue_title_test", 1L, 1L);
		issueRepository.save(issue3);
		FilterRequest issueIsOpen = FilterRequest.builder().writer(1L).issueIsOpen(true).build();
		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(issueIsOpen);
		// then

		assertThat(filteredList).hasSize(2)
			.extracting("title", "editor", "writer", "isOpen", "labelIds", "labelTitles",
				"assigneeIds", "assigneeNames", "milestoneTitle")
			.containsExactlyInAnyOrder(
				tuple("issue_title", "Movie", "June", true, "1,2",
					"label_title1,label_title2", "1,2", "June,Movie", "milestone_title"),
				tuple("issue_title_test", null, "June", true, null,
					null, null, null, "milestone_title")
			);
	}

	private void assertExtractingWithParameter(List<FilterResultVO> filteredList, String title, String editor,
		String writer, Boolean isOpen, String labelIds, String labelTitles,
		String assigneeIds, String assigneeNames, String milestoneTitle) {
		assertThat(filteredList).hasSize(1)
			.extracting("title", "editor", "writer", "isOpen", "labelIds", "labelTitles",
				"assigneeIds", "assigneeNames", "milestoneTitle")
			.containsExactlyInAnyOrder(
				tuple(title, editor, writer, isOpen, labelIds, labelTitles, assigneeIds,
					assigneeNames, milestoneTitle)
			);

	}
}