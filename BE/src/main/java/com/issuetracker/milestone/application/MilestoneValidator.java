package com.issuetracker.milestone.application;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.MilestoneNotFoundException;
import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.milestone.infrastructure.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MilestoneValidator {

	private final MilestoneRepository milestoneRepository;

	public Milestone getVerifyMilestone(Long id) {
		if (!milestoneRepository.existById(id)) {
			throw new MilestoneNotFoundException();
		}

		return Milestone.createInstanceById(id);

	}

}
