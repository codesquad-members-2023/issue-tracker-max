package codesquad.issueTracker.issue.service;

import codesquad.issueTracker.global.common.Status;
import codesquad.issueTracker.issue.dto.IssueLabelResponseDto;
import codesquad.issueTracker.issue.dto.IssueMilestoneResponseDto;
import codesquad.issueTracker.label.dto.LabelResponseDto;
import codesquad.issueTracker.milestone.vo.MilestoneVo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.issueTracker.comment.service.CommentService;
import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.issue.domain.Issue;
import codesquad.issueTracker.issue.dto.IssueWriteRequestDto;
import codesquad.issueTracker.issue.repository.IssueRepository;
import codesquad.issueTracker.label.service.LabelService;
import codesquad.issueTracker.milestone.service.MilestoneService;
import codesquad.issueTracker.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class IssueService {

	private final IssueRepository issueRepository;
	private final LabelService labelService;
	private final UserService userService;
	private final MilestoneService milestoneService;
	private final CommentService commentService;

	@Transactional
	public Long save(IssueWriteRequestDto request, Long id) {
		milestoneService.isExistMilestone(request.getMilestoneId());
		List<Long> labels = request.getLabels();
		List<Long> assignees = request.getAssignees();
		Issue issue = IssueWriteRequestDto.toEntity(request, id);
		Long savedIssueId = issueRepository.insert(issue);

		// 라벨 리스트가 null 이 아니면 해당 라벨이 존재하는지 검증 후  라벨과 이슈 연결 테이블에 insert
		if (labels != null) {
			duplicatedId(labels);
			labelService.validateLabelsId(labels);
			labels.stream()
				.map(findLabel -> issueRepository.insertLabels(savedIssueId, findLabel))
				.collect(Collectors.toList());
		}

		// assignee 리스트가 null 이 아니면 assignees( 유저 id )가  존재하는지 검증 후  assignees 테이블에 insert
		if (assignees != null) {
			duplicatedId(assignees);
			userService.validateUserIds(assignees);
			assignees.stream()
				.map(findUser -> issueRepository.insertAssignees(savedIssueId, findUser))
				.collect(Collectors.toList());
		}
		return savedIssueId;
	}

	private void duplicatedId(List<Long> list) {
		Set<Long> set = new HashSet<>();
		for (Long temp : list) {
			if (!set.add(temp)) {
				throw new CustomException(ErrorCode.DUPLICATE_OBJECT_FOUND);
			}
		}
	}

	public List<IssueLabelResponseDto> getIssueLabels() {
		LabelResponseDto allLabels = labelService.findAll();
		return allLabels.getLabels().stream()
				.map(IssueLabelResponseDto::from)
				.collect(Collectors.toList());
	}

	public List<IssueMilestoneResponseDto> getIssueMilestones() {
		List<MilestoneVo> milestones = milestoneService.findMilestonesByStatus(Status.OPEN.getStatus());
		return milestones.stream()
				.map(IssueMilestoneResponseDto::from)
				.collect(Collectors.toList());
	}
}
