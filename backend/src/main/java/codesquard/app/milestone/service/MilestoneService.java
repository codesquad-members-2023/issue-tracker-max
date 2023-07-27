package codesquard.app.milestone.service;

import org.springframework.stereotype.Service;

import codesquard.app.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;
}
