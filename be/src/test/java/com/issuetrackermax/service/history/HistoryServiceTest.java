package com.issuetrackermax.service.history;

import static com.issuetrackermax.fixture.EntityFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.controller.issue.dto.request.IssuePostRequest;
import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.history.HistoryRepository;
import com.issuetrackermax.domain.history.entity.History;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;
import com.issuetrackermax.service.issue.IssueService;
import com.issuetrackermax.util.DatabaseCleaner;

class HistoryServiceTest extends IntegrationTestSupport {
	@Autowired
	HistoryRepository historyRepository;
	@Autowired
	IssueService issueService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	DatabaseCleaner databaseCleaner;

	@AfterEach
	void setUp() {
		databaseCleaner.execute();
	}

	@DisplayName("새로운 이슈 저장시 히스토리를 생성한다")
	@Test
	void save() {
		// given
		Long writerId = memberRepository.save(makeMember("loginId", "123", "nickname", LoginType.LOCAL));
		IssuePostRequest issuePostRequest = IssuePostRequest.builder()
			.title("title1")
			.content("content1")
			.build();

		Member member = memberRepository.findById(writerId).get();
		// when
		Long issueId = issueService.post(issuePostRequest, writerId).getId();

		// then
		History history = historyRepository.findLatestByIssueId(issueId);
		assertAll(
			() -> assertThat(history.getEditor()).isEqualTo(member.getNickName()),
			() -> assertThat(history.getIssueId()).isEqualTo(issueId),
			() -> assertThat(history.getIssueIsOpen()).isTrue()
		);
	}

	@DisplayName("여러 이슈를 열림으로 설정시 히스토리를 저장한다")
	@Test
	void saveHistoryWhenOpeningMultipleIssues() {
		// given
		Long writerId = memberRepository.save(makeMember("loginId", "123", "nickname", LoginType.LOCAL));
		IssuePostRequest issuePostRequest = IssuePostRequest.builder()
			.title("title1")
			.content("content1")
			.build();
		IssuePostRequest issuePostRequest2 = IssuePostRequest.builder()
			.title("title2")
			.content("content2")
			.build();
		Long issueId1 = issueService.post(issuePostRequest, writerId).getId();
		Long issueId2 = issueService.post(issuePostRequest2, writerId).getId();

		// when
		issueService.openIssue(List.of(issueId1, issueId2), writerId);

		// then
		History history = historyRepository.findLatestByIssueId(issueId1);
		History history2 = historyRepository.findLatestByIssueId(issueId2);

		assertAll(
			() -> assertThat(history.getIssueId()).isEqualTo(issueId1),
			() -> assertThat(history.getEditor()).isEqualTo("nickname"),
			() -> assertThat(history.getIssueIsOpen()).isTrue()
		);

		assertAll(
			() -> assertThat(history2.getIssueId()).isEqualTo(issueId2),
			() -> assertThat(history2.getEditor()).isEqualTo("nickname"),
			() -> assertThat(history2.getIssueIsOpen()).isTrue()
		);
	}

	@DisplayName("여러 이슈를 닫힘으로 설정시 히스토리를 저장한다")
	@Test
	void saveHistoryWhenClosingMultipleIssues() {
		// given
		Long writerId = memberRepository.save(makeMember("loginId", "123", "nickname", LoginType.LOCAL));
		IssuePostRequest issuePostRequest = IssuePostRequest.builder()
			.title("title1")
			.content("content1")
			.build();
		IssuePostRequest issuePostRequest2 = IssuePostRequest.builder()
			.title("title2")
			.content("content2")
			.build();
		Long issueId1 = issueService.post(issuePostRequest, writerId).getId();
		Long issueId2 = issueService.post(issuePostRequest2, writerId).getId();

		// when
		issueService.closeIssue(List.of(issueId1, issueId2), writerId);

		// then
		History history = historyRepository.findLatestByIssueId(issueId1);
		History history2 = historyRepository.findLatestByIssueId(issueId2);

		assertAll(
			() -> assertThat(history.getIssueId()).isEqualTo(issueId1),
			() -> assertThat(history.getEditor()).isEqualTo("nickname"),
			() -> assertThat(history.getIssueIsOpen()).isFalse()
		);

		assertAll(
			() -> assertThat(history2.getIssueId()).isEqualTo(issueId2),
			() -> assertThat(history2.getEditor()).isEqualTo("nickname"),
			() -> assertThat(history2.getIssueIsOpen()).isFalse()
		);
	}
}
