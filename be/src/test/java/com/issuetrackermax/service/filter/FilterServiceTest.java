package com.issuetrackermax.service.filter;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.issue.entity.Issue;
import com.issuetrackermax.domain.label.LabelRepository;
import com.issuetrackermax.domain.label.entity.Label;
import com.issuetrackermax.domain.milestone.MilestoneRepository;
import com.issuetrackermax.domain.milestone.entity.Milestone;
import com.issuetrackermax.util.DatabaseCleaner;

class FilterServiceTest extends IntegrationTestSupport {

	@Autowired
	private FilterService filterService;
	@Autowired
	private MilestoneRepository milestoneRepository;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	DatabaseCleaner databaseCleaner;

	@AfterEach
	void cleaner() {
		databaseCleaner.execute();
	}

	@DisplayName("Repository에 저장된 Milestone의 총 개수를 가져온다.")
	@Test
	void getMilestoneCount() {
		// given
		Milestone milestone = makeMilestone(true, "milestone", "description");
		Milestone milestone2 = makeMilestone(true, "milestone", "description");
		Milestone milestone3 = makeMilestone(true, "milestone", "description");
		milestoneRepository.save(milestone);
		milestoneRepository.save(milestone2);
		milestoneRepository.save(milestone3);

		// when
		Long milestoneCount = filterService.getMilestoneCount();

		// then
		assertThat(milestoneCount).isEqualTo(3L);
	}

	@DisplayName("Repository에 저장된 Label의 총 개수를 가져온다.")
	@Test
	void getLabelCount() {
		// given
		Label label = makeLabel("label_title1", "label_description1", "0#9999", "0#8888");
		Label label2 = makeLabel("label_title1", "label_description1", "0#9999", "0#8888");
		Label label3 = makeLabel("label_title1", "label_description1", "0#9999", "0#8888");

		labelRepository.save(label);
		labelRepository.save(label2);
		labelRepository.save(label3);

		// when
		Long labelCount = labelRepository.getLabelCount();

		// then
		assertThat(labelCount).isEqualTo(3L);

	}

	@DisplayName("Repository에 열린 이슈의 총 개수를 가져온다.")
	@Test
	void getOpenIssueCount() {
		// given
		Issue issue = makeIssue(true, "issue_title", 1L, 1L);
		Issue issue2 = makeIssue(true, "issue_title", 1L, 1L);
		Issue issue3 = makeIssue(true, "issue_title", 1L, 1L);

		issueRepository.save(issue);
		issueRepository.save(issue2);
		issueRepository.save(issue3);

		// when
		List<Issue> openIssue = issueRepository.getOpenIssue();

		// then
		assertThat(openIssue.size()).isEqualTo(3L);

	}

	@DisplayName("Repository에 닫힌 이슈의 총 개수를 가져온다.")
	@Test
	void getClosedIssueCount() {
		// given
		Issue issue = makeIssue(false, "issue_title", 1L, 1L);
		Issue issue2 = makeIssue(false, "issue_title", 1L, 1L);
		Issue issue3 = makeIssue(false, "issue_title", 1L, 1L);

		issueRepository.save(issue);
		issueRepository.save(issue2);
		issueRepository.save(issue3);

		// when
		List<Issue> openIssue = issueRepository.getClosedIssue();

		// then
		assertThat(openIssue.size()).isEqualTo(3L);
	}

	private Milestone makeMilestone(Boolean isOpen, String title, String description) {
		return Milestone.builder()
			.isOpen(isOpen)
			.title(title)
			.description(description)
			.build();
	}

	private Label makeLabel(String title, String description, String textColor, String backgroundColor) {
		return Label.builder()
			.title(title)
			.description(description)
			.textColor(textColor)
			.backgroundColor(backgroundColor)
			.build();
	}

	private Issue makeIssue(Boolean isOpen, String title, Long milestoneId, Long writerId) {
		return Issue.builder()
			.isOpen(isOpen)
			.title("issue_title")
			.milestoneId(milestoneId)
			.writerId(writerId)
			.build();
	}
}