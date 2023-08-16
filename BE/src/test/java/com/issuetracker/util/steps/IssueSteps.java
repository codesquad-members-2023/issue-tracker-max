package com.issuetracker.util.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;

import com.issuetracker.issue.ui.dto.IssueCreateRequest;
import com.issuetracker.issue.ui.dto.IssueSearchRequest;
import com.issuetracker.issue.ui.dto.IssueUpdateAllOpenRequest;
import com.issuetracker.issue.ui.dto.IssueUpdateRequest;
import com.issuetracker.issue.ui.dto.comment.IssueCommentCreateRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class IssueSteps {

	public static ExtractableResponse<Response> 이슈_목록_조회_요청(IssueSearchRequest issueSearchRequest) {
		Map<String, Object> params = new HashMap<>();
		params.put("isOpen", issueSearchRequest.getIsOpen());
		params.put("assigneeNames", issueSearchRequest.getAssigneeNames());
		params.put("labelTitles", issueSearchRequest.getLabelTitles());
		params.put("milestoneTitle", issueSearchRequest.getMilestoneTitle());
		params.put("authorName", issueSearchRequest.getAuthorName());
		params.put("isCommentedByMe", issueSearchRequest.getIsCommentedByMe());
		return RestAssured.given().log().all()
			.queryParams(params)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈_목록_조회_요청(Map<String, Object> params) {
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

	public static ExtractableResponse<Response> 이슈_상세_조회_요청(Long id) {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/issues/{id}", id)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈_열림_닫힘_수정_요청(boolean isOpen, Long id) {
		return RestAssured.given().log().all()
			.body(new IssueUpdateRequest(isOpen, null, null, null))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().patch("/api/issues/{id}/open", id)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈_열림_닫힘_일괄_수정_요쳥(boolean isOpen, List<Long> ids) {
		return RestAssured.given().log().all()
			.body(new IssueUpdateAllOpenRequest(ids, isOpen))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().patch("/api/issues/open-all")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈_제목_수정_요청(Long id, String title) {
		return RestAssured.given().log().all()
			.body(new IssueUpdateRequest(null, title, null, null))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().patch("/api/issues/{id}/title", id)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈_내용_수정_요청(Long id, String content) {
		return RestAssured.given().log().all()
			.body(new IssueUpdateRequest(null, null, content, null))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().patch("/api/issues/{id}/content", id)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈_마일스톤_수정_요청(Long id, Long milestoneId) {
		return RestAssured.given().log().all()
			.body(new IssueUpdateRequest(null, null, null, milestoneId))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().patch("/api/issues/{id}/milestone", id)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈_삭제_요청(Long id) {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().delete("/api/issues/{id}", id)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈_댓글_작성_요청(Long id, String content) {
		return RestAssured.given().log().all()
			.body(new IssueCommentCreateRequest(content))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/api/issues/{id}/comments", id)
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> 이슈_댓글_내용_수정_요청(Long id, Long issueCommentId, String content) {
		return RestAssured.given().log().all()
			.body(new IssueCommentCreateRequest(content))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().patch("/api/issues/{id}/comments/{comment-id}", id, issueCommentId)
			.then().log().all().extract();
	}
}
