package com.issuetracker.unit.validator;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.issue.application.IssueValidator;
import com.issuetracker.issue.domain.IssueDetailRead;
import com.issuetracker.label.application.LabelValidator;
import com.issuetracker.member.application.MemberValidator;
import com.issuetracker.member.domain.Member;
import com.issuetracker.milestone.application.MilestoneValidator;
import com.issuetracker.util.MockTest;

@MockTest
public class IssueValidatorTest {

	@InjectMocks
	private IssueValidator issueValidator;

	@Mock
	private MilestoneValidator milestoneValidator;

	@Mock
	private MemberValidator memberValidator;

	@Mock
	private LabelValidator labelValidator;

	@Test
	void 이슈_상세_조회_시_NULL을_검증한다() {
		// given
		IssueDetailRead issueDetailRead = IssueDetailRead.builder()
			.id(1L)
			.isOpen(false)
			.labels(Collections.emptyList())
			.assignees(Collections.emptyList())
			.milestone(null)
			.title("제목")
			.content("내용")
			.author(Member.builder()
				.id(1L)
				.email("test@c.com")
				.nickname("test")
				.password("asdfds")
				.build())
			.build();

		// then
		Assertions.assertDoesNotThrow(() -> issueValidator.verifyIssueDetail(issueDetailRead));
	}

	@Test
	void 이슈_상세_조회_시_NULL인_경우_에러를_반환한다() {
		// then
		Assertions.assertThrows(CustomHttpException.class,
			() -> issueValidator.verifyIssueDetail(null));
	}

	@Test
	void 이슈_업데이트시_카운트로_검증한다() {
		// then
		Assertions.assertDoesNotThrow(() -> issueValidator.verifyUpdateIssue(1));
	}

	@Test
	void 이슈_업데이트시_카운트가_1이_아닌_경우_에러를_반환한다() {
		// then
		Assertions.assertThrows(CustomHttpException.class,
			() -> issueValidator.verifyUpdateIssue(0));
	}
}
