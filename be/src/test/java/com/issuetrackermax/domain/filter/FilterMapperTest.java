package com.issuetrackermax.domain.filter;

import static com.issuetrackermax.fixture.EntityFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.assignee.AssigneeRepository;
import com.issuetrackermax.domain.assignee.entity.Assignee;
import com.issuetrackermax.domain.comment.CommentRepository;
import com.issuetrackermax.domain.history.HistoryRepository;
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
import com.issuetrackermax.service.filter.dto.FilterInformation;

@Transactional
class FilterMapperTest extends IntegrationTestSupport {

	@Autowired
	FilterMapper filterMapper;
	@Autowired
	IssueRepository issueRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	IssueLabelRepository issueLabelRepository;
	@Autowired
	LabelRepository labelRepository;
	@Autowired
	MilestoneRepository milestoneRepository;
	@Autowired
	HistoryRepository historyRepository;
	@Autowired
	AssigneeRepository assigneeRepository;
	@Autowired
	CommentRepository commentRepository;

	@DisplayName("열린 이슈가 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithIssueOpen() {
		// given
		String issueTitle = "issue_title";
		Boolean issueIsOpen = true;

		Issue issue = makeIssue(issueIsOpen, issueTitle, 1L, 1L);
		Long issueId = issueRepository.save(issue);

		FilterInformation information = FilterInformation.builder().issueIsOpen(issueIsOpen).build();
		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(information);
		// then
		assertThat(filteredList).hasSize(1)
			.extracting("title", "isOpen")
			.containsExactlyInAnyOrder(
				tuple(issueTitle, issueIsOpen)
			);
	}

	@DisplayName("닫힌 이슈가 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithIssueClosed() {
		// given
		String issueTitle = "issue_title";
		Boolean issueIsOpen = true;

		Issue issue = makeIssue(true, issueTitle, 1L, 1L);
		Long issueId = issueRepository.save(issue);

		FilterInformation information = FilterInformation.builder().issueIsOpen(issueIsOpen).build();
		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(information);
		// then
		assertThat(filteredList).hasSize(1)
			.extracting("title", "isOpen")
			.containsExactlyInAnyOrder(
				tuple(issueTitle, issueIsOpen)
			);
	}

	@DisplayName("담당자가 earth인 이슈가 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithAssigneeEarth() {
		// given
		String issueTitle = "issue_title";
		Boolean issueIsOpen = true;
		String name1 = "earth";
		String name2 = "movie";

		Member member1 = makeMember("earth@codesquad.kr", "1234", name1, LoginType.LOCAL);
		Long memberId1 = memberRepository.save(member1);

		Member member2 = makeMember("earth@codesquad.kr", "1234", name2, LoginType.LOCAL);
		Long memberId2 = memberRepository.save(member2);

		Issue issue = makeIssue(issueIsOpen, issueTitle, 1L, memberId1);
		Long issueId = issueRepository.save(issue);

		Assignee assignee = makeAssignee(issueId, memberId1);
		Long issigneeId = assigneeRepository.save(assignee);
		Assignee assignee2 = makeAssignee(issueId, memberId2);
		Long issigneeId2 = assigneeRepository.save(assignee2);
		FilterInformation information = FilterInformation.builder().assignee(memberId1).build();
		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(information);
		// then
		assertThat(filteredList).hasSize(1)
			.extracting("title", "isOpen", "writer", "assigneeIds", "assigneeNames")
			.containsExactlyInAnyOrder(
				tuple(issueTitle, issueIsOpen, name1, memberId1 + "," + memberId2, "earth,movie")
			);
	}

	@DisplayName("새로운 레이블을 등록하고 해당 레이블의 id 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithLabelNewId() {

		// given
		String issueTitle = "issue_title";
		String labelTitle1 = "label_title1";
		String labelTitle2 = "label_title2";

		Boolean issueIsOpen = true;

		Label label1 = makeLabel(labelTitle1, "label_description3", "0#9999", "0#8888");
		Long labelId1 = labelRepository.save(label1);
		Label label2 = makeLabel(labelTitle2, "label_description3", "0#9999", "0#8888");
		Long labelId2 = labelRepository.save(label2);
		String labels = labelId1 + "," + labelId2;

		Issue issue = makeIssue(issueIsOpen, issueTitle, 1L, 1L);
		Long issueId = issueRepository.save(issue);

		IssueWithLabel issueWithLabel1 = makeIssueWithLabel(labelId1, issueId);
		IssueWithLabel issueWithLabel2 = makeIssueWithLabel(labelId2, issueId);

		Long issueLabelId1 = issueLabelRepository.save(issueWithLabel1);
		Long issueLabelId2 = issueLabelRepository.save(issueWithLabel2);

		FilterInformation information = FilterInformation.builder().label(labelId1).issueIsOpen(issueIsOpen).build();

		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(information);
		// then
		assertThat(filteredList).hasSize(1)
			.extracting("title", "isOpen", "labelIds")
			.containsExactlyInAnyOrder(
				tuple(issueTitle, issueIsOpen, labels)
			);

	}

