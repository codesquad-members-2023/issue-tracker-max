package com.issuetracker.acceptance;

import static com.issuetracker.util.steps.MilestoneSteps.*;

import java.util.List;
import java.util.Objects;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.issuetracker.milestone.ui.dto.MilestoneCreateRequest;
import com.issuetracker.milestone.ui.dto.MilestoneResponse;
import com.issuetracker.milestone.ui.dto.MilestoneUpdateRequest;
import com.issuetracker.milestone.ui.dto.MilestonesResponse;
import com.issuetracker.util.AcceptanceTest;
import com.issuetracker.util.steps.MilestoneSteps;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MilestoneAcceptanceTest extends AcceptanceTest {

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 작성 하면
	 * Then 마일스톤 목록 조회 시 생성한 마일스톤을 확인 할 수 있다.
	 */
	@Test
	void 마일스톤을_생성한다() {
		// given
		MilestoneCreateRequest milestoneCreateRequest = new MilestoneCreateRequest(
			"마일스톤 제목",
			"마일스톤 설명",
			"2023-08-09"
		);

		// when
		var response = MilestoneSteps.마일스톤_생성_요청(milestoneCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.CREATED);
		마일스톤_목록_조회_시_생성된_마일스톤을_검증(response, milestoneCreateRequest);
	}

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 생성시 제목이 공백이면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 마일스톤을_생성시_제목을_입력하지_않으면_400에러를_반환한다() {
		// given
		MilestoneCreateRequest milestoneCreateRequest = new MilestoneCreateRequest(
			"",
			"마일스톤 설명",
			"2023-08-09"
		);

		// when
		var response = 마일스톤_생성_요청(milestoneCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 생성시 마일스톤 설명은 없어도
	 * Then 마일스톤을 생성한다.
	 */
	@Test
	void 마일스톤을_생성시_마일스톤_설명은_없어도_마일스톤을_생성한다() {
		// given
		MilestoneCreateRequest milestoneCreateRequest = new MilestoneCreateRequest(
			"마일스톤 제목",
			null,
			"2023-08-09"
		);

		// when
		var response = 마일스톤_생성_요청(milestoneCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.CREATED);
		마일스톤_목록_조회_시_생성된_마일스톤을_검증(response, milestoneCreateRequest);
	}

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 생성시 데드라인이 yyyy.MM.dd 형식이 아니면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 마일스톤을_생성시_데드라인_형식이_올바르지_않으면_400에러를_반환한다() {
		// given
		MilestoneCreateRequest milestoneCreateRequest = new MilestoneCreateRequest(
			"마일스톤 제목",
			"마일스톤 설명",
			"2023.09.09"
		);

		// when
		var response = 마일스톤_생성_요청(milestoneCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 수정 하면
	 * Then 마일스톤 목록 조회 시 수정한 마일스톤을 확인 할 수 있다.
	 */
	@Test
	void 마일스톤을_수정한다() {
		// given
		Long milestoneId = 2L;
		MilestoneUpdateRequest milestoneUpdateRequest = new MilestoneUpdateRequest(
			"수정된 마일스톤 제목",
			"수정된 마일스톤 설명",
			"2023-10-10"
		);

		// when
		var response = 마일스톤_수정_요청(milestoneUpdateRequest, milestoneId);

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		마일스톤_목록_조회_시_수정된_마일스톤을_검증(response, milestoneUpdateRequest, milestoneId);
	}

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 수정시 제목이 공백이면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 마일스톤을_수정시_제목이_공백이면_400에러를_반환한다() {
		// given
		Long milestoneId = 2L;
		MilestoneUpdateRequest milestoneUpdateRequest = new MilestoneUpdateRequest(
			"",
			"수정된 마일스톤 설명",
			"2023-10-10"
		);

		// when
		var response = 마일스톤_수정_요청(milestoneUpdateRequest, milestoneId);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 수정시 마일스톤 설명은 없어도
	 * Then 마일스톤을 수정한다.
	 */
	@Test
	void 마일스톤을_수정시_마일스톤_설명은_없어도_마일스톤을_수정한다() {
		// given
		Long milestoneId = 2L;
		MilestoneUpdateRequest milestoneUpdateRequest = new MilestoneUpdateRequest(
			"수정된 마일스톤 제목",
			null,
			"2023-10-10"
		);

		// when
		var response = 마일스톤_수정_요청(milestoneUpdateRequest, milestoneId);

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		마일스톤_목록_조회_시_수정된_마일스톤을_검증(response, milestoneUpdateRequest, milestoneId);
	}

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 수정시 데드라인이 yyyy-MM-dd 형식이 아니면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 마일스톤을_수정시_데드라인_형식이_올바르지_않으면_400에러를_반환한다() {
		// given
		Long milestoneId = 2L;
		MilestoneUpdateRequest milestoneUpdateRequest = new MilestoneUpdateRequest(
			"수정된 마일스톤 제목",
			"수정된 마일스톤 설명",
			"2023.10.10"
		);

		// when
		var response = 마일스톤_수정_요청(milestoneUpdateRequest, milestoneId);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 삭제할 마일스톤 id를 결정하고
	 * When 마일스톤을 삭제 하면
	 * Then 마일스톤 목록 조회 시 삭제된 마일스톤을 찾을 수 없다.
	 */
	@Test
	void 마일스톤을_삭제한다() {
		// given
		Long milestoneId = 2L;

		// when
		var response = 마일스톤_삭제_요청(milestoneId);

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		마일스톤_목록_조회_시_삭제된_마일스톤을_검증(response, milestoneId);
	}

	/**
	 * Given 삭제할 마일스톤 id를 결정하고
	 * When 마일스톤을 삭제할 때 없는 마일스톤을 삭제하려고 하면
	 * Then 404 에러가 발생한다.
	 */
	@Test
	void 마일스톤_삭제시_없는_마일스톤을_삭제하려고_하면_404_에러가_발생한다() {
		// given
		Long milestoneId = 999L;

		// when
		var response = 마일스톤_삭제_요청(milestoneId);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	private void 마일스톤_목록_조회_시_생성된_마일스톤을_검증(ExtractableResponse<Response> response,
		MilestoneCreateRequest milestoneCreateRequest) {
		var findResponse = 열린_마일스톤_목록_조회_요청();
		List<MilestoneResponse> milestoneResponse = findResponse.as(MilestonesResponse.class).getMilestones();
		MilestoneResponse lastMilestoneResponse = milestoneResponse.get(0);

		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(lastMilestoneResponse.getId()).isEqualTo(response.jsonPath().getLong("id"));
		softAssertions.assertThat(lastMilestoneResponse.getTitle()).isEqualTo(milestoneCreateRequest.getTitle());
		softAssertions.assertThat(lastMilestoneResponse.getDescription())
			.isEqualTo(milestoneCreateRequest.getDescription());
		softAssertions.assertThat(lastMilestoneResponse.getDeadline())
			.isEqualTo(milestoneCreateRequest.getDeadline());
		softAssertions.assertAll();
	}

	private void 마일스톤_목록_조회_시_수정된_마일스톤을_검증(ExtractableResponse<Response> response,
		MilestoneUpdateRequest milestoneUpdateRequest, Long milestoneId) {
		var findResponse = 열린_마일스톤_목록_조회_요청();
		List<MilestoneResponse> milestoneResponse = findResponse.as(MilestonesResponse.class).getMilestones();
		MilestoneResponse lastMilestoneResponse = milestoneResponse.stream()
			.filter(m -> Objects.equals(m.getId(), milestoneId))
			.findAny()
			.orElseThrow();

		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(lastMilestoneResponse.getId()).isEqualTo(milestoneId);
		softAssertions.assertThat(lastMilestoneResponse.getTitle()).isEqualTo(milestoneUpdateRequest.getTitle());
		softAssertions.assertThat(lastMilestoneResponse.getDescription())
			.isEqualTo(milestoneUpdateRequest.getDescription());
		softAssertions.assertThat(lastMilestoneResponse.getDeadline())
			.isEqualTo(milestoneUpdateRequest.getDeadline());
		softAssertions.assertAll();
	}

	private void 마일스톤_목록_조회_시_삭제된_마일스톤을_검증(ExtractableResponse<Response> response, Long milestoneId) {
		var findResponse = 열린_마일스톤_목록_조회_요청();
		List<MilestoneResponse> milestoneResponses = findResponse.as(MilestonesResponse.class).getMilestones();

		for (MilestoneResponse milestoneResponse : milestoneResponses) {
			Assertions.assertThat(milestoneResponse.getId()).isNotEqualTo(milestoneId);
		}
	}
}
