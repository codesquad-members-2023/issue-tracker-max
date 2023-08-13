package codesquad.issueTracker.milestone.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import annotation.RepositoryTest;
import codesquad.issueTracker.milestone.domain.Milestone;
import codesquad.issueTracker.milestone.vo.MilestoneVo;

@RepositoryTest
class MilestoneRepositoryTest {

	private MilestoneRepository milestoneRepository;

	@Autowired
	public MilestoneRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.milestoneRepository = new MilestoneRepository(jdbcTemplate);
	}

	@Test
	@DisplayName("마일스톤 등록 테스트")
	void insert() {
		// given
		Milestone milestone = Milestone.builder()
			.name("첫번째 마일스톤")
			.description("설명")
			.doneDate(LocalDate.now())
			.isClosed(false)
			.build();

		// when
		Long id = milestoneRepository.insert(milestone);

		// then
		assertThat(id).isEqualTo(1L);

	}

	@Test
	@DisplayName("마일스톤 수정 테스트 : 수정에 성공하면 수정에 성공한 id 를 반환 한다.")
	void update() {

		// given
		Milestone milestone = Milestone.builder()
			.name("첫번째 마일스톤")
			.description("설명")
			.doneDate(LocalDate.now())
			.isClosed(false)
			.build();
		Long savedId = milestoneRepository.insert(milestone);
		Milestone updated = Milestone.builder()
			.name("수정된 이름")
			.description("수정 설명")
			.doneDate(null)
			.isClosed(false)
			.build();
		// when
		Long updatedId = milestoneRepository.update(savedId, updated);

		// then
		assertThat(updatedId).isEqualTo(savedId);
	}

	@Test
	@DisplayName("마일스톤 삭제 테스트 : 삭제에 성공하면 삭제에 성공한 id 를 반환 한다.")
	void delete() {
		// given
		Milestone milestone = Milestone.builder()
			.name("첫번째 마일스톤")
			.description("설명")
			.doneDate(LocalDate.now())
			.isClosed(false)
			.build();
		Long savedId = milestoneRepository.insert(milestone);

		// when
		Long deletedId = milestoneRepository.delete(savedId);

		// then
		assertThat(deletedId).isEqualTo(savedId);

	}

	@Test
	@DisplayName("마일스톤 존재 여부 테스트 : 해당하는 id 의 마일스톤 id가 존재하면 true 를 반환한다.")
	void isExist() {
		// given
		Milestone milestone = Milestone.builder()
			.name("첫번째 마일스톤")
			.description("설명")
			.doneDate(LocalDate.now())
			.isClosed(false)
			.build();
		Long savedId = milestoneRepository.insert(milestone);
		// when
		Boolean result = milestoneRepository.isExist(savedId);
		// then
		assertThat(result).isTrue();

	}

	@Test
	@DisplayName("마일스톤 상태변경 테스트 : 마일스톤 상태변경에 성공하면 마일스톤 상태변경에 성공한 id 를 반환한다.")
	void updateStatus() {
		// given
		Milestone milestone = Milestone.builder()
			.name("첫번째 마일스톤")
			.description("설명")
			.doneDate(LocalDate.now())
			.isClosed(false)
			.build();
		Long savedId = milestoneRepository.insert(milestone);
		// when
		Long updatedMilestoneStatus = milestoneRepository.updateStatus(savedId, true);
		// then
		assertThat(updatedMilestoneStatus).isEqualTo(savedId);

	}

	@Test
	@DisplayName("마일스톤 전체조회 : 상태에 따른 마일스톤 전체 조회 테스트 : ex) 상태가 open 이면 open 상태의 마일스톤만 조회")
	void findAll() {
		// given
		// 열려있는 마일스톤
		Milestone openMilestone = Milestone.builder()
			.name("첫번째 마일스톤")
			.description("설명")
			.doneDate(LocalDate.now())
			.isClosed(false)
			.build();
		// 닫혀있는 마일스톤
		Milestone closedMilestone = Milestone.builder()
			.name("두번째 마일스톤")
			.description("설명2")
			.doneDate(LocalDate.now())
			.isClosed(true)
			.build();
		milestoneRepository.insert(openMilestone);
		milestoneRepository.insert(closedMilestone);
		// when
		List<MilestoneVo> milestones = milestoneRepository.findAll(false);
		// then
		assertAll(
			() -> assertThat(milestones.get(0).getName()).isEqualTo(openMilestone.getName()),
			() -> assertThat(milestones.get(0).getDescription()).isEqualTo(openMilestone.getDescription()),
			() -> assertThat(milestones.get(0).getDoneDate()).isEqualTo(openMilestone.getDoneDate()),
			() -> assertThat(milestones.get(0).getIssueOpenCount()).isEqualTo(0),
			() -> assertThat(milestones.get(0).getIssueClosedCount()).isEqualTo(0)
		);
	}

	@Test
	@DisplayName("해당 상태 갯수 카운트 "
		+ "*service 단에서 현재 상태의 반대로 변환해 주어서 메서드 명이 getAnotherCount")
	void getAnotherCount() {
		// given
		Milestone milestone = Milestone.builder()
			.name("첫번째 마일스톤")
			.description("설명")
			.doneDate(LocalDate.now())
			.isClosed(false)
			.build();
		milestoneRepository.insert(milestone);
		// when
		int count = milestoneRepository.getAnotherCount(false);
		// then
		assertThat(count).isEqualTo(1);

	}

	@Test
	@DisplayName("라벨 갯수 카운트 : 라벨 존재하지 않아서 0")
	void getLabelCount() {
		// given
		// when
		int count = milestoneRepository.getLabelCount();
		// then
		assertThat(count).isEqualTo(0);

	}
}