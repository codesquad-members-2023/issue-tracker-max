package codesquard.app.milestone.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.milestone.dto.request.MilestoneSavedRequest;
import codesquard.app.milestone.entity.Milestone;
import codesquard.app.milestone.repository.MilestoneRepository;

@Service
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;

	public MilestoneService(MilestoneRepository milestoneRepository) {
		this.milestoneRepository = milestoneRepository;
	}

	@Transactional
	public Long saveMilestone(MilestoneSavedRequest milestoneSavedRequest) {
		Milestone milestone = MilestoneSavedRequest.toEntity(milestoneSavedRequest);
		return milestoneRepository.save(milestone).orElseThrow(() -> new RuntimeException("임시"));
	}
}
