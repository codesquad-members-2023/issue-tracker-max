package codesquad.issueTracker.milestone.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.issueTracker.global.common.Status;
import codesquad.issueTracker.milestone.domain.Milestone;
import codesquad.issueTracker.milestone.dto.MileStoneStatusDto;
import codesquad.issueTracker.milestone.dto.MilestoneResponseDto;
import codesquad.issueTracker.milestone.dto.ModifyMilestoneRequestDto;
import codesquad.issueTracker.milestone.repository.MilestoneRepository;
import codesquad.issueTracker.milestone.vo.MilestoneVo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;
	private final MilestoneValidator milestoneValidator;

	@Transactional
	public Long save(ModifyMilestoneRequestDto request) {
		Milestone milestone = ModifyMilestoneRequestDto.toEntity(request);
		milestone.validateDate();
		return milestoneRepository.insert(milestone);
	}

	@Transactional
	public Long update(Long id, ModifyMilestoneRequestDto request) {
		milestoneValidator.existValidate(id);
		Milestone milestone = ModifyMilestoneRequestDto.toEntity(request);
		milestone.validateDate();
		return milestoneRepository.update(id, milestone);
	}

	@Transactional
	public Long delete(Long id) {
		milestoneValidator.existValidate(id);
		return milestoneRepository.delete(id);
	}

	@Transactional
	public Long updateStatus(Long id, MileStoneStatusDto request) {
		Boolean status = Status.from(request.getStatus()).getStatus();
		return milestoneRepository.updateStatus(id, status);
	}

	public MilestoneResponseDto findAll(MileStoneStatusDto request) {
		Boolean status = Status.from(request.getStatus()).getStatus();
		List<MilestoneVo> milestones = milestoneRepository.findAll(status);
		int labelCount = milestoneRepository.getLabelCount();
		int anotherCount = milestoneRepository.getAnotherCount(!status);
		MilestoneResponseDto milestoneResponseDto = new MilestoneResponseDto(labelCount, anotherCount, milestones);
		return milestoneResponseDto;
	}

}
