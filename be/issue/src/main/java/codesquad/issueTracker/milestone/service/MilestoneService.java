package codesquad.issueTracker.milestone.service;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.milestone.domain.Milestone;
import codesquad.issueTracker.milestone.dto.SaveMilestoneRequestDto;
import codesquad.issueTracker.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;

	public Long save(SaveMilestoneRequestDto request) {
		Milestone milestone = SaveMilestoneRequestDto.toEntity(request);
		milestone.validateDate();
		return milestoneRepository.insert(milestone);
	}
}
