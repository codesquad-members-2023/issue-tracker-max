package codesquard.app.milestone.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.api.errors.exception.NoSuchMilestoneException;
import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
import codesquard.app.milestone.dto.request.MilestoneStatusUpdateRequest;
import codesquard.app.milestone.dto.request.MilestoneUpdateRequest;
import codesquard.app.milestone.dto.response.MilestoneReadResponse;
import codesquard.app.milestone.dto.response.MilestonesResponse;
import codesquard.app.milestone.entity.Milestone;
import codesquard.app.milestone.entity.MilestoneStatus;
import codesquard.app.milestone.repository.MilestoneRepository;

@Transactional
@Service
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;

	public MilestoneService(final MilestoneRepository milestoneRepository) {
		this.milestoneRepository = milestoneRepository;
	}

	public Long saveMilestone(final MilestoneSaveRequest milestoneSaveRequest, final Long userId) {
		Milestone milestone = MilestoneSaveRequest.toEntity(milestoneSaveRequest, userId);
		return milestoneRepository.save(milestone)
			.orElseThrow(NoSuchMilestoneException::new);
	}

	public Long updateMilestone(final Long milestoneId, final MilestoneUpdateRequest milestoneUpdateRequest,
		final Long userId) {
		return milestoneRepository.updateBy(milestoneId,
			MilestoneUpdateRequest.toEntity(milestoneUpdateRequest, userId));
	}

	public Long updateMilestoneStatus(final Long milestoneId,
		final MilestoneStatusUpdateRequest milestoneStatusUpdateRequest) {
		return milestoneRepository.updateBy(milestoneId, MilestoneStatusUpdateRequest.toStatus(
			milestoneStatusUpdateRequest));
	}

	public Long deleteMilestone(final Long milestoneId) {
		return milestoneRepository.deleteBy(milestoneId);
	}

	public MilestoneReadResponse makeMilestoneResponse(final MilestoneStatus status) {
		// 1. milestones 배열 (이 List가 여러개 있는 2차원 배열이라고 생각하면 됨)
		// makeIssues(): issues를 `countIssuesBy()`로 milestoneStatus별로 각각 1번씩 가져오기
		// id ~ deadline까지 `findAllBy()`로 가져와서 issues랑 합치기
		// List<MilestonesResponse> milestones = sumMilestonesResponseAndIssues(status, makeIssues());
		List<MilestonesResponse> milestones = new ArrayList<>();
		List<Milestone> getMilestones = milestoneRepository.findAllBy(status);
		for (Milestone getMilestone : getMilestones) {
			Long openedIssueCount = milestoneRepository.countIssuesBy(getMilestone.getId(), MilestoneStatus.OPENED);
			Long closedIssueCount = milestoneRepository.countIssuesBy(getMilestone.getId(), MilestoneStatus.CLOSED);
			MilestonesResponse milestonesResponse = MilestonesResponse.fromEntity(getMilestone,
				Map.of("openedIssueCount", openedIssueCount, "closedIssueCount", closedIssueCount));

			milestones.add(milestonesResponse);
		}
		// Map<String, Long> issues = new HashMap<>();
		// issues.put("openedIssueCount", milestoneRepository.countIssuesBy(MilestoneStatus.OPENED));
		// issues.put("closedIssueCount", milestoneRepository.countIssuesBy(MilestoneStatus.CLOSED));

		// 2. labelCount 가져오기
		Long labelCount = milestoneRepository.countLabels();

		// 3. openedMilestoneCount, closedMilestoneCount 가져오기 (List로 묶는 거 아님)
		Long openedMilestoneCount = milestoneRepository.countMilestonesBy(MilestoneStatus.OPENED);
		Long closedMilestoneCount = milestoneRepository.countMilestonesBy(MilestoneStatus.CLOSED);

		return new MilestoneReadResponse(openedMilestoneCount, closedMilestoneCount, labelCount, status.getName(),
			milestones);
	}

	// private Map<String, Long> makeIssues() {
	// 	Map<String, Long> issues = new HashMap<>();
	// 	issues.put("openedIssueCount", milestoneRepository.countIssuesBy(MilestoneStatus.OPENED));
	// 	issues.put("closedIssueCount", milestoneRepository.countIssuesBy(MilestoneStatus.CLOSED));
	//
	// 	return issues;
	// }

	// private List<MilestonesResponse> sumMilestonesResponseAndIssues(final MilestoneStatus status,
	// 	final Map<String, Long> issues) {
	// 	return milestoneRepository.findAllBy(status)
	// 		.stream()
	// 		// 미리 생성해둔 issues 배열을 각 milestone 객체마다 넣어줌
	// 		.map(milestone -> MilestonesResponse.fromEntity(milestone, issues))
	// 		.collect(Collectors.toUnmodifiableList());
	// }
}
