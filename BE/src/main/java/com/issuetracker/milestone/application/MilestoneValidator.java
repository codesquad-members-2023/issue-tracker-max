package com.issuetracker.milestone.application;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.issuetracker.common.config.exception.CustomHttpException;
import com.issuetracker.common.config.exception.ErrorType;
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

	public void verifyDuplicationTitle(String title) {
		if (milestoneRepository.existsByTitle(title)) {
			throw new CustomHttpException(ErrorType.MILESTONE_TITLE_DUPLICATION);
		}
	}

	public void verifyUpdatedOrDeletedCount(int count) {
		if (count != 1) {
			throw new CustomHttpException(ErrorType.MILESTONE_NOT_FOUND);
		}
	}
}
