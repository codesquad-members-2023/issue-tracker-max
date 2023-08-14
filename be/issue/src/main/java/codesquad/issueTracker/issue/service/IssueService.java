package codesquad.issueTracker.issue.service;

import codesquad.issueTracker.global.common.Status;
import codesquad.issueTracker.issue.dto.IssueOptionResponseDto;
import codesquad.issueTracker.issue.dto.IssueLabelResponseDto;
import codesquad.issueTracker.issue.dto.IssueMilestoneResponseDto;
import codesquad.issueTracker.issue.vo.IssueLabelVo;
import codesquad.issueTracker.issue.vo.IssueMilestoneVo;
import codesquad.issueTracker.issue.dto.IssueResponseDto;
import codesquad.issueTracker.issue.dto.IssueUserResponseDto;
import codesquad.issueTracker.issue.vo.AssigneeVo;
import codesquad.issueTracker.label.dto.LabelResponseDto;

import codesquad.issueTracker.milestone.vo.MilestoneVo;
import codesquad.issueTracker.user.domain.User;
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
import codesquad.issueTracker.issue.dto.ModifyAssigneeRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueContentRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueContentResponseDto;
import codesquad.issueTracker.issue.dto.ModifyIssueMilestoneDto;
import codesquad.issueTracker.issue.dto.ModifyIssueStatusRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueTitleRequest;
import codesquad.issueTracker.issue.dto.ModifyIssueTitleResponse;
import codesquad.issueTracker.issue.dto.ModifyLabelRequestDto;
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

	public List<IssueUserResponseDto> getIssueUsers() {
		List<User> users = userService.getUsers();
		return users.stream()
				.map(IssueUserResponseDto::from)
				.collect(Collectors.toList());
	}

	public IssueResponseDto getIssueById(Long issueId) {
		validateExistIssue(issueId);
		Issue issue = validateActiveIssueById(issueId);

		return IssueResponseDto.from(issue);
	}

	private void validateExistIssue(Long issueId) {
		issueRepository.findById(issueId)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ISSUE));
	}

	private Issue validateActiveIssueById(Long issueId) {
		return issueRepository.findActiveIssueById(issueId)
				.orElseThrow(() -> new CustomException(ErrorCode.ALREADY_DELETED_ISSUE));
	}

	public IssueOptionResponseDto getIssueOptions(Long issueId) {
		validateExistIssue(issueId);
		validateActiveIssueById(issueId);

		List<AssigneeVo> assignees = issueRepository.findAssigneesById(issueId);
		List<IssueLabelVo> labels = labelService.findByIssueId(issueId);
		IssueMilestoneVo milestone = milestoneService.findByIssueId(issueId);

		if (milestone.getId() != null) {
			int closeCount = issueRepository.findCountByStatusAndMilestone(Status.CLOSED.getStatus(), milestone);
			int openCount = issueRepository.findCountByStatusAndMilestone(Status.OPEN.getStatus(), milestone);
			return IssueOptionResponseDto.of(assignees, labels, milestone.getMilestoneWithRatio(openCount, closeCount));
		}

		return IssueOptionResponseDto.of(assignees, labels, milestone);
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
		validateExistIssue(id);
		String modifiedContent = request.getContent();
		issueRepository.updateContent(id, modifiedContent);
		return new ModifyIssueContentResponseDto(modifiedContent);
	}

	@Transactional
	public ModifyIssueTitleResponse modifyIssueTitle(Long id, ModifyIssueTitleRequest request) {
		validateExistIssue(id);
		String modifiedTitle = request.getTitle();
		issueRepository.updateTitle(id, modifiedTitle);
		return new ModifyIssueTitleResponse(modifiedTitle);
	}

	@Transactional
	public Long delete(Long id) {
		validateExistIssue(id);
		Long deletedId = issueRepository.delete(id);
		return deletedId;

	}

	@Transactional
	public Long modifyAssignees(Long id, ModifyAssigneeRequestDto request) {
		validateExistIssue(id);
		List<Long> assignees = request.getAssignees();

		if (assignees != null) {
			duplicatedId(assignees);
			for (Long assigneeId : assignees) {
				userService.validateUserId(assigneeId);
			}
			issueRepository.resetAssignees(id);
			for (Long assigneeId : assignees) {
				issueRepository.insertAssignees(id, assigneeId);
			}
			return id;
		}
		issueRepository.resetAssignees(id);
		return id;
	}

	@Transactional
	public Long modifyLabels(Long id, ModifyLabelRequestDto request) {
		validateExistIssue(id);
		List<Long> labels = request.getLabels();

		if (labels != null) {
			duplicatedId(labels);
			for (Long labelId : labels) {
				labelService.validateLabelsId(labelId);
			}
			labelService.resetLabels(id);
			for (Long labelId : labels) {
				labelService.insertIssuesLabels(id, labelId);
			}
			return id;
		}
		labelService.resetLabels(id);
		return id;
	}

	@Transactional
	public Long modifyMilestone(Long id, ModifyIssueMilestoneDto request) {
		validateExistIssue(id);
		Long milestoneId = request.getMilestone();
		if (milestoneId != null) {
			milestoneService.isExistMilestone(milestoneId);
		}
		return issueRepository.updateMilestone(id, milestoneId);
	}
}
