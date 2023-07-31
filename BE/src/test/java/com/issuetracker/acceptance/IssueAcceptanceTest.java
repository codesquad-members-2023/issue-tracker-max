package com.issuetracker.acceptance;

import static com.issuetracker.acceptance.IssueSteps.이슈_목록_조회_요청;
import static com.issuetracker.util.LabelFixture.LABEL1;
import static com.issuetracker.util.LabelFixture.LABEL2;
import static com.issuetracker.util.LabelFixture.LABEL4;
import static com.issuetracker.util.LabelFixture.LABEL7;
import static com.issuetracker.util.MemberFixture.USER1;
import static com.issuetracker.util.MemberFixture.USER2;
import static com.issuetracker.util.MemberFixture.USER4;
import static com.issuetracker.util.MilestoneFixture.MILESTONR1;
import static com.issuetracker.util.MilestoneFixture.MILESTONR4;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.issuetracker.issue.ui.dto.IssueSearchRequest;
import com.issuetracker.issue.ui.dto.IssueSearchResponse;
import com.issuetracker.util.AcceptanceTest;
import com.issuetracker.util.LabelFixture;
import com.issuetracker.util.MemberFixture;
import com.issuetracker.util.MilestoneFixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class IssueAcceptanceTest extends AcceptanceTest {

	/**
	 * Given 회원, 마일스톤, 라벨, 이슈를 생성하고
	 * When 검색 조건에 입력하여 이슈 목록을 조회하면
	 * Then 검색 조건에 맞는 이슈 목록을 조회할 수 있다.
	 */
	@Test
	void 이슈를_목록을_조회한다() {
		// when
		IssueSearchRequest issueSearchRequest = new IssueSearchRequest(
			true,
			List.of(USER2.getId()),
			List.of(LABEL4.getId(), LABEL7.getId()),
			MILESTONR1.getId(),
			USER2.getId(),
			true
		);
		var response = 이슈_목록_조회_요청(issueSearchRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		검색_조건에_맞는_이슈_목록_검증(response, issueSearchRequest);
	}

	/**
	 * Given 회원, 마일스톤, 라벨, 이슈를 생성하고
	 * When 검색 조건에 입력하여 이슈 목록을 조회하면
	 * Then 비어있는 이슈 목록을 조회할 수 있다.
	 */
	@Test
	void 검색조건에_맞지_않는_이슈를_목록을_조회한다() {
		// when
		var issueSearchRequest = new IssueSearchRequest(
			false,
			List.of(USER4.getId()),
			List.of(LABEL1.getId(), LABEL2.getId()),
			MILESTONR4.getId(),
			USER1.getId(),
			true
		);
		var response = 이슈_목록_조회_요청(issueSearchRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		비어있는_이슈_목록_검증(response);
	}

	private void 응답_상태코드_검증(ExtractableResponse<Response> response, HttpStatus httpStatus) {
		assertThat(response.statusCode()).isEqualTo(httpStatus.value());
	}

	private void 검색_조건에_맞는_이슈_목록_검증(ExtractableResponse<Response> response, IssueSearchRequest issueSearchRequest) {
		if (issueSearchRequest.getIsOpen() != null) {
			List<Boolean> isOpens = response.jsonPath().getList("issues.isOpen", Boolean.class);

			assertThat(isOpens).containsOnly(issueSearchRequest.getIsOpen());
		}

		if (issueSearchRequest.getLabelIds() != null) {
			List<String> labelTitles = response.jsonPath().getList("issues.labels.title", String.class);
			List<String> expectedTitles = LabelFixture.findAllById(issueSearchRequest.getLabelIds())
				.stream()
				.map(LabelFixture::getTitle)
				.collect(Collectors.toUnmodifiableList());

			assertThat(labelTitles).containsOnly(expectedTitles.toString());
		}

		if (issueSearchRequest.getAuthorId() != null) {
			List<String> authorNickname = response.jsonPath().getList("issues.author", String.class);
			MemberFixture findMember = MemberFixture.findById(issueSearchRequest.getAuthorId());

			assertThat(authorNickname).containsOnly(findMember.getNickname());
		}

		if (issueSearchRequest.getMilestoneId() != null) {
			List<String> milestoneTitles = response.jsonPath().getList("issues.milestone.title", String.class);
			MilestoneFixture findMilestone = MilestoneFixture.findById(issueSearchRequest.getMilestoneId());

			assertThat(milestoneTitles).containsOnly(findMilestone.getTitle());
		}
	}

	private void 비어있는_이슈_목록_검증(ExtractableResponse<Response> response) {
		List<IssueSearchResponse> issueSearchResponses = response.jsonPath().getList("issues", IssueSearchResponse.class);

		assertThat(issueSearchResponses).isEmpty();
	}
}