	@DisplayName("마일스톤 id가 1이며 닫힌 이슈를 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithMileStoneFirstIdWithClosedIssue() {
		// given
		String issueTitle = "issue_title";
		String milestoneIitle = "milestone_title";

		Boolean issueIsOpen = false;

		Milestone milestone = makeMilestone(true, milestoneIitle, "milestone_description");
		Long milestoneId = milestoneRepository.save(milestone);

		Issue issue = makeIssue(issueIsOpen, issueTitle, milestoneId, 1L);
		Long issueId = issueRepository.save(issue);
		FilterInformation information = FilterInformation.builder()
			.milestone(milestoneId)
			.issueIsOpen(issueIsOpen)
			.build();

		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(information);
		// then
		assertThat(filteredList).hasSize(1)
			.extracting("title", "isOpen", "milestoneId", "milestoneTitle")
			.containsExactlyInAnyOrder(
				tuple(issueTitle, issueIsOpen, milestoneId, milestoneIitle)
			);
	}

	@DisplayName("마일스톤 id가 1이며 열린 이슈를 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithMileStoneFirstIdWithOpenIssue() {
		// given
		String issueTitle = "issue_title";
		String milestoneIitle = "milestone_title";

		Boolean issueIsOpen = true;

		Milestone milestone = makeMilestone(true, milestoneIitle, "milestone_description");
		Long milestoneId = milestoneRepository.save(milestone);

		Issue issue = makeIssue(issueIsOpen, issueTitle, milestoneId, 1L);
		Long issueId = issueRepository.save(issue);
		FilterInformation information = FilterInformation.builder()
			.milestone(milestoneId)
			.issueIsOpen(issueIsOpen)
			.build();

		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(information);
		// then
		assertThat(filteredList).hasSize(1)
			.extracting("title", "isOpen", "milestoneId", "milestoneTitle")
			.containsExactlyInAnyOrder(
				tuple(issueTitle, issueIsOpen, milestoneId, milestoneIitle)
			);
	}

	@DisplayName("작성자가 June이며 열린 이슈를 필터링된 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithWriterJuneWithOpenIssue() {
		// given
		String issueTitle = "issue_title";
		String issueTitle2 = "issue_title2";
		Boolean issueIsOpen = true;
		String name = "June";

		Member member1 = makeMember("earth@codesquad.kr", "1234", name, LoginType.LOCAL);
		Long memberId1 = memberRepository.save(member1);

		Issue issue = makeIssue(issueIsOpen, issueTitle, 1L, memberId1);
		Long issueId = issueRepository.save(issue);
		Issue issue2 = makeIssue(issueIsOpen, issueTitle2, 1L, memberId1);
		Long issueId2 = issueRepository.save(issue2);
		FilterInformation information = FilterInformation.builder().writer(memberId1).issueIsOpen(true).build();

		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(information);
		// then

		assertThat(filteredList).hasSize(2)
			.extracting("title", "writer", "isOpen")
			.containsExactlyInAnyOrder(
				tuple(issueTitle, name, issueIsOpen),
				tuple(issueTitle2, name, issueIsOpen)
			);
	}

	@DisplayName("댓글작성자 아이디로 필터링한 경우 해당 작성자가 댓글을 단 열린이슈 결과를 FilterResultVO로 반환한다.")
	@Test
	void getFilteredListWithCommentWriter() {
		// given
		Long issueId1 = issueRepository.save(makeIssue(true, "issuetitle1", 1L, 1L));
		Long issueId2 = issueRepository.save(makeIssue(true, "issuetitle2", 1L, 1L));
		Long issueId3 = issueRepository.save(makeIssue(true, "issuetitle3", 1L, 1L));
		Long commentId1 = commentRepository.save(makeComment("c1", issueId1, 1L));
		Long commentId2 = commentRepository.save(makeComment("c2", issueId2, 1L));
		Long commentId3 = commentRepository.save(makeComment("c3", issueId3, 2L));

		FilterInformation information = FilterInformation.builder().commentWriter(1L).build();

		// when
		List<FilterResultVO> filteredList = filterMapper.getFilteredList(information);

		// then
		assertThat(filteredList.size()).isEqualTo(2);
		assertThat(filteredList.get(0).getId()).isEqualTo(issueId1);
		assertThat(filteredList.get(1).getId()).isEqualTo(issueId2);
	}

}
