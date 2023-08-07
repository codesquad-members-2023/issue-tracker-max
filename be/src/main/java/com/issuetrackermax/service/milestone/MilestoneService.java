package com.issuetrackermax.service.milestone;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.issuetrackermax.controller.milestone.dto.request.MilestoneModifyRequest;
import com.issuetrackermax.controller.milestone.dto.request.MilestonePostRequest;
import com.issuetrackermax.controller.milestone.dto.response.MilestoneDetailResponse;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.issue.entity.Issue;
import com.issuetrackermax.domain.milestone.MilestoneRepository;
import com.issuetrackermax.domain.milestone.entity.Milestone;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;
	private final IssueRepository issueRepository;

	public Long getMilestoneCount() {
		return milestoneRepository.getMilestoneCount();
	}

	public List<MilestoneDetailResponse> getOpenMilestone() {
		List<Milestone> openMilestone = milestoneRepository.getOpenMilestone();
		return getMilestoneDetailResponses(openMilestone);
	}

	public List<MilestoneDetailResponse> getClosedMilestone() {
		List<Milestone> closedMilestone = milestoneRepository.getClosedMilestone();
		return getMilestoneDetailResponses(closedMilestone);

	}

	private List<MilestoneDetailResponse> getMilestoneDetailResponses(List<Milestone> openMilestone) {
		List<MilestoneDetailResponse> response =
			openMilestone.stream()
				.map(milestone -> {
					long openIssueCount = issueRepository.findByMilestoneId(milestone.getId())
						.stream()
						.filter(Issue::getIsOpen)
						.count();

					long closedIssueCount = issueRepository.findByMilestoneId(milestone.getId())
						.stream()
						.filter(issue -> !issue.getIsOpen())
						.count();

					return MilestoneDetailResponse.builder()
						.id(milestone.getId())
						.description(milestone.getDescription())
						.name(milestone.getTitle())
						.dueDate(milestone.getDuedate())
						.openIssueCount(openIssueCount)
						.closedIssueCount(closedIssueCount)
						.build();
				})
				.collect(Collectors.toList());

		return response;
	}

	public Long save(MilestonePostRequest milestonePostRequest) {
		Milestone milestone = Milestone.from(milestonePostRequest);
		return milestoneRepository.save(milestone);
	}

	public void update(Long id, MilestoneModifyRequest milestoneModifyRequest) {
		milestoneRepository.update(id, Milestone.from(milestoneModifyRequest));
		return;
	}

	public void delete(Long id) {
		int count = milestoneRepository.deleteById(id);
		return;
	}

	public void updateStatus(Long id) {
		milestoneRepository.updateStatus(id);
		return;
	}
}
