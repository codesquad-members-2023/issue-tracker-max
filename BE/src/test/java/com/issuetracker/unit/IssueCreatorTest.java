package com.issuetracker.unit;

import static com.issuetracker.util.fixture.MemberFixture.USER1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.issuetracker.issue.application.IssueCreator;
import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.infrastrucure.AssigneeRepository;
import com.issuetracker.issue.infrastrucure.IssueLabelMappingRepository;
import com.issuetracker.issue.infrastrucure.IssueRepository;
import com.issuetracker.label.application.LabelValidator;
import com.issuetracker.member.application.MemberValidator;
import com.issuetracker.milestone.application.MilestoneValidator;
import com.issuetracker.util.MockTest;

@MockTest
public class IssueCreatorTest {

	@InjectMocks
	private IssueCreator issueCreator;

	@Mock
	private MilestoneValidator milestoneValidator;

	@Mock
	private MemberValidator memberValidator;

	@Mock
	private LabelValidator labelValidator;

	@Mock
	private IssueLabelMappingRepository issueLabelMappingRepository;

	@Mock
	private AssigneeRepository assigneeRepository;

	@Mock
	private IssueRepository issueRepository;

	@Test
	void 이슈를_생성한다() {
		// given
		given(issueRepository.save(any())).willReturn(1L);
		given(assigneeRepository.saveAll(Collections.emptyList())).willReturn(new int[0]);
		given(issueLabelMappingRepository.saveAll(Collections.emptyList())).willReturn(new int[0]);

		IssueCreateInputData issueCreateInputData = new IssueCreateInputData(
			"제목",
			"##내용",
			Collections.emptyList(),
			Collections.emptyList(),
			null,
			USER1.getId()
		);

		// when
		Long actual = issueCreator.create(issueCreateInputData);

		// then
		assertThat(actual).isEqualTo(1L);
	}
}
