package com.issuetracker.milestone.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.milestone.application.dto.MilestoneCreateInformation;
import com.issuetracker.milestone.application.dto.MilestoneCreateInputData;
import com.issuetracker.milestone.application.dto.MilestoneInformation;
import com.issuetracker.milestone.application.dto.MilestoneSearchInformation;
import com.issuetracker.milestone.domain.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MilestoneService {

	private final MilestoneRepository milestoneRepository;

	public List<MilestoneSearchInformation> searchMilestonesForFilter() {
		return MilestoneSearchInformation.from(milestoneRepository.findAllForFilter());
	}

	@Transactional
	public MilestoneCreateInformation create(MilestoneCreateInputData milestoneCreateInputData) {
		return MilestoneCreateInformation.from(
			milestoneRepository.save(milestoneCreateInputData.toMilestoneForCreate()));
	}

	public List<MilestoneInformation> search() {
		return MilestoneInformation.from(milestoneRepository.findAll());
	}
}
