package codesquad.issueTracker.issue.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.issueTracker.comment.service.CommentService;
import codesquad.issueTracker.global.common.Status;
import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.issue.domain.Issue;
import codesquad.issueTracker.issue.dto.IssueWriteRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueContentRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueContentResponseDto;
import codesquad.issueTracker.issue.dto.ModifyIssueStatusRequestDto;
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
			labels.stream()
				.map(labelId -> labelService.validateLabelsId(labelId))
				.map(existLabel -> issueRepository.insertLabels(savedIssueId, existLabel.getId()))
				.collect(Collectors.toList());
		}

		// assignee 리스트가 null 이 아니면 assignees( 유저 id )가  존재하는지 검증 후  assignees 테이블에 insert
		if (assignees != null) {
			duplicatedId(assignees);
			assignees.stream()
				.map(assigneesId -> userService.validateUserId(assigneesId))
				.map(existUser -> issueRepository.insertAssignees(savedIssueId, existUser.getId()))
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

	@Transactional
	public List<Long> modifyIssueStatus(ModifyIssueStatusRequestDto request) {
		List<Long> issueIds = request.getIssueIds();
		Boolean status = Status.from(request.getStatus()).getStatus();
		if (issueIds != null) {
			duplicatedId(issueIds);
			issueIds.stream()
				.map(issueId -> validateExistIssue(issueId))
				.map(existIssue -> issueRepository.modifyStatus(existIssue.getId(), status))
				.collect(Collectors.toList());
			;
		}
		return issueIds;

	}

	@Transactional
	public Long modifyIssueStatusInDetail(Long id, ModifyIssueStatusRequestDto request) {
		Boolean status = Status.from(request.getStatus()).getStatus();
		validateExistIssue(id);
		return issueRepository.modifyStatus(id, status);

	}

	private Issue validateExistIssue(Long issuesIds) {
		return issueRepository.findById(issuesIds).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ISSUES));
	}

	@Transactional
	public ModifyIssueContentResponseDto modifyIssueContent(Long id, ModifyIssueContentRequestDto request) {
		String modifiedContent = request.getContent();
		issueRepository.updateContent(id, modifiedContent);
		return new ModifyIssueContentResponseDto(modifiedContent);
	}
}
