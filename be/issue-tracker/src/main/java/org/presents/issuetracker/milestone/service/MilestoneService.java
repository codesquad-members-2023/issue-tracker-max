package org.presents.issuetracker.milestone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.milestone.repository.MilestoneRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;

	public List<MilestonePreviewResponse> getMilestonePreviews() {
		return milestoneRepository.findPreviews().stream()
			.map(MilestonePreviewResponse::from)
			.collect(Collectors.toList());
	}
}
