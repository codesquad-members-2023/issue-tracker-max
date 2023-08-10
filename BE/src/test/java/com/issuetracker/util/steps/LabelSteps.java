package com.issuetracker.util.steps;

import org.springframework.http.MediaType;

import com.issuetracker.label.ui.dto.LabelCreateRequest;
import com.issuetracker.label.ui.dto.LabelUpdateRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class LabelSteps {

	public static ExtractableResponse<Response> 레이블_목록_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/labels")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 레이블_생성_요청(LabelCreateRequest labelCreateRequest) {
		return RestAssured.given().log().all()
			.body(labelCreateRequest)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/api/labels")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 레이블_수정_요청(LabelUpdateRequest labelUpdateRequest, Long labelId) {
		return RestAssured.given().log().all()
			.body(labelUpdateRequest)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().put("/api/labels/" + labelId)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 레이블_삭제_요청(Long labelId) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().delete("/api/labels/" + labelId)
			.then().log().all().extract();
	}

}
