package com.issuetracker.acceptance;

import static com.issuetracker.util.fixture.IssueFixture.ISSUE1;
import static com.issuetracker.util.fixture.LabelFixture.LABEL1;
import static com.issuetracker.util.fixture.LabelFixture.LABEL2;
import static com.issuetracker.util.fixture.LabelFixture.LABEL4;
import static com.issuetracker.util.fixture.LabelFixture.LABEL7;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER1;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER2;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER3;
import static com.issuetracker.util.fixture.MemberFixture.MEMBER4;
import static com.issuetracker.util.fixture.MilestoneFixture.MILESTON1;
import static com.issuetracker.util.fixture.MilestoneFixture.MILESTON2;
import static com.issuetracker.util.fixture.MilestoneFixture.MILESTON4;
import static com.issuetracker.util.steps.IssueSteps.이슈_내용_수정_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈_마일스톤_수정_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈_목록_조회_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈_삭제_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈_상세_조회_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈_열림_닫힘_수정_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈_작성_요청;
import static com.issuetracker.util.steps.IssueSteps.이슈_제목_수정_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import com.issuetracker.issue.ui.dto.IssueCreateRequest;
import com.issuetracker.issue.ui.dto.IssueDetailResponse;
import com.issuetracker.issue.ui.dto.IssueSearchRequest;
import com.issuetracker.issue.ui.dto.IssueSearchResponse;
import com.issuetracker.issue.ui.dto.IssuesSearchResponse;
import com.issuetracker.util.AcceptanceTest;
import com.issuetracker.util.fixture.IssueCommentFixture;
import com.issuetracker.util.fixture.IssueFixture;
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
	@ParameterizedTest
	@MethodSource("providerIssueSearchRequest")
	void 이슈를_목록을_조회한다(IssueSearchRequest issueSearchRequest) {
		// when
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
			List.of(MEMBER4.getId()),
			List.of(LABEL1.getId(), LABEL2.getId()),
			MILESTON4.getId(),
			MEMBER1.getId(),
			true
		);
		var response = 이슈_목록_조회_요청(issueSearchRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		비어있는_이슈_목록_검증(response);
	}

	/**
	 * Given 회원, 마일스톤, 라벨, 이슈를 생성하고
	 * When 이슈 목록 조회 시 검색 조건이 아니면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 이슈를_목록_조회_시_검색_조건이_아니면_요청이_실패된다() {
		// when
		Map<String, Object> params = new HashMap<>();
		params.put("notSearchCondition", "test");
		var response = 이슈_목록_조회_요청(params);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 회원, 마일스톤, 라벨을 생성하고 
	 * When 이슈를 작성하면
	 * Then 이슈 목록 조회 시 생성한 이슈를 확인 할 수 있다.
	 */
	@Test
	void 이슈를_작성한다() {
		// when
		IssueCreateRequest issueCreateRequest = new IssueCreateRequest(
			"제목",
			"내용",
			List.of(MEMBER1.getId()),
			List.of(LABEL1.getId()),
			MILESTON1.getId()
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
			List.of(MEMBER1.getId()),
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
			MILESTON1.getId()
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
			List.of(MEMBER1.getId()),
			List.of(존재하지_않는_라벨_아이디),
			MILESTON1.getId()
		);
		var response = 이슈_작성_요청(issueCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 라벨, 마일스톤, 회원, 이슈를 생성하고
	 * When 이슈 상세 조회하면
	 * Then 이슈에 등록되어 있는 라벨, 마일스톤, 작성자를 조회할 수 있다.
	 */
	@Test
	void 이슈_상세를_조회한다() {
		// when
		var response = 이슈_상세_조회_요청(ISSUE1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		이슈_상세_조회_검증(response, ISSUE1);
	}

	/**
	 * When 존재하지 않는 이슈를 상세 조회하면
	 * THen 요청이 실패된다.
	 */
	@Test
	void 존재하지_않는_이슈를_상세_조회한다() {
		// given
		Long 존재하지_않는_이슈_아이디 = 20L;

		// when
		var response = 이슈_상세_조회_요청(존재하지_않는_이슈_아이디);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 라벨, 마일스톤, 회원, 이슈를 생성하고
	 * When 이슈 열림/닫힘 수정하면
	 * Then 이슈_상세 조회에서 수정된 값을 확인할 수 있다.
	 */
	@Test
	void 이슈_열림_닫힘_수정한다() {
		// when
		var response = 이슈_열림_닫힘_수정_요청(ISSUE1.getId(), false);

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		이슈_상세_조회에서_수정한_값_검증(ISSUE1.getId(), "isOpen", false);
	}

	/**
	 * When 존재하지 않는 이슈로 열림/닫힘 수정하면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 존재하지_않는_이슈로_열림_닫힘_수정_시_요청이_실패된다() {
		// given
		Long 존재하지_않는_이슈_아이디 = 20L;

		// when
		var response = 이슈_열림_닫힘_수정_요청(존재하지_않는_이슈_아이디, false);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 라벨, 마일스톤, 회원, 이슈를 생성하고
	 * When 이슈 제목을 수정하면
	 * Then 이슈_상세 조회에서 수정된 값을 확인할 수 있다.
	 */
	@Test
	void 이슈_제목을_수정한다() {
		// when
		var response = 이슈_제목_수정_요청(ISSUE1.getId(), "수정한 제목");

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		이슈_상세_조회에서_수정한_값_검증(ISSUE1.getId(), "title", "수정한 제목");
	}

	/**
	 * Given 라벨, 마일스톤, 회원, 이슈를 생성하고
	 * When 이슈 제목을 수정 시 공백 이거나 100글자 초과하면
	 * Then 요청이 실패된다.
	 */
	@ParameterizedTest
	@NullAndEmptySource
	@MethodSource("providerUpdateTitle")
	void 이슈_제목_수정_시_공백_이거나_100글자_초과하면_요청이_실패된다(String title) {
		// when
		var response = 이슈_제목_수정_요청(ISSUE1.getId(), title);

		// when
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * When 존재하지 않는 이슈로 제목을 수정하면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 존재하지_않는_이슈로_제목을_수정_시_요청이_실패된다() {
		// given
		Long 존재하지_않는_이슈_아이디 = 20L;

		// when
		var response = 이슈_제목_수정_요청(존재하지_않는_이슈_아이디, "수정한 제목");

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 라벨, 마일스톤, 회원, 이슈를 생성하고
	 * When 이슈 내용을 수정하면
	 * Then 이슈 상세 조회에서 수정된 값을 확인할 수 있다.
	 */
	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = "## 수정한 내용")
	void 이슈_내용을_수정한다(String content) {
		// when
		var response = 이슈_내용_수정_요청(ISSUE1.getId(), content);

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		이슈_상세_조회에서_수정한_값_검증(ISSUE1.getId(), "content", content);
	}

	/**
	 * Given 라벨, 마일스톤, 회원, 이슈를 생성하고
	 * When 이슈 내용을 수정 시 3000글자 초과하면
	 * Then 요청이 실패된다.
	 */
	@ParameterizedTest
	@NullSource
	@MethodSource("providerUpdateContent")
	void 이슈_내용_수정_시_3000글자_초과하면_요청이_실패된다(String content) {
		// when
		var response = 이슈_내용_수정_요청(ISSUE1.getId(), content);

		// when
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * When 존재하지 않는 이슈로 내용을 수정하면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 존재하지_않는_이슈로_내용을_수정_시_요청이_실패된다() {
		// given
		Long 존재하지_않는_이슈_아이디 = 20L;

		// when
		var response = 이슈_내용_수정_요청(존재하지_않는_이슈_아이디, "## 수정한 내용");

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 라벨, 마일스톤, 회원, 이슈를 생성하고
	 * When 이슈에 마일스톤을 수정하면
	 * Then 이슈 상세 조회에서 수정된 값을 확인할 수 있다.
	 */
	@Test
	void 이슈에_마일스톤을_등록한다() {
		// when
		var response = 이슈_마일스톤_수정_요청(ISSUE1.getId(), MILESTON2.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		이슈_상세_조회에서_수정한_값_검증(ISSUE1.getId(), "milestone.id", MILESTON2.getId());
	}

	/**
	 * Given 라벨, 마일스톤, 회원, 이슈를 생성하고
	 * When 이슈에 마일스톤을 수정하면
	 * Then 이슈 상세 조회에서 수정된 값을 확인할 수 있다.
	 */
	@Test
	void 이슈에_등록된_마일스톤_제거한다() {
		// when
		var response = 이슈_마일스톤_수정_요청(ISSUE1.getId(), null);

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		이슈_상세_조회에서_수정한_값_검증(ISSUE1.getId(), "milestone", null);
	}

	/**
	 * When 존재하지 않는 이슈로 마일스톤을 수정하면
	 * Then 요청이 실패된다.
	 */
	@Test
	void 존재하지_않는_이슈로_마일스톤을_수정_시_요청이_실패된다() {
		// given
		Long 존재하지_않는_이슈_아이디 = 20L;

		// when
		var response = 이슈_마일스톤_수정_요청(존재하지_않는_이슈_아이디, MILESTON2.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}


	/**
	 * Given 라벨, 마일스톤, 회원, 이슈를 생성하고
	 * When 이슈를 삭제하면
	 * Then 이슈 상세 조회에서 삭제 되었는지 확인할 수 있다.
	 */
	@Test
	void 이슈를_삭제한다() {
		// when
		var response = 이슈_삭제_요청(ISSUE1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		이슈_상세_조회에서_삭제_되었는지_검증(ISSUE1.getId());
	}

	private static Stream<Arguments> providerIssueSearchRequest() {
		return Stream.of(
			Arguments.of(
				new IssueSearchRequest(
					true,
					List.of(MEMBER2.getId(), MEMBER3.getId()),
					List.of(LABEL4.getId(), LABEL7.getId()),
					MILESTON2.getId(),
					MEMBER4.getId(),
					null)
			),
			Arguments.of(
				new IssueSearchRequest(
					true,
					null,
					List.of(LABEL4.getId(), LABEL7.getId()),
					MILESTON1.getId(),
					MEMBER2.getId(),
					true)
			),
			Arguments.of(
				new IssueSearchRequest(
					null,
					null,
					null,
					MILESTON1.getId(),
					MEMBER2.getId(),
					true)
			)
		);
	}

	private static Stream<Arguments> providerUpdateTitle() {
		return Stream.of(
			Arguments.of(
				"title".repeat(100)
			)
		);
	}

	private static Stream<Arguments> providerUpdateContent() {
		return Stream.of(
			Arguments.of(
				"content".repeat(3000)
			)
		);
	}

	private void 검색_조건에_맞는_이슈_목록_검증(ExtractableResponse<Response> response, IssueSearchRequest issueSearchRequest) {
		int issueOpenCount = response.jsonPath().getInt("metadata.issueOpenCount");
		int issueCloseCount = response.jsonPath().getInt("metadata.issueCloseCount");
		int labelCount = response.jsonPath().getInt("metadata.labelCount");
		int milestoneCount = response.jsonPath().getInt("metadata.milestoneCount");

		Assertions.assertAll(
			() -> assertThat(issueOpenCount).isEqualTo(6),
			() -> assertThat(issueCloseCount).isEqualTo(6),
			() -> assertThat(labelCount).isEqualTo(7),
			() -> assertThat(milestoneCount).isEqualTo(4)
		);

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
		IssueSearchResponse lastIssueSearchResponse = issueSearchResponses.get(0);

		assertThat(lastIssueSearchResponse.getId()).isEqualTo(response.jsonPath().getLong("id"));
		assertThat(lastIssueSearchResponse.getTitle()).isEqualTo(issueCreateRequest.getTitle());
		assertThat(lastIssueSearchResponse.getIsOpen()).isTrue();
		assertThat(lastIssueSearchResponse.getLabels().size()).isEqualTo(issueCreateRequest.getLabelIds().size());
	}

	private void 이슈_상세_조회_검증(ExtractableResponse<Response> response, IssueFixture issue) {
		IssueDetailResponse issueDetailResponse = response.as(IssueDetailResponse.class);

		Assertions.assertAll(
			() -> assertThat(issueDetailResponse.getTitle()).isEqualTo(issue.getTitle()),
			() -> assertThat(issueDetailResponse.getContent()).isEqualTo(issue.getContent()),
			() -> assertThat(issueDetailResponse.getAuthor().getId()).isEqualTo(issue.getAuthorId()),
			() -> assertThat(issueDetailResponse.getMilestone().getId()).isEqualTo(issue.getMilestoneId()),
			() -> assertThat(issueDetailResponse.getLabels()).hasSize(LabelFixture.findByIssueId(issue.getId()).size()),
			() -> assertThat(issueDetailResponse.getAssignees()).hasSize(MemberFixture.findByIssueId(issue.getId()).size()),
			() -> assertThat(issueDetailResponse.getComments()).hasSize(IssueCommentFixture.findByIssueId(issue.getId()).size())
		);
	}

	private void 이슈_상세_조회에서_수정한_값_검증(Long id, String column, Object expected) {
		var response = 이슈_상세_조회_요청(id);

		if (Objects.isNull(expected)) {
			Object actual = response.jsonPath().getObject(column, Object.class);

			assertThat(actual).isNull();
			return;
		}

		Object actual = 이슈_상세_조회_요청(id).jsonPath().getObject(column, expected.getClass());

		assertThat(actual).isEqualTo(expected);
	}

	private void 이슈_상세_조회에서_삭제_되었는지_검증(Long id) {
		var response = 이슈_상세_조회_요청(id);

		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}
}
