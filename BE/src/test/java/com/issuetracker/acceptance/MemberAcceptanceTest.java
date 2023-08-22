package com.issuetracker.acceptance;

import static com.issuetracker.util.fixture.MemberFixture.MEMBER1;
import static com.issuetracker.util.steps.MemberSteps.회원_정보_수정_요청;
import static com.issuetracker.util.steps.MemberSteps.회원_정보_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.issuetracker.file.application.FileService;
import com.issuetracker.file.application.dto.FileMetadata;
import com.issuetracker.member.ui.dto.MemberResponse;
import com.issuetracker.util.AcceptanceTest;
import com.issuetracker.util.fixture.MemberFixture;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

public class MemberAcceptanceTest extends AcceptanceTest {

	private static final String FILE_PATH = String.format("%s/%s", System.getProperty("user.dir"), "src/test/resources/bike.jpg");

	@MockBean
	private FileService fileService;

	/**
	 * Given 회원을 생성하고
	 * When 회원 정보를 조회하면
	 * Then 해당 회원 정보를 확인 할 수 있다.
	 */
	@Test
	void 회원_정보를_조회한다() {
		// when
		var response = 회원_정보_조회_요청(MEMBER1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		회원_정보를_검증(response, MEMBER1.getId(), MEMBER1.getNickname(), MEMBER1.getProFileImageUrl());
	}

	/**
	 * When 존재하지 않는 회원 정보를 조회하면
	 * Then 요청이 실패한다
	 */
	@Test
	void 존재하지_않는_회원_정보를_조회하면_실패한다() {
		// given
		long 존재하지_않는_회원_아이디 = 20L;

		// when
		var response = 회원_정보_조회_요청(존재하지_않는_회원_아이디);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Given 회원을 생성하고
	 * When 회원 정보를 수정하면
	 * Then 회원 프로필 조회에서 확인 할 수 있다.
	 */
	@ParameterizedTest
	@MethodSource("providerMemberUpdate")
	void 회원_정보를_수정한다(String nickname, String password, MultiPartSpecification multiPartSpecification) {
		// given
		given(fileService.upload(null)).willReturn(new FileMetadata("testPicture.jpg", MEMBER1.getProFileImageUrl()));
		given(fileService.upload(any())).willReturn(new FileMetadata("testPicture.jpg", "aws"));

		// when
		var response = 회원_정보_수정_요청(MEMBER1.getId(), nickname, password, multiPartSpecification);

		// then
		응답_상태코드_검증(response, HttpStatus.NO_CONTENT);
		회원_정보를_조회하여_수정된_회원_정보_검증(MEMBER1, nickname, multiPartSpecification);
	}

	/**
	 * When 존재하지 않는 회원을 수정하면
	 * Then 요청이 실패한다
	 */
	@Test
	void 존재하지_않는_회원을_수정하면_요청이_실패된다() {
		// given
		long 존재하지_않는_회원_아이디 = 20L;
		String nickname = "mandu";
		String password = "password";
		MultiPartSpecification multiPartSpecification = null;

		// when
		var response = 회원_정보_수정_요청(존재하지_않는_회원_아이디, nickname, password, multiPartSpecification);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	private static Stream<Arguments> providerMemberUpdate() throws IOException {
		return Stream.of(
			Arguments.of(
				"mandu",
				"password",
				new MultiPartSpecBuilder(new FileInputStream(FILE_PATH))
					.fileName("testPicture.jpg")
					.controlName("file")
					.mimeType(MediaType.IMAGE_JPEG_VALUE)
					.build()
			),
			Arguments.of(
				"test",
				"password2",
				null
			),
			Arguments.of(
				null,
				"password2",
				null
			),
			Arguments.of(
				"test",
				null,
				null
			),
			Arguments.of(
				null,
				null,
				null
			)
		);
	}

	private void 회원_정보를_조회하여_수정된_회원_정보_검증(MemberFixture memberFixture, String nickname, MultiPartSpecification multiPartSpecification) {
		MemberResponse memberResponse = 회원_정보_조회_요청(memberFixture.getId()).as(MemberResponse.class);

		Assertions.assertAll(
			() -> assertThat(memberResponse.getId()).isEqualTo(memberFixture.getId()),
			() -> {
				if (Objects.isNull(nickname)) {
					assertThat(memberResponse.getNickname()).isEqualTo(memberFixture.getNickname());
					return;
				}

				assertThat(memberResponse.getNickname()).isEqualTo(nickname);
			},
			() -> {
				if (Objects.isNull(multiPartSpecification)) {
					assertThat(memberResponse.getProfileImageUrl()).isEqualTo(memberFixture.getProFileImageUrl());
					return;
				}

				assertThat(memberResponse.getProfileImageUrl()).isNotEqualTo(memberFixture.getProFileImageUrl());
			}
		);
	}

	private void 회원_정보를_검증(ExtractableResponse<Response> response, Long id, String nickname, String profileImageUrl) {
		MemberResponse memberResponse = response.as(MemberResponse.class);

		Assertions.assertAll(
			() -> assertThat(memberResponse.getId()).isEqualTo(id),
			() -> assertThat(memberResponse.getNickname()).isEqualTo(nickname),
			() -> assertThat(memberResponse.getProfileImageUrl()).isEqualTo(profileImageUrl)
		);
	}
}
