package codesquard.app.milestone.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
import codesquard.app.milestone.dto.request.MilestoneStatusUpdateRequest;
import codesquard.app.milestone.dto.request.MilestoneUpdateRequest;
import codesquard.app.milestone.repository.MilestoneRepository;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;

public class MilestoneServiceTest extends IntegrationTestSupport {

	@Autowired
	private MilestoneService milestoneService;

	@Autowired
	private MilestoneRepository milestoneRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JdbcTemplate template;

	@BeforeEach
	void setUp() {
		template.update("SET FOREIGN_KEY_CHECKS = 0");
		template.update("TRUNCATE TABLE `milestone`");
		template.update("TRUNCATE TABLE `user`");
		template.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@DisplayName("마일스톤을 등록한다.")
	@Test
	void saveMilestone() {
		// given
		createUserFixture();
		MilestoneSaveRequest milestoneSaveRequest = new MilestoneSaveRequest("이름", LocalDate.now(), "설명");

		// when
		Long savedId = milestoneService.saveMilestone(milestoneSaveRequest, 1L);

		// then
		assertThat(savedId).isEqualTo(1L);
	}

	@DisplayName("마일스톤을 수정한다.")
	@Test
	void updateMilestone() {
		// given
		createUserFixture();
		Long milestoneId = createMilestoneFixture().get();
		MilestoneUpdateRequest milestoneUpdateRequest = new MilestoneUpdateRequest("바뀐 이름", LocalDate.now(), "바뀐 설명");

		// when
		Long updatedId = milestoneService.updateMilestone(milestoneId, milestoneUpdateRequest, 1L);

		// then
		assertThat(updatedId).isEqualTo(milestoneId);
	}

	@DisplayName("마일스톤 상태를 수정한다.")
	@Test
	void updateMilestoneStatus() {
		// given
		createUserFixture();
		Long milestoneId = createMilestoneFixture().get();
		MilestoneStatusUpdateRequest milestoneStatusUpdateRequest = new MilestoneStatusUpdateRequest("closed");

		// when
		Long updatedId = milestoneService.updateMilestoneStatus(milestoneId, milestoneStatusUpdateRequest);

		// then
		assertThat(updatedId).isEqualTo(milestoneId);
	}

	@DisplayName("마일스톤을 삭제한다.")
	@Test
	void deleteMilestone() {
		// given
		createUserFixture();
		Long milestoneId = createMilestoneFixture().get();

		// when
		Long deletedId = milestoneService.deleteMilestone(milestoneId);

		// then
		assertThat(deletedId).isEqualTo(milestoneId);
	}

	private void createUserFixture() {
		User user = new User(null, "sully", "sully1234@gmail.com", "qwe123", "url path");
		userRepository.save(user);
	}

	private Optional<Long> createMilestoneFixture() {
		MilestoneSaveRequest milestoneSaveRequest = new MilestoneSaveRequest("이름", LocalDate.now(), "설명");
		return milestoneRepository.save(MilestoneSaveRequest.toEntity(milestoneSaveRequest, 1L));
	}
}
