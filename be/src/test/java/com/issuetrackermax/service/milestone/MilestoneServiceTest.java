package com.issuetrackermax.service.milestone;

import static com.issuetrackermax.fixture.EntityFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.MilestoneException;
import com.issuetrackermax.controller.milestone.dto.request.MilestoneModifyRequest;
import com.issuetrackermax.controller.milestone.dto.request.MilestonePostRequest;
import com.issuetrackermax.controller.milestone.dto.response.MilestoneDetailResponse;
import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.milestone.MilestoneRepository;
import com.issuetrackermax.domain.milestone.entity.Milestone;
import com.issuetrackermax.util.DatabaseCleaner;

class MilestoneServiceTest extends IntegrationTestSupport {
	@Autowired
	private MilestoneService milestoneService;
	@Autowired
	private DatabaseCleaner databaseCleaner;
	@Autowired
	private MilestoneRepository milestoneRepository;
	@Autowired
	private IssueRepository issueRepository;

	@AfterEach
	void cleaner() {
		databaseCleaner.execute();
	}

	@Test
	void getMilestoneCount() {
		// given
		milestoneService.save(MilestonePostRequest
			.builder()
			.title("title1")
			.description("description2")
			.dueDate(LocalDateTime.now())
			.build());
		milestoneService.save(MilestonePostRequest
			.builder()
			.title("title2")
			.description("description2")
			.dueDate(LocalDateTime.now())
			.build());
		// when
		Long milestoneCount = milestoneService.getMilestoneCount();

		// then
		assertThat(milestoneCount).isEqualTo(2L);
	}

	@Test
	void getOpenMilestone() {
		// when
		Long milestoneId = milestoneRepository.save(

			Milestone.builder()
				.title("milestone_title")
				.isOpen(true)
				.description("description")
				.duedate(LocalDateTime.now())
				.build()
		);
		issueRepository.save(
			makeIssue(true, "issue_title1", milestoneId, 1L)
		);
		issueRepository.save(
			makeIssue(true, "issue_title2", milestoneId, 1L)
		);
		issueRepository.save(
			makeIssue(false, "issue_title3", milestoneId, 1L)
		);

		// when
		List<MilestoneDetailResponse> openMilestone = milestoneService.getOpenMilestone();

		// then
		assertAll(
			() -> assertThat(openMilestone.get(0).getTitle()).isEqualTo("milestone_title"),
			() -> assertThat(openMilestone.get(0).getDescription()).isEqualTo("description"),
			() -> assertThat(openMilestone.get(0).getOpenIssueCount()).isEqualTo(2L),
			() -> assertThat(openMilestone.get(0).getClosedIssueCount()).isEqualTo(1L)

		);
	}

	@Test
	void post() {
		// given
		MilestonePostRequest milestonePostRequet = MilestonePostRequest
			.builder()
			.title("title")
			.description("description")
			.dueDate(LocalDateTime.now())
			.build();

		// when
		Long id = milestoneService.save(milestonePostRequet);

		// then
		Milestone milestone = milestoneRepository.findbyId(id);
		assertAll(
			() -> assertThat(milestone.getTitle()).isEqualTo("title"),
			() -> assertThat(milestone.getDescription()).isEqualTo("description"),
			() -> assertThat(milestone.getIsOpen()).isTrue()
		);
	}

	@DisplayName("마일스톨을 저장하고,같은 이름의 마일스톤을 저장하면 오류가 발생한다..")
	@Test
	void saveDuplicateTitle() {
		// given
		MilestonePostRequest milestonePostRequet = MilestonePostRequest
			.builder()
			.title("title")
			.description("description")
			.dueDate(LocalDateTime.now())
			.build();
		Long id = milestoneService.save(milestonePostRequet);

		// when
		MilestonePostRequest milestonePostRequet1 = MilestonePostRequest
			.builder()
			.title("title")
			.description("description")
			.dueDate(LocalDateTime.now())
			.build();
		// when & then
		assertThatThrownBy(() -> milestoneService.save(milestonePostRequet))
			.isInstanceOf(ApiException.class)
			.satisfies(exception -> {
				ApiException apiException = (ApiException)exception;
				assertThat(apiException.getCustomException()).isInstanceOf(MilestoneException.class);
			});
	}

	@DisplayName("저장된 마일스톤을 수정하고, 수정된 값을 확인한다.")
	@Test
	void update() {
		// given
		MilestonePostRequest milestonePostRequest = new MilestonePostRequest("title", LocalDateTime.now(),
			"description");
		Long postId = milestoneService.save(milestonePostRequest);
		MilestoneModifyRequest milestoneModifyRequest = new MilestoneModifyRequest("title2", "description2",
			LocalDateTime.now());

		// when
		milestoneService.update(postId, milestoneModifyRequest);

		// then
		Milestone milestone = milestoneRepository.findbyId(postId);
		assertAll(
			() -> assertThat(milestone.getId()).isEqualTo(postId),
			() -> assertThat(milestone.getTitle()).isEqualTo("title2"),
			() -> assertThat(milestone.getDescription()).isEqualTo("description2"),
			() -> assertThat(milestone.getIsOpen()).isTrue()
		);

	}

	@DisplayName("마일스톤을 삭제한다.")
	@Test
	void delete() {
		// given
		MilestonePostRequest milestonePostRequest = new MilestonePostRequest("title", LocalDateTime.now(),
			"description");
		Long postId = milestoneService.save(milestonePostRequest);

		// when
		milestoneService.delete(postId);

		// then
		assertThat(milestoneRepository.existById(postId)).isFalse();
	}

	@DisplayName("마일 스톤의 상태를 바꾼다.")
	@Test
	void updateStatus() {
		// given
		MilestonePostRequest milestonePostRequest = new MilestonePostRequest("title", LocalDateTime.now(),
			"description");
		Long postId = milestoneService.save(milestonePostRequest);

		// when
		milestoneService.updateStatus(postId);

		// then
		Milestone milestone = milestoneRepository.findbyId(postId);
		assertAll(
			() -> assertThat(milestone.getId()).isEqualTo(postId),
			() -> assertThat(milestone.getTitle()).isEqualTo("title"),
			() -> assertThat(milestone.getDescription()).isEqualTo("description"),
			() -> assertThat(milestone.getIsOpen()).isFalse()
		);
	}
}
