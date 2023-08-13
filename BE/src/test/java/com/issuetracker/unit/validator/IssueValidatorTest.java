package com.issuetracker.unit.validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.issue.application.IssueValidator;
import com.issuetracker.issue.domain.IssueDetailRead;
import com.issuetracker.issue.domain.IssueRepository;
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

	@Mock
	private IssueRepository issueRepository;

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
	void 이슈_업데이트_및_삭제_시_카운트로_검증한다() {
		// then
		Assertions.assertDoesNotThrow(() -> issueValidator.verifyUpdatedOrDeletedCount(1));
	}

	@Test
	void 이슈_업데이트_및_삭제_시_카운트가_1이_아닌_경우_에러를_반환한다() {
		// then
		Assertions.assertThrows(CustomHttpException.class,
			() -> issueValidator.verifyUpdatedOrDeletedCount(0));
	}

	@Test
	void 이슈_업데이트_시_데이터가_NULL을_검증한다() {
		// then
		Assertions.assertAll(
			() -> Assertions.assertDoesNotThrow(() -> issueValidator.verifyNonNull("내용")),
			() -> Assertions.assertDoesNotThrow(() -> issueValidator.verifyNonNull(false))
		);
	}

	@Test
	void 이슈_업데이트_시_데이터가_NULL인_경우_에러를_반환한다() {
		// then
		Assertions.assertThrows(CustomHttpException.class,
			() -> issueValidator.verifyNonNull(null));
	}

	@Test
	void 댓글_작성_시_이슈와_작성자가_존재하는지_검증한다() {
		// given
		given(issueRepository.existById(anyLong())).willReturn(true);
		willDoNothing().given(memberValidator).verifyMember(any());

		// then
		Assertions.assertDoesNotThrow(() -> issueValidator.verifyCreateIssueComment(1L, 1L));
	}

	@Test
	void 댓글_작성_시_이슈의_아이디가_null인_경우_동작되지_않는다() {
		// then
		Assertions.assertDoesNotThrow(() -> issueValidator.verifyCreateIssueComment(null, null));
		then(issueRepository).should(Mockito.never()).existById(anyLong());
	}

	@Test
	void 댓글_작성_시_해당_이슈가_존재하지_않으면_에러를_반환한다() {
		// given
		given(issueRepository.existById(anyLong())).willReturn(false);

		// then
		Assertions.assertThrows(CustomHttpException.class,
			() -> issueValidator.verifyCreateIssueComment(1L, 1L));
	}
}
