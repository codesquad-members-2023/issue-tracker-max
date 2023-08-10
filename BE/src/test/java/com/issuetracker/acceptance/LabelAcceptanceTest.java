package com.issuetracker.acceptance;

import static com.issuetracker.util.steps.LabelSteps.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.issuetracker.label.ui.dto.LabelCreateRequest;
import com.issuetracker.label.ui.dto.LabelResponse;
import com.issuetracker.label.ui.dto.LabelUpdateRequest;
import com.issuetracker.label.ui.dto.LabelsResponse;
import com.issuetracker.util.AcceptanceTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class LabelAcceptanceTest extends AcceptanceTest {

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 작성 하면
	 * Then 레이블 목록 조회 시 생성한 레이블을 확인 할 수 있다.
	 */
	@Test
	void 레이블을_생성한다() {
		// given
		LabelCreateRequest labelCreateRequest = new LabelCreateRequest(
			"레이블 제목",
			"레이블 설명",
			"#ffffff"
		);

		// when
		var response = 레이블_생성_요청(labelCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.CREATED);
		레이블_목록_조회_시_생성된_레이블을_검증(response, labelCreateRequest);
	}

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 생성시 제목이 공백이면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 레이블을_생성시_제목을_입력하지_않으면_400에러를_반환한다() {
		// given
		LabelCreateRequest labelCreateRequest = new LabelCreateRequest(
			"",
			"레이블 설명",
			"#ffffff"
		);

		// when
		var response = 레이블_생성_요청(labelCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 생성시 레이블 설명은 없어도
	 * Then 레이블을 생성한다.
	 */
	@Test
	void 레이블을_생성시_레이블_설명은_없어도_레이블을_생성한다() {
		// given
		LabelCreateRequest labelCreateRequest = new LabelCreateRequest(
			"레이블 제목",
			null,
			"#ffffff"
		);

		// when
		var response = 레이블_생성_요청(labelCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.CREATED);
		레이블_목록_조회_시_생성된_레이블을_검증(response, labelCreateRequest);
	}

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 생성시 색상이 HEX 코드가 아니면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 레이블을_생성시_색상이_HEX_코드가_아니면_400에러를_반환한다() {
		// given
		LabelCreateRequest labelCreateRequest = new LabelCreateRequest(
			"레이블 제목",
			"레이블 설명",
			"#HEX코드가아니다"
		);

		// when
		var response = 레이블_생성_요청(labelCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 수정 하면
	 * Then 레이블 목록 조회 시 수정한 레이블을 확인 할 수 있다.
	 */
	@Test
	void 레이블을_수정한다() {
		// given
		Long labelId = 1L;
		LabelUpdateRequest labelUpdateRequest = new LabelUpdateRequest(
			"수정된 레이블 제목",
			"수정된 레이블 설명",
			"#000000"
		);

		// when
		var response = 레이블_수정_요청(labelUpdateRequest, labelId);

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		레이블_목록_조회_시_수정된_레이블을_검증(response, labelUpdateRequest, labelId);
	}

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 수정시 제목이 공백이면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 레이블을_수정시_제목이_공백이면_400에러를_반환한다() {
		// given
		Long labelId = 1L;
		LabelUpdateRequest labelUpdateRequest = new LabelUpdateRequest(
			"",
			"수정된 레이블 설명",
			"#000000"
		);

		// when
		var response = 레이블_수정_요청(labelUpdateRequest, labelId);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 수정시 레이블 설명은 없어도
	 * Then 레이블을 수정한다.
	 */
	@Test
	void 레이블을_수정시_레이블_설명은_없어도_레이블을_수정한다() {
		// given
		Long labelId = 1L;
		LabelUpdateRequest labelUpdateRequest = new LabelUpdateRequest(
			"수정된 레이블 제목",
			null,
			"#000000"
		);

		// when
		var response = 레이블_수정_요청(labelUpdateRequest, labelId);

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		레이블_목록_조회_시_수정된_레이블을_검증(response, labelUpdateRequest, labelId);
	}

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 수정시 색상이 HEX 코드가 아니면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 레이블을_수정시_색상이_HEX_코드가_아니면_400에러를_반환한다() {
		// given
		Long labelId = 1L;
		LabelUpdateRequest labelUpdateRequest = new LabelUpdateRequest(
			"수정된 레이블 제목",
			"수정된 레이블 설명",
			"#HEX코드가아니다"
		);

		// when
		var response = 레이블_수정_요청(labelUpdateRequest, labelId);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 삭제할 레이블 id를 결정하고
	 * When 레이블을 삭제 하면
	 * Then 레이블 목록 조회 시 삭제된 레이블을 찾을 수 없다.
	 */
	@Test
	void 레이블을_삭제한다() {
		// given
		Long labelId = 1L;

		// when
		var response = 레이블_삭제_요청(labelId);

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		레이블_목록_조회_시_삭제된_레이블을_검증(response, labelId);
	}

	/**
	 * Given 삭제할 레이블 id를 결정하고
	 * When 레이블을 삭제할 때 없는 레이블을 삭제하려고 하면
	 * Then 404 에러가 발생한다.
	 */
	@Test
	void 레이블_삭제시_없는_레이블을_삭제하려고_하면_404_에러가_발생한다() {
		// given
		Long labelId = 999L;

		// when
		var response = 레이블_삭제_요청(labelId);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	private void 레이블_목록_조회_시_생성된_레이블을_검증(ExtractableResponse<Response> response,
		LabelCreateRequest labelCreateRequest) {
		var findResponse = 레이블_목록_조회_요청();
		List<LabelResponse> labelResponse = findResponse.as(LabelsResponse.class).getLabels();
		LabelResponse lastLabelResponse = labelResponse.get(labelResponse.size() - 1);

		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(lastLabelResponse.getId()).isEqualTo(response.jsonPath().getLong("id"));
		softAssertions.assertThat(lastLabelResponse.getTitle()).isEqualTo(labelCreateRequest.getTitle());
		softAssertions.assertThat(lastLabelResponse.getColor()).isEqualTo(labelCreateRequest.getColor());
		softAssertions.assertAll();
	}

	private void 레이블_목록_조회_시_수정된_레이블을_검증(ExtractableResponse<Response> response,
		LabelUpdateRequest labelUpdateRequest, Long labelId) {
		var findResponse = 레이블_목록_조회_요청();
		List<LabelResponse> labelResponse = findResponse.as(LabelsResponse.class).getLabels();
		LabelResponse lastLabelResponse = labelResponse.get(Long.valueOf(labelId - 1L).intValue());

		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(lastLabelResponse.getId()).isEqualTo(labelId);
		softAssertions.assertThat(lastLabelResponse.getTitle()).isEqualTo(labelUpdateRequest.getTitle());
		softAssertions.assertThat(lastLabelResponse.getColor()).isEqualTo(labelUpdateRequest.getColor());
		softAssertions.assertAll();
	}

	private void 레이블_목록_조회_시_삭제된_레이블을_검증(ExtractableResponse<Response> response, Long labelId) {
		var findResponse = 레이블_목록_조회_요청();
		List<LabelResponse> labelResponses = findResponse.as(LabelsResponse.class).getLabels();

		for (LabelResponse labelResponse : labelResponses) {
			Assertions.assertThat(labelResponse.getId()).isNotEqualTo(labelId);
		}
	}
}
