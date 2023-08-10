package com.issuetracker.util.steps;

import org.springframework.http.MediaType;

import com.issuetracker.milestone.ui.dto.MilestoneCreateRequest;
import com.issuetracker.milestone.ui.dto.MilestoneUpdateRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MilestoneSteps {

	public static ExtractableResponse<Response> 마일스톤_목록_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/milestones")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 마일스톤_생성_요청(MilestoneCreateRequest milestoneCreateRequest) {
		return RestAssured.given().log().all()
			.body(milestoneCreateRequest)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/api/milestones")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 마일스톤_수정_요청(MilestoneUpdateRequest milestoneUpdateRequest,
		Long milestoneId) {
		return RestAssured.given().log().all()
			.body(milestoneUpdateRequest)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().put("/api/milestones/" + milestoneId)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 마일스톤_삭제_요청(Long milestoneId) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().delete("/api/milestones/" + milestoneId)
			.then().log().all().extract();
	}
}
