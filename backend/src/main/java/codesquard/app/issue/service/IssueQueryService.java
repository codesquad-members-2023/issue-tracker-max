package codesquard.app.issue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.api.errors.errorcode.IssueErrorCode;
import codesquard.app.api.errors.exception.NoSuchIssueException;
import codesquard.app.api.errors.exception.RestApiException;
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
import codesquard.app.issue.mapper.response.filters.response.MultiFilterAssignees;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterAuthors;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterLabels;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterMilestones;
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
	private static final Long OPENED_ID = 1L;
	private static final Long AUTHOR_ID = 2L;
	private static final Long ASSIGNEE_ID = 3L;
	private static final Long MENTIONS_ID = 4L;
	private static final Long CLOSED_ID = 5L;

	private boolean multiFiltersCheck = false;

	public IssueReadResponse get(Long issueId, Long userId) {
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

	public void validateExistIssues(List<Long> issues) {
		if (issueMapper.isNotExist(issues)) {
			throw new NoSuchIssueException();
		}
	}

	public void validateIssueAuthor(Long issueId, Long userId) {
		validateExistIssue(issueId);
		if (!issueRepository.isSameIssueAuthor(issueId, userId)) {
			throw new RestApiException(IssueErrorCode.FORBIDDEN_ISSUE);
		}
	}

	public void validateIssuesAuthor(List<Long> issues, Long userId) {
		validateExistIssues(issues);
		if (issues.size() > issueMapper.countIssueSameAuthor(issues, userId)) {
			throw new RestApiException(IssueErrorCode.FORBIDDEN_ISSUE);
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

		return new IssueFilterResponse(generateInput(request), counts.get(IssueStatus.OPENED.name()),
			counts.get(IssueStatus.CLOSED.name()), labelRepository.countAll(), milestoneRepository.countAll(),
			findIssues(loginId, request), generateSingleFilters(request),
			checkMultiFilters(multiFiltersCheck, request));
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
		if (request.getLabel() != null && !request.getLabel().isEmpty()) {
			for (String label : request.getLabel()) {
				builder.append("label:").append(label).append(SPACE);
			}
		}

		if (builder.length() == 0) {
			return "is:opened";
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

	public List<SingleFilter> generateSingleFilters(IssueFilterRequest request) {
		List<SingleFilter> singleFilters = new ArrayList<>();
		String generated = generateInput(request);

		boolean selectedOpened = generated.strip().equals(SingleFilter.IS.OPENED.getResponse());
		singleFilters.add(
			new SingleFilter(OPENED_ID, SingleFilter.IS.OPENED.getName(), SingleFilter.IS.OPENED.getResponse(),
				selectedOpened));

		boolean selectedAuthor = generated.strip().equals(SingleFilter.ME.AUTHOR.getResponse());
		singleFilters.add(
			new SingleFilter(AUTHOR_ID, SingleFilter.ME.AUTHOR.getName(), SingleFilter.ME.AUTHOR.getResponse(),
				selectedAuthor));

		boolean selectedAssignee = generated.strip().equals(SingleFilter.ME.ASSIGNEE.getResponse());
		singleFilters.add(
			new SingleFilter(ASSIGNEE_ID, SingleFilter.ME.ASSIGNEE.getName(), SingleFilter.ME.ASSIGNEE.getResponse(),
				selectedAssignee));

		boolean selectedMentions = generated.strip().equals(SingleFilter.ME.MENTIONS.getResponse());
		singleFilters.add(
			new SingleFilter(MENTIONS_ID, SingleFilter.ME.MENTIONS.getName(), SingleFilter.ME.MENTIONS.getResponse(),
				selectedMentions));

		boolean selectedClosed = generated.strip().equals(SingleFilter.IS.CLOSED.getResponse());
		singleFilters.add(
			new SingleFilter(CLOSED_ID, SingleFilter.IS.CLOSED.getName(), SingleFilter.IS.CLOSED.getResponse(),
				selectedClosed));

		multiFiltersCheck =
			!selectedOpened && !selectedClosed && !selectedAuthor && !selectedAssignee && !selectedMentions;

		return singleFilters;
	}

	private MultiFilters checkMultiFilters(boolean check, IssueFilterRequest request) {
		MultiFilters multiFilters = new MultiFilters(
			new MultiFilterAssignees(issueMapper.getMultiFiltersAssignees(check, request)),
			new MultiFilterLabels(issueMapper.getMultiFiltersLabels(check, request)),
			new MultiFilterMilestones(issueMapper.getMultiFiltersMilestones(check, request)),
			new MultiFilterAuthors(issueMapper.getMultiFiltersAuthors(check, request)));
		multiFilters.getAssignees()
			.addNoneOptionToAssignee(request.getAssignee() != null && request.getAssignee().equals("none"));
		multiFilters.getLabels()
			.addNoneOptionToLabels(request.getLabel() != null && request.getLabel().get(0).equals("none"));
		multiFilters.getMilestones()
			.addNoneOptionToMilestones(request.getMilestone() != null && request.getMilestone().equals("none"));
		return multiFilters;
	}

}
