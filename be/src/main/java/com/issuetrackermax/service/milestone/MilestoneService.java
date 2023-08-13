package com.issuetrackermax.service.milestone;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.controller.milestone.dto.request.MilestoneModifyRequest;
import com.issuetrackermax.controller.milestone.dto.request.MilestonePostRequest;
import com.issuetrackermax.controller.milestone.dto.response.MilestoneDetailResponse;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.issue.entity.Issue;
import com.issuetrackermax.domain.milestone.MilestoneRepository;
import com.issuetrackermax.domain.milestone.MilestoneValidator;
import com.issuetrackermax.domain.milestone.entity.Milestone;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {
	private final MilestoneValidator milestoneValidator;
	private final MilestoneRepository milestoneRepository;
	private final IssueRepository issueRepository;

	@Transactional(readOnly = true)
	public Long getMilestoneCount() {
		return milestoneRepository.getMilestoneCount();
	}

	@Transactional(readOnly = true)
	public List<MilestoneDetailResponse> getOpenMilestone() {
		List<Milestone> openMilestone = milestoneRepository.getOpenMilestone();
		return getMilestoneDetailResponses(openMilestone);
	}

	@Transactional(readOnly = true)
	public List<MilestoneDetailResponse> getClosedMilestone() {
		List<Milestone> closedMilestone = milestoneRepository.getClosedMilestone();
		return getMilestoneDetailResponses(closedMilestone);

	}

	@Transactional
	public Long save(MilestonePostRequest milestonePostRequest) {
		milestoneValidator.existByTitle(milestonePostRequest.getTitle());
		Milestone milestone = Milestone.from(milestonePostRequest);
		return milestoneRepository.save(milestone);
	}

	@Transactional
	public void update(Long id, MilestoneModifyRequest milestoneModifyRequest) {
		milestoneValidator.existById(id);
		milestoneRepository.update(id, Milestone.from(milestoneModifyRequest));
	}

	@Transactional
	public void delete(Long id) {
		int count = milestoneRepository.deleteById(id);
	}

	@Transactional
	public void updateStatus(Long id) {
		milestoneValidator.existById(id);
		milestoneRepository.updateStatus(id);
	}

	@Transactional(readOnly = true)
	public Boolean existById(Long id) {
		milestoneValidator.existById(id);
		return milestoneRepository.existById(id);
	}

	private List<MilestoneDetailResponse> getMilestoneDetailResponses(List<Milestone> openMilestone) {
		return openMilestone.stream()
			.map(milestone -> MilestoneDetailResponse.builder()
				.milestone(milestone)
				.openIssueCount(getOpenIssueCount(milestone))
				.closedIssueCount(getClosedIssueCount(milestone))
				.build())
			.collect(Collectors.toList());
	}

	private long getClosedIssueCount(Milestone milestone) {
		return issueRepository.findByMilestoneId(milestone.getId())
			.stream()
			.filter(issue -> !issue.getIsOpen())
			.count();
	}

	private long getOpenIssueCount(Milestone milestone) {
		return issueRepository.findByMilestoneId(milestone.getId())
			.stream()
			.filter(Issue::getIsOpen)
			.count();
	}
}
