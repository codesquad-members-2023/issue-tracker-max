package com.issuetrackermax.service.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.controller.filter.dto.response.FilterResponse;
import com.issuetrackermax.controller.filter.dto.response.IssueResponse;
import com.issuetrackermax.domain.filter.FilterMapper;
import com.issuetrackermax.domain.filter.FilterResultVO;
import com.issuetrackermax.domain.issue.IssueRepository;
import com.issuetrackermax.domain.label.LabelRepository;
import com.issuetrackermax.domain.milestone.MilestoneRepository;
import com.issuetrackermax.service.filter.dto.FilterInformation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FilterService {
	private final FilterMapper filterMapper;
	private final MilestoneRepository milestoneRepository;
	private final IssueRepository issueRepository;
	private final LabelRepository labelRepository;

	@Transactional(readOnly = true)
	public FilterResponse getMainPageIssue(FilterInformation filterInformation) {
		List<FilterResultVO> filterResultVOS = getFilterVO(filterInformation);
		return FilterResponse.builder()
			.labelCount(getLabelCount())
			.mileStoneCount(getMilestoneCount())
			.closedIssueCount(getClosedIssueCount())
			.openIssueCount(getOpenIssueCount())
			.issues(getIssues(filterResultVOS))
			.build();
	}

	private List<IssueResponse> getIssues(List<FilterResultVO> filterResultVOS) {
		if (filterResultVOS.size() == 0) {
			return null;
		}
		return filterResultVOS.stream()
			.map(i -> IssueResponse.builder().resultVO(i).build())
			.collect(Collectors.toList());
	}

	private List<FilterResultVO> getFilterVO(FilterInformation filterInformation) {
		return filterMapper.getFilteredList(filterInformation);
	}

	private Long getMilestoneCount() {
		return milestoneRepository.getMilestoneCount();
	}

	private Long getLabelCount() {
		return labelRepository.getLabelCount();
	}

	private Long getOpenIssueCount() {
		return ((long)issueRepository.getOpenIssue().size());
	}

	private Long getClosedIssueCount() {
		return (long)issueRepository.getClosedIssue().size();

	}

}
