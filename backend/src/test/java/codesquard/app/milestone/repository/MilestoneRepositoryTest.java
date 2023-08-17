package codesquard.app.milestone.repository;

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
import codesquard.app.milestone.entity.Milestone;
import codesquard.app.milestone.entity.MilestoneStatus;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;

public class MilestoneRepositoryTest extends IntegrationTestSupport {

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
		Milestone milestone = new Milestone("이름", "설명", LocalDate.now(), 1L);

		// when
		Long savedId = milestoneRepository.save(milestone).get();

		// then
		assertThat(savedId).isEqualTo(1L);
	}

	@DisplayName("마일스톤을 수정한다.")
	@Test
	void updateMilestone() {
		// given
		createUserFixture();
		Long milestoneId = createMilestoneFixture().get();
		Milestone milestone = new Milestone("이름", "설명", LocalDate.now(), 1L);

		// when
		Long updatedId = milestoneRepository.updateBy(milestoneId, milestone);

		// then
		assertThat(updatedId).isEqualTo(milestoneId);
	}

	@DisplayName("마일스톤 상태를 수정한다.")
	@Test
	void updateMilestoneStatus() {
		// given
		createUserFixture();
		Long milestoneId = createMilestoneFixture().get();
		MilestoneStatus milestoneStatus = MilestoneStatus.CLOSED;

		// when
		Long updatedId = milestoneRepository.updateBy(milestoneId, milestoneStatus);

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
		Long deletedId = milestoneRepository.deleteBy(milestoneId);

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
