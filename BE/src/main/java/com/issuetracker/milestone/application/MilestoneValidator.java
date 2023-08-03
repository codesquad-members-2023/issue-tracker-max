package com.issuetracker.milestone.application;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.MilestoneNotFoundException;
import com.issuetracker.milestone.infrastructure.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MilestoneValidator {

	private final MilestoneRepository milestoneRepository;

	public void verifyMilestone(Long id) {
		if (Objects.nonNull(id) && !milestoneRepository.existById(id)) {
			throw new MilestoneNotFoundException();
		}
	}
}
