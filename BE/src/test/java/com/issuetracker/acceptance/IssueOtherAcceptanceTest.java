package com.issuetracker.acceptance;

import static com.issuetracker.util.fixture.AssignedLabelFixture.ASSIGNED_LABEL1;
import static com.issuetracker.util.fixture.AssigneeFixture.ASSIGNEE1;
import static com.issuetracker.util.fixture.IssueFixture.ISSUE1;
import static com.issuetracker.util.fixture.IssueFixture.ISSUE2;
import static com.issuetracker.util.fixture.LabelFixture.LABEL1;
import static com.issuetracker.util.fixture.LabelFixture.LABEL5;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER1;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER4;
import static com.issuetracker.util.steps.IssueSteps.마일스톤_목록_조회_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈에_담당자_등록_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈에_담당자_삭제_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈에_등록_되어있는_담당자_목록_조회_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈에_등록_되어있는_라벨_목록_조회_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈에_등록_및_삭제될_담당자_목록_조회_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈에_등록_및_삭제될_라벨_목록_조회_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈에_라벨_등록_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈에_라벨_삭제_요청;
import static com.issuetracker.util.steps.IssueSteps.작성자_목록_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.issuetracker.issue.ui.dto.assignee.AuthorResponses;
import com.issuetracker.milestone.ui.dto.MilestonesSearchResponse;
import com.issuetracker.util.AcceptanceTest;
import com.issuetracker.util.fixture.AssignedLabelFixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class IssueOtherAcceptanceTest extends AcceptanceTest {

	/**
	 * When 마일스톤 목록을 조회하면
	 * Then 마일스톤 목록을 반환한다.
	 */
	@Test
	void 마일스톤_목록을_조회한다() {
		// when
		var response = 마일스톤_목록_조회_요청();

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		마일스톤_목록_검증(response);
	}

	/**
	 * When 작성자 목록을 조회하면
	 * Then 작성자 목록을 반환한다.
	 */
	@Test
	void 작성자_목록을_조회한다() {
		// when
		var response = 작성자_목록_조회_요청();

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		작성자_목록_검증(response);
	}

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
	 * Given 라벨, 이슈, 라벨과 이슈를 매핑하여 생성하고
	 * When 이슈에 등록 되어있는 라벨 목록을 조회하면
	 * Then 이슈에 등록 되어 있는 라벨 목록을 조회할 수 있다.
	 */
	@Test
	void 라벨_목록을_조회한다() {
		// when
		var response = 이슈에_등록_되어있는_라벨_목록_조회_요청();

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		이슈에_등록_되어있는_라벨_목록_검증(response);
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
		var response = 이슈에_담당자_삭제_요청(ISSUE1.getId(), ASSIGNEE1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		이슈에_등록_및_삭제될_담당자_목록에서_삭제된_담당자_검증(ISSUE1.getId(), ASSIGNEE1.getIssueId());
	}

	/**
	 * Given 회원, 라벨, 이슈를 생성하고
	 * When 해당 이슈에 라벨을 등록하면
	 * Then 등록 및 삭제될 라벨 목록에서 등록된 라벨을 찾을 수 있다.
	 */
	@Test
	void 이슈에_라벨을_등록한다() {
		// when
		var response = 이슈에_라벨_등록_요청(ISSUE1.getId(), LABEL1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		이슈에_등록_및_삭제될_라벨_목록에서_등록된_라벨_검증(ISSUE1.getId(), LABEL1.getId());
	}

	/**
	 * Given 회원, 라벨, 이슈를 생성하고
	 * When 해당 이슈에 라벨 등록 시 이미 등록된 라벨이면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 이슈에_라벨을_등록_시_이미_등록된_라벨인_경우_요청이_실패된다() {
		// when
		var response = 이슈에_라벨_등록_요청(ISSUE1.getId(), LABEL5.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 회원, 라벨, 생성하고
	 * When 라벨을 등록 시 존재하지 않는 이슈이면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 이슈에_라벨을_등록_시_존재하지_않는_이슈이면_요청이_실패된다() {
		// given
		Long 존재하지_않는_이슈_아이디 = 20L;

		// when
		var response = 이슈에_라벨_등록_요청(존재하지_않는_이슈_아이디, LABEL1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 회원, 이슈을 생성하고
	 * When 라벨을 등록 시 존재하지 않는 라벨이면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 이슈에_라벨을_등록_시_존재하지_않는_라벨이면_요청이_실패된다() {
		// given
		Long 존재하지_않는_라벨_아이디 = 20L;

		// when
		var response = 이슈에_담당자_등록_요청(ISSUE2.getId(), 존재하지_않는_라벨_아이디);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 회원, 이슈, 라벨을 생성하고
	 * When 해당 이슈에 라벨을 삭제하면
	 * Then 등록 및 삭제될 라벨 목록에서 삭제된 라벨을 찾을 수 없다.
	 */
	@Test
	void 이슈에_라벨을_삭제한다() {
		// when
		var response = 이슈에_라벨_삭제_요청(ISSUE1.getId(), ASSIGNED_LABEL1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		이슈에_등록_및_삭제될_라벨_목록에서_삭제된_라벨_검증(ISSUE1.getId(), ASSIGNED_LABEL1.getLabelId());
	}

	private void 이슈에_등록_및_삭제될_라벨_목록에서_삭제된_라벨_검증(Long id, Long labelId) {
		List<Long> labelIds = 이슈에_등록_및_삭제될_라벨_목록_조회_요청(id).jsonPath().getList("assignedLabels.id", Long.class);

		assertThat(labelIds).doesNotContain(labelId);
	}

	private void 이슈에_등록_및_삭제될_라벨_목록에서_등록된_라벨_검증(Long id, Long labelId) {
		List<Long> labelIds = 이슈에_등록_및_삭제될_라벨_목록_조회_요청(id).jsonPath().getList("assignedLabels.id", Long.class);

		assertThat(labelIds).contains(labelId);
	}

	private void 이슈에_등록_및_삭제될_담당자_목록에서_삭제된_담당자_검증(Long id, Long memberId) {
		List<Long> memberIds = 이슈에_등록_및_삭제될_담당자_목록_조회_요청(id).jsonPath().getList("assignees.id", Long.class);

		assertThat(memberIds).doesNotContain(memberId);
	}

	private void 이슈에_등록_및_삭제될_담당자_목록에서_등록된_담당자_검증(Long id, Long memberId) {
		List<Long> memberIds = 이슈에_등록_및_삭제될_담당자_목록_조회_요청(id).jsonPath().getList("assignees.id", Long.class);

		assertThat(memberIds).contains(memberId);
	}

	private void 마일스톤_목록_검증(ExtractableResponse<Response> response) {
		var findResponse = 마일스톤_목록_조회_요청();
		MilestonesSearchResponse milestonesSearchResponse = findResponse.as(MilestonesSearchResponse.class);

		assertThat(milestonesSearchResponse.getMilestones()).isNotEmpty();
	}

	private void 작성자_목록_검증(ExtractableResponse<Response> response) {
		var findResponse = 작성자_목록_조회_요청();
		AuthorResponses authorResponses = findResponse.as(AuthorResponses.class);

		assertThat(authorResponses.getAuthors()).isNotEmpty();
	}

	private void 이슈에_등록_되어있는_담당자_목록_검증(ExtractableResponse<Response> response) {
		List<Long> ids = response.jsonPath().getList("assignees.id", Long.class);

		assertThat(ids).containsExactly(1L, 2L, 3L, 4L);
	}

	private void 이슈에_등록_되어있는_라벨_목록_검증(ExtractableResponse<Response> response) {
		List<Long> ids = response.jsonPath().getList("labels.id", Long.class);

		assertThat(ids).containsExactly(3L, 4L, 5L, 7L);
	}
}
