package com.issuetracker.acceptance;

import static com.issuetracker.util.steps.IssueSteps.마일스톤_목록_조회_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈에_등록_되어있는_담당자_목록_조회_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈에_등록_되어있는_라벨_목록_조회_요청;
import static com.issuetracker.util.steps.IssueSteps.작성자_목록_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.issuetracker.issue.ui.dto.AuthorResponses;
import com.issuetracker.milestone.ui.dto.MilestonesSearchResponse;
import com.issuetracker.util.AcceptanceTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class IssueFilterAcceptanceTest extends AcceptanceTest {

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
