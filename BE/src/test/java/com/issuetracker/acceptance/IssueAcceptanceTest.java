package com.issuetracker.acceptance;

import static com.issuetracker.acceptance.IssueSteps.이슈_목록_조회_요청;
import static com.issuetracker.acceptance.IssueSteps.이슈_작성_요청;
import static com.issuetracker.util.fixture.LabelFixture.LABEL1;
import static com.issuetracker.util.fixture.LabelFixture.LABEL2;
import static com.issuetracker.util.fixture.LabelFixture.LABEL4;
import static com.issuetracker.util.fixture.LabelFixture.LABEL7;
import static com.issuetracker.util.fixture.MemberFixture.USER1;
import static com.issuetracker.util.fixture.MemberFixture.USER2;
import static com.issuetracker.util.fixture.MemberFixture.USER4;
import static com.issuetracker.util.fixture.MilestoneFixture.MILESTONR1;
import static com.issuetracker.util.fixture.MilestoneFixture.MILESTONR4;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.issuetracker.issue.ui.dto.IssueCreateRequest;
import com.issuetracker.issue.ui.dto.IssueSearchRequest;
import com.issuetracker.issue.ui.dto.IssueSearchResponse;
import com.issuetracker.issue.ui.dto.IssuesSearchResponse;
import com.issuetracker.util.AcceptanceTest;
import com.issuetracker.util.fixture.LabelFixture;
import com.issuetracker.util.fixture.MemberFixture;
import com.issuetracker.util.fixture.MilestoneFixture;

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

	/**
	 * Given 회원, 마일스톤, 라벨을 생성하고 
	 * When 이슈를 생성하면
	 * Then 이슈 목록 조회 시 생성한 이슈를 확인 할 수 있다.
	 */
	@Test
	void 이슈를_생성한다() {
		// when
		IssueCreateRequest issueCreateRequest = new IssueCreateRequest(
			"제목",
			"내용",
			List.of(USER1.getId()),
			List.of(LABEL1.getId()),
			MILESTONR1.getId()
		);
		var response = 이슈_작성_요청(issueCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.CREATED);
		이슈_목록_조회_시_생성된_이슈를_검증(response, issueCreateRequest);
	}

	/**
	 * Given 회원, 라벨을 생성하고
	 * When 존재하지 않는 마일스톤으로 이슈를 생성하면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 존재하지_않는_마일스톤으로_이슈를_생성하면_요청이_실패된다() {
		// given
		Long 존재하지_않는_마일스톤_아이디 = 10L;

		// when
		IssueCreateRequest issueCreateRequest = new IssueCreateRequest(
			"제목",
			"내용",
			List.of(USER1.getId()),
			List.of(LABEL1.getId()),
			존재하지_않는_마일스톤_아이디
		);
		var response = 이슈_작성_요청(issueCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 마일스톤, 라벨을 생성하고
	 * When 존재하지 않는 담당자로 이슈를 생성하면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 존재하지_않는_담당자로_이슈를_생성하면_요청이_실패된다() {
		// given
		Long 존재하지_않는_담당자_아이디 = 10L;

		// when
		IssueCreateRequest issueCreateRequest = new IssueCreateRequest(
			"제목",
			"내용",
			List.of(존재하지_않는_담당자_아이디),
			List.of(LABEL1.getId()),
			MILESTONR1.getId()
		);
		var response = 이슈_작성_요청(issueCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 회원, 마일스톤을 생성하고
	 * When 존재하지 않는 라벨로 이슈를 생성하면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 존재하지_않는_라벨로_이슈를_생성하면_요청이_실패된다() {
		// given
		Long 존재하지_않는_라벨_아이디 = 10L;

		// when
		IssueCreateRequest issueCreateRequest = new IssueCreateRequest(
			"제목",
			"내용",
			List.of(USER1.getId()),
			List.of(존재하지_않는_라벨_아이디),
			MILESTONR1.getId()
		);
		var response = 이슈_작성_요청(issueCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
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

	private void 이슈_목록_조회_시_생성된_이슈를_검증(ExtractableResponse<Response> response, IssueCreateRequest issueCreateRequest) {
		var findResponse =  이슈_목록_조회_요청(new IssueSearchRequest(true, null, null, null, null, null));
		List<IssueSearchResponse> issueSearchResponses = findResponse.as(IssuesSearchResponse.class).getIssues();
		IssueSearchResponse lastIssueSearchResponse = issueSearchResponses.get(issueSearchResponses.size() - 1);

		assertThat(lastIssueSearchResponse.getId()).isEqualTo(response.jsonPath().getLong("id"));
		assertThat(lastIssueSearchResponse.getTitle()).isEqualTo(issueCreateRequest.getTitle());
		assertThat(lastIssueSearchResponse.getIsOpen()).isTrue();
		assertThat(lastIssueSearchResponse.getLabels().size()).isEqualTo(issueCreateRequest.getLabelIds().size());
	}
}
