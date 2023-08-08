package com.issuetracker.acceptance;

import static com.issuetracker.util.fixture.IssueCommentFixture.ISSUE_COMMENT1;
import static com.issuetracker.util.fixture.IssueFixture.ISSUE1;
import static com.issuetracker.util.steps.IssueSteps.이슈_댓글_내용_수정_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈_댓글_작성_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈_상세_조회_요청;

import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.http.HttpStatus;

import com.issuetracker.issue.ui.dto.IssueCommentResponse;
import com.issuetracker.util.AcceptanceTest;

public class IssueCommentAcceptanceTest extends AcceptanceTest {

	/**
	 * Given 회원, 마일스톤, 라벨, 이슈를 생성하고
	 * When 이슈 댓글을 작성하면
	 * Then 이슈 상세 조회에서 조회할 수 있다.
	 */
	@Test
	void 이슈_댓글을_작성한다() {
		// when
		var response = 이슈_댓글_작성_요청(ISSUE1.getId(), "# 집중!");

		// then
		long savedIssueCommentId = response.jsonPath().getLong("id");
		응답_상태코드_검증(response, HttpStatus.CREATED);
		이슈_상세_조회에서_작성한_댓글_검증(ISSUE1.getId(), savedIssueCommentId, "# 집중!");
	}

	/**
	 * Given 회원, 마일스톤, 라벨, 이슈를 생성하고
	 * When 이슈 댓글을 작성 시 공백이거나 3000자 초과할 경우
	 * Then 요청이 실패된다.
	 */
	@ParameterizedTest
	@NullAndEmptySource
	@MethodSource("providerCommentContent")
	void 이슈_댓글_작성_시_공백_이거나_3000자_초과할_경우_요청이_실패된다(String content) {
		// when
		var response = 이슈_댓글_작성_요청(ISSUE1.getId(), content);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 	Given 회원, 마일스톤, 라벨을 생성하고
	 * 	When 이슈 댓글 작성 시 존재하지 않는 이슈를 입력하면
	 * 	Then 요청이 실패된다.
	 */
	@Test
	void 이슈_댓글_작성_시_존재하지_않는_이슈를_입력하면_요청이_실패된다() {
		// given
		Long 존재_하지_않는_아이디 = 20L;

		// when
		var response = 이슈_댓글_작성_요청(존재_하지_않는_아이디, "content");

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 라벨, 마일스톤, 회원, 이슈, 이슈 댓글을 생성하고
	 * When 이슈 댓글 내용을 수정하면
	 * Then 이슈 상세 조회에서 수정된 값을 확인할 수 있다.
	 */
	@Test
	void 이슈_댓글_내용을_수정한다() {
		// when
		var response = 이슈_댓글_내용_수정_요청(ISSUE1.getId(), ISSUE_COMMENT1.getIssueId(), "## 수정한 댓글 내용");

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		이슈_상세_조회에서_작성한_댓글_검증(ISSUE1.getId(), ISSUE_COMMENT1.getIssueId(),"## 수정한 댓글 내용");
	}

	/**
	 * Given 라벨, 마일스톤, 회원, 이슈, 이슈 댓글을 생성하고
	 * When 이슈 내용을 수정 시 3000글자 초과하면
	 * Then 요청이 실패된다.
	 */
	@ParameterizedTest
	@NullSource
	@MethodSource("providerCommentContent")
	void 이슈_내용_수정_시_3000글자_초과하면_요청이_실패된다(String content) {
		// when
		var response = 이슈_댓글_내용_수정_요청(ISSUE1.getId(), ISSUE_COMMENT1.getIssueId(), content);

		// when
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * When 존재하지 않는 이슈 댓글로 내용을 수정하면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 존재하지_않는_이슈_댓글로_내용을_수정_시_요청이_실패된다() {
		// given
		Long 존재하지_않는_이슈_댓글_아이디 = 20L;

		// when
		var response = 이슈_댓글_내용_수정_요청(ISSUE1.getId(), 존재하지_않는_이슈_댓글_아이디, "## content");

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	private static Stream<Arguments> providerCommentContent() {
		return Stream.of(
			Arguments.of(
				"content".repeat(3000)
			)
		);
	}

	private void 이슈_상세_조회에서_작성한_댓글_검증(Long id, Long issueCommentId, String content) {
		List<IssueCommentResponse> comments = 이슈_상세_조회_요청(id).jsonPath().getList("comments", IssueCommentResponse.class);
		IssueCommentResponse issueCommentResponse = comments.stream()
			.filter(c -> c.getId() == issueCommentId)
			.findAny()
			.orElseThrow();

		Assertions.assertThat(issueCommentResponse.getContent()).isEqualTo(content);
	}
}
