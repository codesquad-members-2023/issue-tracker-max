package codesquad.issueTracker.milestone.service;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;
}
