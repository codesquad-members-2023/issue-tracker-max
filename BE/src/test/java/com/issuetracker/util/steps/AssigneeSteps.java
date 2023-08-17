package com.issuetracker.util.steps;

import org.springframework.http.MediaType;

import com.issuetracker.issue.ui.dto.assignee.AssigneeCreateRequest;
import com.issuetracker.util.JwtTokenForTest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AssigneeSteps {

	public static ExtractableResponse<Response> 이슈에_담당자_등록_요청(Long id, Long memberId) {
		return RestAssured.given().log().all().auth().oauth2(JwtTokenForTest.accessToken)
			.body(new AssigneeCreateRequest(memberId))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/api/issues/{id}/assignees", id)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈에_담당자_삭제_요청(Long id, Long assigneeId) {
		return RestAssured.given().log().all().auth().oauth2(JwtTokenForTest.accessToken)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().delete("/api/issues/{id}/assignees/{assignee-id}", id, assigneeId)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈에_등록_되어있는_담당자_목록_조회_요청() {
		return RestAssured.given().log().all().auth().oauth2(JwtTokenForTest.accessToken)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues/assignees")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈에_등록_및_삭제될_라벨_목록_조회_요청(Long id) {
		return RestAssured.given().log().all().auth().oauth2(JwtTokenForTest.accessToken)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues/{id}/label-candidates", id)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈에_등록_및_삭제될_담당자_목록_조회_요청(Long id) {
		return RestAssured.given().log().all().auth().oauth2(JwtTokenForTest.accessToken)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues/{id}/assignee-candidates", id)
			.then().log().all().extract();
	}
}
