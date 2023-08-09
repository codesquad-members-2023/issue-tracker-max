package codesquad.issueTracker.milestone.service;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.milestone.domain.Milestone;
import codesquad.issueTracker.milestone.dto.ModifyMilestoneRequestDto;
import codesquad.issueTracker.milestone.dto.UpdateMilestoneStatusDto;
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

	public Long updateStatus(Long id , UpdateMilestoneStatusDto request) {
		int status = convertStatusToValue(request.getStatus());
		return milestoneRepository.updateStatus(id,status);
	}

	private int convertStatusToValue(String status) {
		if ("open".equals(status)) {
			return 0;
		} else if ("close".equals(status)) {
			return 1;
		}
		throw new CustomException(ErrorCode.ILLEGAL_STATUS_MILESTONE);
	}
}
