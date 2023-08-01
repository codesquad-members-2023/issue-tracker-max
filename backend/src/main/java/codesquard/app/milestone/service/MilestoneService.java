package codesquard.app.milestone.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.milestone.dto.request.MilestoneRequestDto;
import codesquard.app.milestone.entity.Milestone;
import codesquard.app.milestone.repository.MilestoneRepository;

@Service
@Transactional
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;

	public MilestoneService(MilestoneRepository milestoneRepository) {
		this.milestoneRepository = milestoneRepository;
	}

	public Long saveMilestone(MilestoneRequestDto milestoneRequestDto) {
		Milestone milestone = MilestoneRequestDto.toEntity(milestoneRequestDto);
		return milestoneRepository.save(milestone).orElseThrow(() -> new RuntimeException("임시"));
	}
}
