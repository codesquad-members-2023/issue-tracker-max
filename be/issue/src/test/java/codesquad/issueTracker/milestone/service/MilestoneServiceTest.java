package codesquad.issueTracker.milestone.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import annotation.ServiceTest;
import codesquad.issueTracker.global.common.Status;
import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.milestone.domain.Milestone;
import codesquad.issueTracker.milestone.dto.MileStoneStatusDto;
import codesquad.issueTracker.milestone.dto.MilestoneResponseDto;
import codesquad.issueTracker.milestone.dto.ModifyMilestoneRequestDto;
import codesquad.issueTracker.milestone.repository.MilestoneRepository;
import codesquad.issueTracker.milestone.vo.MilestoneVo;

@ServiceTest
class MilestoneServiceTest {

	@InjectMocks
	MilestoneService milestoneService;

	@Mock
	MilestoneRepository milestoneRepository;

	@Test
	@DisplayName("save 메서드 테스트 : 성공시 save 성공한 id 반환 ")
	void save() {
		// given
		ModifyMilestoneRequestDto requestDto = new ModifyMilestoneRequestDto("Name", "Description", "2023-09-25");
		Long expectedId = 1L;
		given(milestoneRepository.insert(any())).willReturn(expectedId);

		// when
		Long actualId = milestoneService.save(requestDto);

		// then
		verify(milestoneRepository).insert(any(Milestone.class));

		assertThat(actualId).isEqualTo(expectedId);
	}

	@Test
	@DisplayName("Milestone 의 validateDate() 메서드 실패 테스트 -> case: 이전 날짜 입력시 예외 발생  ")
	void validateBeforeDate() {
		// given
		ModifyMilestoneRequestDto requestDto = new ModifyMilestoneRequestDto("Name", "Description", "2023-01-25");
		Milestone milestone = Milestone.builder()
			.doneDate(LocalDate.parse(requestDto.getDoneDate()))
			.build();
		// then
		assertThrows(CustomException.class, () -> {
			milestone.validateDate();
		});
	}

	@Test
	@DisplayName("update 메서드 성공 : update에 성공한 id 반환  ")
	void update() {
		// given
		ModifyMilestoneRequestDto requestDto = new ModifyMilestoneRequestDto("Name", "Description", "2023-09-25");
		Long id = 1L;
		given(milestoneRepository.update(any(), any())).willReturn(id);
		given(milestoneRepository.isExist(any())).willReturn(true);
		// when
		Long result = milestoneService.update(1L, requestDto);
		// then
		verify(milestoneRepository).update(eq(id), any(Milestone.class));
		assertThat(result).isEqualTo(id);
	}

	@Test
	@DisplayName("update 실패 : existValidate(id) 메서드에서 없는 아이디 입력받아서 예외발생  ")
	void updateFailed() {
		// given
		ModifyMilestoneRequestDto requestDto = new ModifyMilestoneRequestDto("Name", "Description", "2023-09-25");
		Long invalidId = 999L;
		given(milestoneRepository.isExist(eq(invalidId))).willReturn(false);

		// when & then
		assertThrows(RuntimeException.class, () -> milestoneService.update(invalidId, requestDto));
		verify(milestoneRepository, never()).update(eq(invalidId), any(Milestone.class));
	}

	@Test
	@DisplayName("delete 성공 메서드 : delete에 성공한 id 반환 "
		+ "* existValidate() 메서드는 위 updateFailed에서 검증 했기 때문에"
		+ " delete 메서드에서는 생략 ")
	void delete() {
		// given
		Long id = 1L;
		given(milestoneRepository.isExist(id)).willReturn(true);
		given(milestoneRepository.delete(any())).willReturn(id);

		// when
		Long result = milestoneService.delete(id);

		// then
		verify(milestoneRepository).delete(id);
		assertThat(result).isEqualTo(id);
	}

	@Test
	@DisplayName("updateStatus 메서드 성공 : 상태 변경에 성공하면 상태 변경에 성공한 id를 반환")
	void updateStatus() {
		// given
		Long id = 1L;
		given(milestoneRepository.isExist(id)).willReturn(true);
		MileStoneStatusDto request = new MileStoneStatusDto("closed");
		Boolean status = Status.from(request.getStatus()).getStatus();
		given(milestoneRepository.updateStatus(any(), any())).willReturn(id);
		// then
		Long result = milestoneService.updateStatus(id, request);
		verify(milestoneRepository).updateStatus(id, status);
		assertThat(result).isEqualTo(id);
	}

	@Test
	@DisplayName("updateStatus 메서드 실패 :  Status.from(String) 에서 입력한 String 값이 Status Enum 값인 open,closed 가 아닌 다른 문자면 예외 발생 ")
	void updateStatusFailed() {
		// given
		Long id = 1L;
		MileStoneStatusDto requestDto = new MileStoneStatusDto("openddd");
		given(milestoneRepository.isExist(eq(id))).willReturn(true);

		// then
		assertThrows(CustomException.class, () -> milestoneService.updateStatus(id, requestDto));
		verify(milestoneRepository, never()).updateStatus(anyLong(), anyBoolean());
	}

	@Test
	@DisplayName("마일스톤 전체 조회 테스트")
	void findAll() {
		// given
		MileStoneStatusDto request = new MileStoneStatusDto("closed");
		Boolean status = Status.from(request.getStatus()).getStatus();
		MilestoneVo milestones1 = MilestoneVo.builder()
			.id(1l)
			.name("1번")
			.description("설명1")
			.doneDate(LocalDate.parse("2023-09-27"))
			.issueOpenCount(1)
			.issueClosedCount(1)
			.build();
		MilestoneVo milestones2 = MilestoneVo.builder()
			.id(2l)
			.name("2번")
			.description("설명2")
			.doneDate(LocalDate.parse("2023-09-27"))
			.issueOpenCount(2)
			.issueClosedCount(2)
			.build();
		List<MilestoneVo> milestones = new ArrayList<>();
		milestones.add(milestones1);
		milestones.add(milestones2);

		given(milestoneRepository.findAll(any())).willReturn(milestones);
		given(milestoneRepository.getLabelCount()).willReturn(1);
		given(milestoneRepository.getAnotherCount(any())).willReturn(1);

		// when
		MilestoneResponseDto result = milestoneService.findAll(request);

		// then
		assertThat(result.getLabelCount()).isEqualTo(1);
		assertThat(result.getAnotherMilestoneCount()).isEqualTo(1);
		assertThat(result.getMilestones()).isEqualTo(milestones);

	}

}