package codesquad.issueTracker.milestone.service;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.milestone.domain.Milestone;
import codesquad.issueTracker.milestone.dto.ModifyMilestoneRequestDto;
import codesquad.issueTracker.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;
	private final MilestoneValidator milestoneValidator;

	public Long save(ModifyMilestoneRequestDto request) {
		Milestone milestone = ModifyMilestoneRequestDto.toEntity(request);
		milestone.validateDate();
		return milestoneRepository.insert(milestone);
	}

	public Long update(Long id, ModifyMilestoneRequestDto request) {
		milestoneValidator.existValidate(id);
		Milestone milestone = ModifyMilestoneRequestDto.toEntity(request);
		milestone.validateDate();
		return milestoneRepository.update(id,milestone);
	}

	public Long delete(Long id) {
		milestoneValidator.existValidate(id);
		return milestoneRepository.delete(id);
	}
}
