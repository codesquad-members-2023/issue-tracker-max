package codesquad.issueTracker.issue.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.issue.domain.Issue;
import codesquad.issueTracker.issue.dto.IssueWriteRequestDto;
import codesquad.issueTracker.issue.repository.IssueRepository;
import codesquad.issueTracker.label.repository.LabelRepository;
import codesquad.issueTracker.milestone.repository.MilestoneRepository;
import codesquad.issueTracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class IssueService {

	private final IssueRepository issueRepository;
	private final LabelRepository labelRepository;
	private final UserRepository userRepository;
	private final MilestoneRepository milestoneRepository;

	@Transactional
	public Long save(IssueWriteRequestDto request) {
		isExistMilestone(request.getMilestoneId());
		List<Long> labels = request.getLabels();
		List<Long> assignees = request.getAssignees();
		Issue issue = IssueWriteRequestDto.toEntity(request);
		Long savedIssueId = issueRepository.insert(issue);

		// 라벨 리스트가 null 이 아니면 해당 라벨이 존재하는지 검증 후  라벨과 이슈 연결 테이블에 insert
		if (labels != null) {
			duplicatedId(labels);
			labels.stream()
				.map(label -> labelRepository.findById(label)
					.orElseThrow(() -> new CustomException(ErrorCode.LABEL_FIND_FAILED)))
				.map(findLabel -> issueRepository.insertLabels(savedIssueId, findLabel.getId()))
				.collect(Collectors.toList());
		}

		// assignee 리스트가 null 이 아니면 assignees( 유저 id )가  존재하는지 검증 후  assignees 테이블에 insert
		if (assignees != null) {
			duplicatedId(assignees);
			assignees.stream()
				.map(user -> userRepository.findById(user)
					.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)))
				.map(findUser -> issueRepository.insertAssignees(savedIssueId, findUser.getId()))
				.collect(Collectors.toList());
		}
		return savedIssueId;
	}

	private void isExistMilestone(Long id) {
		if (!milestoneRepository.isExist(id)) {
			throw new CustomException(ErrorCode.NOT_FOUND_MILESTONE);
		}
	}

	private void duplicatedId(List<Long> list) {
		Set<Long> set = new HashSet<>();
		for (Long temp : list) {
			if (!set.add(temp)) {
				throw new CustomException(ErrorCode.DUPLICATE_OBJECT_FOUND);
			}
		}
	}
}
