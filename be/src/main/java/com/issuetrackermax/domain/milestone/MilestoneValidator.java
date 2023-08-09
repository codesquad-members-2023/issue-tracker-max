package com.issuetrackermax.domain.milestone;

import org.springframework.stereotype.Component;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.MilestoneException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MilestoneValidator {
	private final MilestoneRepository milestoneRepository;

	public void existById(Long id) {
		if (!milestoneRepository.existById(id)) {
			throw new ApiException(MilestoneException.NOT_FOUND_MILESTONE);

		}
	}
}
