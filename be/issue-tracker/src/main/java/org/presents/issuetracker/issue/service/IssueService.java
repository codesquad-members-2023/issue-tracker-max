package org.presents.issuetracker.issue.service;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.comment.repository.CommentRepository;
import org.presents.issuetracker.issue.dto.request.IssueCreateRequest;
import org.presents.issuetracker.issue.dto.request.IssueSearchParam;
import org.presents.issuetracker.issue.dto.request.IssueUpdateRequest;
import org.presents.issuetracker.issue.dto.response.IssueDetailResponse;
import org.presents.issuetracker.issue.dto.response.IssueSearch;
import org.presents.issuetracker.issue.dto.response.IssueSearchResponse;
import org.presents.issuetracker.issue.entity.Assignee;
import org.presents.issuetracker.issue.entity.Issue;
import org.presents.issuetracker.issue.entity.IssueLabel;
import org.presents.issuetracker.issue.entity.vo.IssueSearchCountInfo;
import org.presents.issuetracker.issue.entity.vo.IssueSearchInfo;
import org.presents.issuetracker.issue.mapper.IssueMapper;
import org.presents.issuetracker.issue.repository.IssueRepository;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
import org.presents.issuetracker.label.repository.LabelRepository;
import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.milestone.repository.MilestoneRepository;
import org.presents.issuetracker.user.dto.response.UserResponse;
import org.presents.issuetracker.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
	private final IssueRepository issueRepository;
	private final LabelRepository labelRepository;
	private final MilestoneRepository milestoneRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final IssueMapper issueMapper;

	@Transactional
	public Long create(IssueCreateRequest issueCreateRequest) {
		Long savedIssueId = issueRepository.save(
			Issue.builder()
				.title(issueCreateRequest.getTitle())
				.authorId(issueCreateRequest.getAuthorId())
				.contents(issueCreateRequest.getContents())
				.build()
		);

		addAssignees(issueCreateRequest.getAssigneeIds(), savedIssueId);
		addLabels(issueCreateRequest.getLabelIds(), savedIssueId);
		setMilestone(issueCreateRequest.getMilestoneId(), savedIssueId);

		return savedIssueId;
	}

	@Transactional
	public Long updateTitle(IssueUpdateRequest issueUpdateRequest) {
		validateId(issueUpdateRequest.getId());
		issueRepository.updateTitle(Issue.builder()
			.id(issueUpdateRequest.getId())
			.title(issueUpdateRequest.getTitle())
			.build());

		return issueUpdateRequest.getId();
	}

	@Transactional
	public Long updateContents(IssueUpdateRequest issueUpdateRequest) {
		validateId(issueUpdateRequest.getId());
		issueRepository.updateContents(Issue.builder()
			.id(issueUpdateRequest.getId())
			.contents(issueUpdateRequest.getContents())
			.build());

		return issueUpdateRequest.getId();
	}

	@Transactional
	public List<LabelPreviewResponse> updateLabels(List<Long> labelIds, Long issueId) {
		validateId(issueId);
		issueRepository.deleteAllLabel(issueId);
		addLabels(labelIds, issueId);

		return labelRepository.findByIssueId(issueId).stream()
			.map(LabelPreviewResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public List<UserResponse> updateAssignees(List<Long> assigneeIds, Long issueId) {
		validateId(issueId);
		issueRepository.deleteAllAssignee(issueId);
		addAssignees(assigneeIds, issueId);

		return userRepository.findByIssueId(issueId).stream()
			.map(UserResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public MilestonePreviewResponse updateMilestone(Long milestoneId, Long issueId) {
		validateId(issueId);
		if (milestoneId == 0) {
			issueRepository.deleteMilestone(issueId);
		} else {
			setMilestone(milestoneId, issueId);
		}

		return MilestonePreviewResponse.from(milestoneRepository.findByIssueId(issueId));
	}

	@Transactional
	public void updateStatus(List<Long> issueIds, String status) {
		validateIds(issueIds);
		issueRepository.updateStatus(issueIds, status);
	}

	@Transactional
	public void delete(Long issueId) {
		validateId(issueId);
		commentRepository.deleteByIssueId(issueId);
		issueRepository.deleteAllLabel(issueId);
		issueRepository.deleteAllAssignee(issueId);
		issueRepository.deleteMilestone(issueId);
		issueRepository.delete(issueId);
	}

	public IssueSearchResponse getIssues(IssueSearchParam issueSearchParam) {
		List<IssueSearchInfo> issues = issueMapper.getIssues(issueSearchParam);
		IssueSearchCountInfo counts = issueMapper.getIssueSearchCounts(issueSearchParam);
		return IssueSearchResponse.of(counts, IssueSearch.from(issues));
	}

	public IssueDetailResponse getIssueDetail(Long issueId) {
		validateId(issueId);
		return IssueDetailResponse.from(issueMapper.getIssueDetail(issueId));
	}

	private void addAssignees(List<Long> assigneeIds, Long issueId) {
		issueRepository.addAssignees(
			assigneeIds.stream()
				.map(assigneeId ->
					Assignee.builder()
						.issueId(issueId)
						.userId(assigneeId)
						.build()
				)
				.collect(Collectors.toList())
		);
	}

	private void addLabels(List<Long> labelIds, Long issueId) {
		issueRepository.addLabels(
			labelIds.stream()
				.map(labelId ->
					IssueLabel.builder()
						.issueId(issueId)
						.labelId(labelId)
						.build()
				)
				.collect(Collectors.toList())
		);
	}

	private void setMilestone(Long milestoneId, Long issueId) {
		issueRepository.setMilestone(issueId, milestoneId);
	}

	private void validateId(Long issueId) {
		if (!issueRepository.existsById(issueId)) {
			// todo: 커스텀 예외 생성 후 변경
			throw new RuntimeException("이슈를 찾을 수 없습니다.");
		}
	}

	private void validateIds(List<Long> issueIds) {
		int count = issueRepository.countByIssueIds(issueIds);
		if (count != issueIds.size()) {
			// todo: 커스텀 예외 생성 후 변경
			throw new RuntimeException("이슈를 찾을 수 없습니다.");
		}
	}
}
