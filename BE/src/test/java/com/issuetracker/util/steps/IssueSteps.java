package com.issuetracker.util.steps;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

import com.issuetracker.issue.ui.dto.IssueCreateRequest;
import com.issuetracker.issue.ui.dto.IssueSearchRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class IssueSteps {

	public static ExtractableResponse<Response> 이슈_목록_조회_요청(IssueSearchRequest issueSearchRequest) {
		Map<String, Object> params =  new HashMap<>();
		params.put("isOpen", issueSearchRequest.getIsOpen());
		params.put("assigneeIds", issueSearchRequest.getAssigneeIds());
		params.put("labelIds", issueSearchRequest.getLabelIds());
		params.put("milestoneId", issueSearchRequest.getMilestoneId());
		params.put("authorId", issueSearchRequest.getAuthorId());
		params.put("isCommentedByMe", issueSearchRequest.getIsCommentedByMe());
		return RestAssured.given().log().all()
			.queryParams(params)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈_작성_요청(IssueCreateRequest issueCreateRequest) {
		return RestAssured.given().log().all()
			.body(issueCreateRequest)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/api/issues")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 마일스톤_목록_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues/milestones")
			.then().log().all().extract();
  }
  
	public static ExtractableResponse<Response> 작성자_목록_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues/authors")
			.then().log().all().extract();
  }
  
	public static ExtractableResponse<Response> 이슈에_등록_되어있는_담당자_목록_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues/assignees")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈에_등록_되어있는_라벨_목록_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues/labels")
			.then().log().all().extract();
	}
}
