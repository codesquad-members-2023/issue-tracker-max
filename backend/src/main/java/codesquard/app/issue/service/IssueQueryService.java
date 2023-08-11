package codesquard.app.issue.service;

import static codesquard.app.issue.mapper.response.filters.SingleFilters.*;

import java.util.ArrayList;
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
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.issue.mapper.IssueMapper;
import codesquard.app.issue.mapper.request.IssueFilterRequest;
import codesquard.app.issue.mapper.response.IssueFilterResponse;
import codesquard.app.issue.mapper.response.IssuesResponse;
import codesquard.app.issue.mapper.response.filters.MultiFilters;
import codesquard.app.issue.mapper.response.filters.SingleFilters;
import codesquard.app.issue.mapper.response.filters.response.SingleFiltersList;
import codesquard.app.issue.repository.IssueRepository;
import codesquard.app.label.repository.LabelRepository;
import codesquard.app.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class IssueQueryService {

	private final IssueRepository issueRepository;
	private final LabelRepository labelRepository;
	private final MilestoneRepository milestoneRepository;
	private final IssueMapper issueMapper;

	private static Long id = 0L;
	private static final String SPACE = " ";

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
			builder.append("milestone:").append(request.getMentions()).append(SPACE);
		}
		if (request.getLabel().size() > 0) {
			for (String label : request.getLabel()) {
				builder.append("label:").append(label).append(SPACE);
			}
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

	private SingleFiltersList checkSingleFiltersList(IssueFilterRequest request) {
		SingleFilters singleFilters = checkSingleFilters(request);

		// for문으로 List 만들어줘서 SingleFiltersList에 담기

		return new SingleFiltersList();
	}

	private SingleFilters checkSingleFilters(IssueFilterRequest request) {
		boolean selectedIsOpened = request.getIs().equalsIgnoreCase(SingleFilters.IS.OPENED.getType());
		boolean selectedIsClosed = request.getIs().equalsIgnoreCase(SingleFilters.IS.CLOSED.getType());
		// TODO: @me 판단 로직 추가
		boolean selectedAuthorMe = request.getAuthor().equalsIgnoreCase("@me");
		boolean selectedAssigneeMe = request.getAssignee().equalsIgnoreCase("@me");
		boolean selectedMentionsMe = request.getMentions().equalsIgnoreCase("@me");

		if (selectedAuthorMe) {
			if (selectedIsOpened) {
				return new SingleFilters(id++, NAME_AUTHOR, new ArrayList<>(List.of(IS_OPENED, AUTHOR_ME)), true);
			}

			if (selectedIsClosed) {
				return new SingleFilters(id++, NAME_AUTHOR, new ArrayList<>(List.of(IS_CLOSED, AUTHOR_ME)), true);
			}
		}

		if (selectedAssigneeMe) {
			if (selectedIsOpened) {
				return new SingleFilters(id++, NAME_ASSIGNEE, new ArrayList<>(List.of(IS_OPENED, ASSIGNEE_ME)), true);
			}

			if (selectedIsClosed) {
				return new SingleFilters(id++, NAME_ASSIGNEE, new ArrayList<>(List.of(IS_CLOSED, ASSIGNEE_ME)), true);
			}
		}

		if (selectedMentionsMe) {
			if (selectedIsOpened) {
				return new SingleFilters(id++, NAME_MENTIONS, new ArrayList<>(List.of(IS_OPENED, MENTIONS_ME)), true);
			}

			if (selectedIsClosed) {
				return new SingleFilters(id++, NAME_MENTIONS, new ArrayList<>(List.of(IS_CLOSED, MENTIONS_ME)), true);
			}
		}

		return null;
	}

	private SingleFilters checkIS(boolean selectedIsOpened, boolean selectedIsClosed,
		SingleFilters.IS is) {
		if (selectedIsOpened) {
			return new SingleFilters(id++, NAME_OPENED, new ArrayList<>(List.of(IS_OPENED)), true);
		}

		if (selectedIsClosed) {
			return new SingleFilters(id++, NAME_CLOSED, new ArrayList<>(List.of(IS_CLOSED)), true);
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
