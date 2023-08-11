package com.issuetrackermax.service.filter;

import static com.issuetrackermax.fixture.EntityFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.controller.filter.dto.response.FilterResponse;
import com.issuetrackermax.controller.filter.dto.response.IssueResponse;
import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.assignee.AssigneeRepository;
import com.issuetrackermax.domain.assignee.entity.Assignee;
import com.issuetrackermax.domain.issue.IssueLabelRepository;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.issue.entity.Issue;
import com.issuetrackermax.domain.label.LabelRepository;
import com.issuetrackermax.domain.label.entity.Label;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;
import com.issuetrackermax.domain.milestone.MilestoneRepository;
import com.issuetrackermax.domain.milestone.entity.Milestone;
import com.issuetrackermax.service.filter.dto.FilterInformation;
import com.issuetrackermax.util.DatabaseCleaner;

class FilterServiceTest extends IntegrationTestSupport {

	@Autowired
	DatabaseCleaner databaseCleaner;
	@Autowired
	private FilterService filterService;
	@Autowired
	private MilestoneRepository milestoneRepository;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private IssueLabelRepository issueLabelRepository;
	@Autowired
	private AssigneeRepository assigneeRepository;

	@AfterEach
	void cleaner() {
		databaseCleaner.execute();
	}

	@DisplayName("FilterRequest를 받은 후 FilterResponse로 반환한다.")
	@Test
	void getMainPageIssueTest() {
		// given
		Milestone milestone = makeMilestone(true, "milestone_title", "milestone_description");
		Long milestoneId = milestoneRepository.save(milestone);

		Member june = makeMember("june@codesquad.kr", "12345678", "June", LoginType.LOCAL);
		Long juneId = memberRepository.save(june);
		Member movie = makeMember("june@codesquad.kr", "12345678", "Movie", LoginType.LOCAL);
		Long movieId = memberRepository.save(movie);

		Issue issue = makeIssue(true, "issue_title", milestoneId, juneId);
		Long issueId = issueRepository.save(issue);

		Label label = makeLabel("label1", "label_description", "0#1111", "0#3333");
		Long labelId = labelRepository.save(label);
		Label label2 = makeLabel("label2", "label_description", "0#1111", "0#3332");
		Long labelId2 = labelRepository.save(label2);
		Long issueLabelId = issueLabelRepository.save(makeIssueWithLabel(labelId, issueId));
		Long issueLabelId2 = issueLabelRepository.save(makeIssueWithLabel(labelId2, issueId));

		Assignee assignee = makeAssignee(issueId, juneId);
		Long assigneeId = assigneeRepository.save(assignee);
		Assignee assignee2 = makeAssignee(issueId, movieId);
		Long assigneeId2 = assigneeRepository.save(assignee2);

		FilterInformation information = FilterInformation.builder()
			.issueIsOpen(true)
			.build();
		// when
		FilterResponse mainPageIssue = filterService.getMainPageIssue(information);

		List<IssueResponse> issues = mainPageIssue.getIssues();

		// then
		assertAll(
			() -> assertThat(mainPageIssue.getLabelCount()).isEqualTo(2L),
			() -> assertThat(mainPageIssue.getMilestoneCount()).isEqualTo(1L),
			() -> assertThat(mainPageIssue.getOpenIssueCount()).isEqualTo(1L),
			() -> assertThat(mainPageIssue.getClosedIssueCount()).isEqualTo(0L),
			() -> assertThat(issues.get(0).getId()).isEqualTo(issueId),
			() -> assertThat(issues.get(0).getIsOpen()).isTrue(),
			() -> assertThat(issues.get(0).getTitle()).isEqualTo("issue_title"),
			() -> assertThat(issues.get(0).getLabels().get(0).getId()).isEqualTo(labelId),
			() -> assertThat(issues.get(0).getLabels().get(0).getTitle()).isEqualTo("label1"),
			() -> assertThat(issues.get(0).getLabels().get(0).getTextColor()).isEqualTo("0#1111"),
			() -> assertThat(issues.get(0).getLabels().get(0).getBackgroundColor()).isEqualTo("0#3333"),
			() -> assertThat(issues.get(0).getLabels().get(1).getId()).isEqualTo(labelId2),
			() -> assertThat(issues.get(0).getLabels().get(1).getTitle()).isEqualTo("label2"),
			() -> assertThat(issues.get(0).getLabels().get(1).getTextColor()).isEqualTo("0#1111"),
			() -> assertThat(issues.get(0).getLabels().get(1).getBackgroundColor()).isEqualTo("0#3332"),
			() -> assertThat(issues.get(0).getAssignees().get(0).getId()).isEqualTo(juneId),
			() -> assertThat(issues.get(0).getAssignees().get(0).getName()).isEqualTo("June"),
			() -> assertThat(issues.get(0).getAssignees().get(1).getId()).isEqualTo(movieId),
			() -> assertThat(issues.get(0).getAssignees().get(1).getName()).isEqualTo("Movie"),
			() -> assertThat(issues.get(0).getMilestone().getId()).isEqualTo(milestoneId),
			() -> assertThat(issues.get(0).getMilestone().getTitle()).isEqualTo("milestone_title"));

	}

}
