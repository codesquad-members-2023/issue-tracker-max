package codesquad.issueTracker.milestone.service;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneValidator {
	private final MilestoneRepository milestoneRepository;

	public void existValidate(Long id) {
		if (!milestoneRepository.isExist(id)){
			throw new CustomException(ErrorCode.NOT_FOUND_MILESTONE);
		}
	}

}
