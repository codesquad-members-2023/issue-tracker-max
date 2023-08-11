package codesquard.app.issue.service;

import static codesquard.app.issue.mapper.response.filters.SingleFilters.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.api.errors.exception.NoSuchIssueException;
import codesquard.app.issue.dto.response.IssueCommentsResponse;
import codesquard.app.issue.dto.response.IssueLabelResponse;
import codesquard.app.issue.dto.response.IssueMilestoneCountResponse;
import codesquard.app.issue.dto.response.IssueMilestoneResponse;
import codesquard.app.issue.dto.response.IssueReadResponse;
import codesquard.app.issue.dto.response.IssueUserResponse;
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.issue.mapper.IssueMapper;
import codesquard.app.issue.mapper.request.IssueFilterRequest;
import codesquard.app.issue.mapper.response.IssueFilterResponse;
import codesquard.app.issue.mapper.response.IssuesResponse;
import codesquard.app.issue.mapper.response.filters.MultiFilters;
import codesquard.app.issue.mapper.response.filters.SingleFilters;
import codesquard.app.issue.mapper.response.filters.response.SingleFiltersList;
import codesquard.app.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class IssueQueryService {

	private final IssueRepository issueRepository;
	private final IssueMapper issueMapper;
	private static Long id = 0L;

	public IssueReadResponse get(Long issueId) {
		validateExistIssue(issueId);
		IssueReadResponse issueReadResponse = issueRepository.findBy(issueId);
		List<IssueUserResponse> assignees = IssueUserResponse.from(issueRepository.findAssigneesBy(issueId));
		List<IssueLabelResponse> labels = IssueLabelResponse.from(issueRepository.findLabelsBy(issueId));
		IssueMilestoneCountResponse issueMilestoneCountResponse = countIssueForMilestone(
			issueReadResponse.getMilestone());
		List<IssueCommentsResponse> issueCommentsResponse = issueRepository.findCommentsBy(issueId);
		return issueReadResponse.from(assignees, labels, issueMilestoneCountResponse, issueCommentsResponse);
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

	public IssueFilterResponse findFilterIssues(IssueFilterRequest request) {
		HashMap<String, Long> counts = countIssuesByStatus(request);
		SingleFiltersList singleFiltersList = checkSingleFilters(request);
		boolean multiFiltersCheck = singleFiltersList == null;
		return new IssueFilterResponse(
			generateInput(request),
			counts.get(IssueStatus.OPENED.name()),
			counts.get(IssueStatus.CLOSED.name()),
			findIssues(request),
			singleFiltersList,
			checkMultiFilters(multiFiltersCheck, request)
		);
	}

	private HashMap<String, Long> countIssuesByStatus(IssueFilterRequest request) {
		return null;
	}

	private String generateInput(IssueFilterRequest request) {
		return null;
	}

	private List<IssuesResponse> findIssues(IssueFilterRequest request) {
		return issueMapper.getIssues(request);
	}

	private SingleFiltersList checkSingleFilters(IssueFilterRequest request) {
		boolean selectedIsOpened = request.getIs().equalsIgnoreCase(SingleFilters.IS.OPENED.getType());
		boolean selectedIsClosed = request.getIs().equalsIgnoreCase(SingleFilters.IS.CLOSED.getType());
		boolean selectedAuthorMe = request.getAuthor().equalsIgnoreCase("@me");
		boolean selectedAssigneeMe = request.getAssignee().equalsIgnoreCase("@me");
		boolean selectedMentionsMe = request.getMentions().equalsIgnoreCase("@me");

		if (selectedIsOpened) {
			return new SingleFiltersList(new ArrayList<>(List.of(
				new SingleFilters(id++, IS.OPENED.getName(), IS.OPENED.getType(), true),
				new SingleFilters(id++, ME.AUTHOR.getName(), ME.AUTHOR.getType(), false),
				new SingleFilters(id++, ME.ASSIGNEE.getName(), ME.ASSIGNEE.getType(), false),
				new SingleFilters(id++, ME.MENTIONS.getName(), ME.MENTIONS.getType(), false),
				new SingleFilters(id++, IS.CLOSED.getName(), IS.CLOSED.getType(), false)
			)));
		}

		if (selectedIsClosed) {
			return new SingleFiltersList(new ArrayList<>(List.of(
				new SingleFilters(id++, IS.OPENED.getName(), IS.OPENED.getType(), false),
				new SingleFilters(id++, ME.AUTHOR.getName(), ME.AUTHOR.getType(), false),
				new SingleFilters(id++, ME.ASSIGNEE.getName(), ME.ASSIGNEE.getType(), false),
				new SingleFilters(id++, ME.MENTIONS.getName(), ME.MENTIONS.getType(), false),
				new SingleFilters(id++, IS.CLOSED.getName(), IS.CLOSED.getType(), true)
			)));
		}

		if (selectedAuthorMe) {
			return new SingleFiltersList(new ArrayList<>(List.of(
				new SingleFilters(id++, IS.OPENED.getName(), IS.OPENED.getType(), false),
				new SingleFilters(id++, ME.AUTHOR.getName(), ME.AUTHOR.getType(), true),
				new SingleFilters(id++, ME.ASSIGNEE.getName(), ME.ASSIGNEE.getType(), false),
				new SingleFilters(id++, ME.MENTIONS.getName(), ME.MENTIONS.getType(), false),
				new SingleFilters(id++, IS.CLOSED.getName(), IS.CLOSED.getType(), false)
			)));
		}

		if (selectedAssigneeMe) {
			return new SingleFiltersList(new ArrayList<>(List.of(
				new SingleFilters(id++, IS.OPENED.getName(), IS.OPENED.getType(), false),
				new SingleFilters(id++, ME.AUTHOR.getName(), ME.AUTHOR.getType(), false),
				new SingleFilters(id++, ME.ASSIGNEE.getName(), ME.ASSIGNEE.getType(), true),
				new SingleFilters(id++, ME.MENTIONS.getName(), ME.MENTIONS.getType(), false),
				new SingleFilters(id++, IS.CLOSED.getName(), IS.CLOSED.getType(), false)
			)));
		}

		if (selectedMentionsMe) {
			return new SingleFiltersList(new ArrayList<>(List.of(
				new SingleFilters(id++, IS.OPENED.getName(), IS.OPENED.getType(), false),
				new SingleFilters(id++, ME.AUTHOR.getName(), ME.AUTHOR.getType(), false),
				new SingleFilters(id++, ME.ASSIGNEE.getName(), ME.ASSIGNEE.getType(), false),
				new SingleFilters(id++, ME.MENTIONS.getName(), ME.MENTIONS.getType(), true),
				new SingleFilters(id++, IS.CLOSED.getName(), IS.CLOSED.getType(), false)
			)));
		}

		return null;
	}

	private MultiFilters checkMultiFilters(boolean check, IssueFilterRequest request) {
		return new MultiFilters(
			issueMapper.getMultiFiltersAssignees(check, request),
			issueMapper.getMultiFiltersAuthors(check, request),
			issueMapper.getMultiFiltersLabels(check, request),
			issueMapper.getMultiFiltersMilestones(check, request)
		);
	}

}
