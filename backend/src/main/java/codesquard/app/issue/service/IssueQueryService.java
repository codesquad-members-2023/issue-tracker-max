package codesquard.app.issue.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.api.errors.exception.NoSuchIssueException;
import codesquard.app.issue.dto.response.IssueCommentsResponse;
import codesquard.app.issue.dto.response.IssueLabelResponse;
import codesquard.app.issue.dto.response.IssueMilestoneCountResponse;
import codesquard.app.issue.dto.response.IssueMilestoneResponse;
import codesquard.app.issue.dto.response.IssueReadResponse;
import codesquard.app.issue.dto.response.IssueUserResponse;
import codesquard.app.issue.dto.response.userReactionResponse;
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.issue.mapper.IssueMapper;
import codesquard.app.issue.mapper.request.IssueFilterRequest;
import codesquard.app.issue.mapper.response.IssueFilterResponse;
import codesquard.app.issue.mapper.response.IssuesResponse;
import codesquard.app.issue.mapper.response.filters.MultiFilters;
import codesquard.app.issue.mapper.response.filters.SingleFilters;
import codesquard.app.issue.mapper.response.filters.response.SingleFilter;
import codesquard.app.issue.repository.IssueRepository;
import codesquard.app.label.repository.LabelRepository;
import codesquard.app.milestone.repository.MilestoneRepository;
import codesquard.app.user_reaction.repository.UserReactionRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class IssueQueryService {

	private final IssueRepository issueRepository;
	private final LabelRepository labelRepository;
	private final MilestoneRepository milestoneRepository;
	private final UserReactionRepository userReactionRepository;
	private final IssueMapper issueMapper;

	private static final String SPACE = " ";

	public IssueReadResponse get(Long issueId, Long userId) {
		validateExistIssue(issueId);
		IssueReadResponse issueReadResponse = issueRepository.findBy(issueId);
		List<userReactionResponse> users = userReactionRepository.findIssueReactionBy(issueId, userId);
		List<IssueUserResponse> assignees = IssueUserResponse.from(issueRepository.findAssigneesBy(issueId));
		List<IssueLabelResponse> labels = IssueLabelResponse.from(issueRepository.findLabelsBy(issueId));
		IssueMilestoneCountResponse issueMilestoneCountResponse = countIssueForMilestone(
			issueReadResponse.getMilestone());
		List<IssueCommentsResponse> issueCommentsResponse = issueRepository.findCommentsBy(issueId, userId);
		return issueReadResponse.from(users, assignees, labels, issueMilestoneCountResponse, issueCommentsResponse);
	}

	private IssueMilestoneCountResponse countIssueForMilestone(IssueMilestoneResponse milestone) {
		if (milestone != null) {
			return issueRepository.countIssueBy(milestone.getId());
		}
		return null;
	}

	public void validateExistIssue(Long issueId) {
		if (!issueRepository.isExist(issueId)) {
			throw new NoSuchIssueException();
		}
	}

	public IssueReadResponse findById(Long issueId) {
		return issueRepository.findBy(issueId);
	}

	public List<IssueUserResponse> findAssigneesById(Long issueId) {
		return IssueUserResponse.from(issueRepository.findAssigneesBy(issueId));
	}

	public List<IssueLabelResponse> findLabelsById(Long issueId) {
		return IssueLabelResponse.from(issueRepository.findLabelsBy(issueId));
	}

	// Issue Filtering
	public IssueFilterResponse findFilterIssues(String loginId, IssueFilterRequest request) {
		Map<String, Long> counts = countIssuesByStatus();
		SingleFilters singleFilters = checkSingleFilters(request);
		boolean multiFiltersCheck = singleFilters == null;

		return new IssueFilterResponse(generateInput(request), counts.get(IssueStatus.OPENED.name()),
			counts.get(IssueStatus.CLOSED.name()), labelRepository.countAll(), milestoneRepository.countAll(),
			findIssues(loginId, request), singleFilters, checkMultiFilters(multiFiltersCheck, request));
	}

	private String generateInput(IssueFilterRequest request) {
		StringBuilder builder = new StringBuilder();
		if (request.getIs() != null) {
			builder.append("is:").append(request.getIs()).append(SPACE);
		}
		if (request.getAuthor() != null) {
			builder.append("author:").append(request.getAuthor()).append(SPACE);
		}
		if (request.getAssignee() != null) {
			builder.append("assignee:").append(request.getAssignee()).append(SPACE);
		}
		if (request.getMentions() != null) {
			builder.append("mentions:").append(request.getMentions()).append(SPACE);
		}
		if (request.getMilestone() != null) {
			builder.append("milestone:").append(request.getMilestone()).append(SPACE);
		}
		if (request.getLabel() != null && request.getLabel().size() > 0) {
			for (String label : request.getLabel()) {
				builder.append("label:").append(label).append(SPACE);
			}
		}

		if (builder.length() == 0) {
			return "";
		}

		return builder.toString();
	}

	private Map<String, Long> countIssuesByStatus() {
		return Map.of(IssueStatus.OPENED.name(), issueRepository.countIssueByStatus(IssueStatus.OPENED),
			IssueStatus.CLOSED.name(), issueRepository.countIssueByStatus(IssueStatus.CLOSED));
	}

	private List<IssuesResponse> findIssues(String loginId, IssueFilterRequest request) {
		return issueMapper.getIssues(request.convertMe(loginId));
	}

	public SingleFilters checkSingleFilters(IssueFilterRequest request) {
		// TODO: 지금 여러개가 들어왔을 때 싱글필터로 인식되는 문제가 있음 -> 이거 해결하기

		if (request.getIs() != null && request.getIs().equalsIgnoreCase(SingleFilter.IS.OPENED.getType())) {
			SingleFilters singleFilters = new SingleFilters();
			singleFilters.changeBy(true, SingleFilters.OPENED_ID - 1L);
			return singleFilters;
		}

		if (request.getIs() != null && request.getIs().equalsIgnoreCase(SingleFilter.IS.CLOSED.getType())) {
			SingleFilters singleFilters = new SingleFilters();
			singleFilters.changeBy(true, SingleFilters.CLOSED_ID - 1L);
			return singleFilters;
		}

		if (request.getAuthor() != null && request.getAuthor().equalsIgnoreCase(SingleFilter.ME.AUTHOR.getType())) {
			SingleFilters singleFilters = new SingleFilters();
			singleFilters.changeBy(true, SingleFilters.AUTHOR_ID - 1L);
			return singleFilters;
		}

		if (request.getAssignee() != null && request.getAssignee()
			.equalsIgnoreCase(SingleFilter.ME.ASSIGNEE.getType())) {
			SingleFilters singleFilters = new SingleFilters();
			singleFilters.changeBy(true, SingleFilters.ASSIGNEE_ID - 1L);
			return singleFilters;
		}

		if (request.getMentions() != null && request.getMentions()
			.equalsIgnoreCase(SingleFilter.ME.MENTIONS.getType())) {
			SingleFilters singleFilters = new SingleFilters();
			singleFilters.changeBy(true, SingleFilters.MENTIONS_ID - 1L);
			return singleFilters;
		}

		return null;
	}

	private MultiFilters checkMultiFilters(boolean check, IssueFilterRequest request) {
		return new MultiFilters(
			issueMapper.getMultiFiltersAssignees(check, request),
			issueMapper.getMultiFiltersLabels(check, request),
			issueMapper.getMultiFiltersMilestones(check, request),
			issueMapper.getMultiFiltersAuthors(check, request)
		);
	}

}
