package com.issuetrackermax.domain.filter;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

		Member member = Member.builder()
			.loginId("june@codesquad.kr")
			.password("1234")
			.nickName("June")
			.loginType(LoginType.LOCAL)
			.build();
		Long memberId = memberRepository.save(member);

		Member member2 = Member.builder()
			.loginId("movie@codesquad.kr")
			.password("1234")
			.nickName("Movie")
			.loginType(LoginType.LOCAL)
			.build();
		Long memberId2 = memberRepository.save(member2);

		Label label = Label.builder()
			.title("label_title1")
			.description("label_description1")
			.textColor("0#9999")
			.backgroundColor("0#8888")
			.build();
		Long labelId = labelRepository.save(label);

		Label label2 = Label.builder()
			.title("label_title2")
			.description("label_description2")
			.textColor("0#9999")
			.backgroundColor("0#8888")
			.build();
		Long labelId2 = labelRepository.save(label2);

		Milestone milestone = Milestone.builder()
			.title("milestone_title")
			.description("milestone_description_title")
			.isOpen(true)
			.build();
		Long milestoneId = milestoneRepository.save(milestone);
		Issue issue = Issue.builder()
			.isOpen(true)
			.title("issue_title")
			.milestoneId(milestoneId)
			.writerId(memberId)
			.build();
		Long issueId = issueRepository.save(issue);

		Issue issue2 = Issue.builder()
			.isOpen(false)
			.title("issue_title2")
			.milestoneId(milestoneId)
			.writerId(memberId)
			.build();
		Long issueId2 = issueRepository.save(issue2);

		IssueWithLabel issueWithLabel = IssueWithLabel.builder()
			.issueId(issueId)
			.labelId(labelId)
			.build();
		Long issueLabelId = issueLabelRepository.save(issueWithLabel);

		IssueWithLabel issueWithLabel2 = IssueWithLabel.builder()
			.issueId(issueId)
			.labelId(labelId2)
			.build();
		Long issueLabelId2 = issueLabelRepository.save(issueWithLabel2);

		IssueWithLabel issueWithLabel3 = IssueWithLabel.builder()
			.issueId(issueId2)
			.labelId(labelId2)
			.build();
		Long issueLabelId3 = issueLabelRepository.save(issueWithLabel3);

		History history = History.builder()
			.editor(memberId)
			.issueId(issueId)
			.issueIsOpen(true)
			.build();
		Long historyId = historyRepository.save(history);

		History history2 = History.builder()
			.editor(memberId2)
			.issueId(issueId)
			.issueIsOpen(false)
			.build();
		Long historyId2 = historyRepository.save(history2);

		History history3 = History.builder()
			.editor(memberId2)
			.issueId(issueId2)
			.issueIsOpen(false)
			.build();
		Long historyId3 = historyRepository.save(history3);

		Assignee assignee = Assignee.builder()
			.issueId(issueId)
			.memberid(memberId)
			.build();
		Long issigneeId = assigneeRepository.save(assignee);

		Assignee assignee2 = Assignee.builder()
			.issueId(issueId)
			.memberid(memberId2)
			.build();
		Long issigneeId2 = assigneeRepository.save(assignee2);

		Assignee assignee3 = Assignee.builder()
			.issueId(issueId2)
			.memberid(memberId)
			.build();
		Long issigneeId3 = assigneeRepository.save(assignee3);

	}

	@DisplayName("열린 이슈가 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithIssueOpen() {
		// given
		Map<String, Object> issueIsOpen = Map.of("issueIsOpen", 1);

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
		Map<String, Object> issueIsOpen = Map.of("issueIsOpen", 0);
		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(issueIsOpen);
		// then
		assertExtractingWithParameter(filteredList, "issue_title2", "Movie", "June", false, "2",
			"label_title2", "1", "June", "milestone_title");
	}

	@DisplayName("담당자가 Movie인 이슈가 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithAssigneeMovie() {
		// given
		Map<String, Object> issueIsOpen = Map.of("assignee", 2);
		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(issueIsOpen);
		// then
		assertExtractingWithParameter(filteredList, "issue_title", "Movie", "June", true, "1,2",
			"label_title1,label_title2", "1,2", "June,Movie", "milestone_title");
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