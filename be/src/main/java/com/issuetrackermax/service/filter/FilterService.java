package com.issuetrackermax.service.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.issuetrackermax.controller.filter.dto.FilterResponse;
import com.issuetrackermax.controller.filter.dto.IssueResponse;
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

	public List<IssueResponse> getIssues(List<FilterResultVO> filterResultVOS) {
		if (filterResultVOS.size() == 0) {
			return null;
		}
		return filterResultVOS.stream()
			.map(i -> IssueResponse.builder().resultVO(i).build())
			.collect(Collectors.toList());
	}

	public List<FilterResultVO> getFilterVO(FilterInformation filterInformation) {
		return filterMapper.getFilteredList(filterInformation);
	}

	public Long getMilestoneCount() {
		return milestoneRepository.getMilestoneCount();
	}

	public Long getLabelCount() {
		return labelRepository.getLabelCount();
	}

	public Long getOpenIssueCount() {
		return ((long)issueRepository.getOpenIssue().size());
	}

	public Long getClosedIssueCount() {
		return (long)issueRepository.getClosedIssue().size();

	}

}
