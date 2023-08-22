package com.issuetracker.acceptance;

import static com.issuetracker.util.fixture.AssigneeFixture.ASSIGNEE1;
import static com.issuetracker.util.fixture.IssueFixture.ISSUE1;
import static com.issuetracker.util.fixture.IssueFixture.ISSUE2;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER1;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER4;
import static com.issuetracker.util.steps.AssigneeSteps.이슈에_담당자_등록_요청;
import static com.issuetracker.util.steps.AssigneeSteps.이슈에_담당자_삭제_요청;
import static com.issuetracker.util.steps.AssigneeSteps.이슈에_등록_되어있는_담당자_목록_조회_요청;
import static com.issuetracker.util.steps.AssigneeSteps.이슈에_등록_및_삭제될_담당자_목록_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.issuetracker.util.AcceptanceTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AssigneeAcceptanceTest extends AcceptanceTest {

	/**
	 * Given 회원, 이슈, 담당자를 생성하고
	 * When 이슈에 등록 되어있는 담당자 목록을 조회하면
	 * Then 이슈에 등록 되어 있는 담당자 목록을 조회할 수 있다.
	 */
	@Test
	void 담당자_목록을_조회한다() {
		// when
		var response = 이슈에_등록_되어있는_담당자_목록_조회_요청();

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		이슈에_등록_되어있는_담당자_목록_검증(response);
	}

	/**
	 * Given 라벨, 회원, 이슈를 생성하고
	 * When 해당 이슈에 담당자를 등록하면
	 * Then 등록 및 삭제될 담당자 목록에서 등록된 담당자를 확인 할 수 있다.
	 */
	@Test
	void 이슈에_담당자를_등록한다() {
		// when
		var response = 이슈에_담당자_등록_요청(ISSUE1.getId(), MEMBER4.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		이슈에_등록_및_삭제될_담당자_목록에서_등록된_담당자_검증(ISSUE1.getId(), MEMBER4.getId());
	}

	/**
	 * Given 라벨, 회원, 이슈를 생성하고
	 * When 해당 이슈에 담당자 등록 시 이미 등록된 담당자이면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 이슈에_담당자를_등록_시_이미_등록된_담당자인_경우_요청이_실패된다() {
		// when
		var response = 이슈에_담당자_등록_요청(ISSUE1.getId(), MEMBER1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 회원을 생성하고
	 * When 담당자를 등록 시 존재하지 않는 이슈이면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 이슈에_담당자를_등록_시_존재하지_않는_이슈이면_요청이_실패된다() {
		// given
		Long 존재하지_않는_이슈_아이디 = 20L;

		// when
		var response = 이슈에_담당자_등록_요청(존재하지_않는_이슈_아이디, MEMBER1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 이슈을 생성하고
	 * When 담당자를 등록 시 존재하지 않는 회원이면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 이슈에_담당자를_등록_시_존재하지_않는_회원이면_요청이_실패된다() {
		// given
		Long 존재하지_않는_회원_아이디 = 20L;

		// when
		var response = 이슈에_담당자_등록_요청(ISSUE2.getId(), 존재하지_않는_회원_아이디);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 회원, 이슈, 담당자를 생성하고
	 * When 해당 이슈에 담당자를 삭제하면
	 * Then 등록 및 삭제될 담당자 목록에서 삭제된 담당자를 찾을 수 없다.
	 */
	@Test
	void 이슈에_담당자를_삭제한다() {
		// when
		var response = 이슈에_담당자_삭제_요청(ISSUE1.getId(), MEMBER1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		이슈에_등록_및_삭제될_담당자_목록에서_삭제된_담당자_검증(ISSUE1.getId(), MEMBER1.getId());
	}

	private void 이슈에_등록_및_삭제될_담당자_목록에서_삭제된_담당자_검증(Long id, Long memberId) {
		List<Long> memberIds = 이슈에_등록_및_삭제될_담당자_목록_조회_요청(id).jsonPath().getList("assignees.id", Long.class);

		assertThat(memberIds).doesNotContain(memberId);
	}

	private void 이슈에_등록_및_삭제될_담당자_목록에서_등록된_담당자_검증(Long id, Long memberId) {
		List<Long> memberIds = 이슈에_등록_및_삭제될_담당자_목록_조회_요청(id).jsonPath().getList("assignees.id", Long.class);

		assertThat(memberIds).contains(memberId);
	}

	private void 이슈에_등록_되어있는_담당자_목록_검증(ExtractableResponse<Response> response) {
		List<Long> ids = response.jsonPath().getList("assignees.id", Long.class);

		assertThat(ids).containsExactly(1L, 2L, 3L, 4L);
	}
}
