package com.issuetracker.milestone.application;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.config.exception.ErrorType;
import com.issuetracker.milestone.domain.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MilestoneValidator {

	private final MilestoneRepository milestoneRepository;

	public void verifyMilestone(Long id) {
		if (Objects.nonNull(id) && !milestoneRepository.existById(id)) {
			throw new CustomHttpException(ErrorType.MILESTONE_NOT_FOUND);
		}
	}
}
