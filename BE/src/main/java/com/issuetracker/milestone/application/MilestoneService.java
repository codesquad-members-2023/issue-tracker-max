package com.issuetracker.milestone.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.config.exception.ErrorType;
import com.issuetracker.milestone.application.dto.MilestoneCandidatesInformation;
import com.issuetracker.milestone.application.dto.MilestoneCountMetadataInformation;
import com.issuetracker.milestone.application.dto.MilestoneCreateInformation;
import com.issuetracker.milestone.application.dto.MilestoneCreateInputData;
import com.issuetracker.milestone.application.dto.MilestoneDeleteInputData;
import com.issuetracker.milestone.application.dto.MilestoneInformation;
import com.issuetracker.milestone.application.dto.MilestoneSearchByOpenStatusInputData;
import com.issuetracker.milestone.application.dto.MilestoneSearchInformation;
import com.issuetracker.milestone.application.dto.MilestoneUpdateInputData;
import com.issuetracker.milestone.application.dto.MilestoneUpdateOpenStatusInputData;
import com.issuetracker.milestone.application.dto.MilestonesInformation;
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

	@Transactional
	public void update(MilestoneUpdateInputData milestoneUpdateInputData) {
		int numberOfUpdatedRow = milestoneRepository.update(milestoneUpdateInputData.toMilestoneForUpdate());

		if (numberOfUpdatedRow == 0) {
			throw new CustomHttpException(ErrorType.MILESTONE_NOT_FOUND);
		}
	}

	@Transactional
	public void updateOpenStatus(MilestoneUpdateOpenStatusInputData milestoneUpdateOpenStatusInputData) {
		int numberOfUpdatedRow = milestoneRepository.updateOpenStatus(
			milestoneUpdateOpenStatusInputData.toMilestoneForUpdateOpenStatus());

		if (numberOfUpdatedRow == 0) {
			throw new CustomHttpException(ErrorType.MILESTONE_NOT_FOUND);
		}
	}

	@Transactional
	public void delete(MilestoneDeleteInputData milestoneDeleteInputData) {
		int numberOfDeleteRow = milestoneRepository.delete(milestoneDeleteInputData.toMilestoneForDelete());

		if (numberOfDeleteRow == 0) {
			throw new CustomHttpException(ErrorType.MILESTONE_NOT_FOUND);
		}
	}

	public MilestonesInformation search(
		MilestoneSearchByOpenStatusInputData milestoneSearchByOpenStatusInputData) {
		return MilestonesInformation.from(
			MilestoneCountMetadataInformation.from(milestoneRepository.calculateMetadata()),
			MilestoneInformation.from(
				milestoneRepository.findAll(milestoneSearchByOpenStatusInputData.toMilestoneForSearchByOpenStatus()))
		);
	}

	public MilestoneCandidatesInformation searchMilestoneCandidates(long issueId) {

		return MilestoneCandidatesInformation
			.from(
				milestoneRepository.findAllAssignedToIssue(issueId),
				milestoneRepository.findAllUnassignedToIssue(issueId)
			);
	}
}
