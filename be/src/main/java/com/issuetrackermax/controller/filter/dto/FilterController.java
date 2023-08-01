package com.issuetrackermax.controller.filter.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.issue.dto.response.IssueResponse;
import com.issuetrackermax.domain.filter.FilterMapper;
import com.issuetrackermax.domain.filter.FilterResultVO;
import com.issuetrackermax.service.filter.FilterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FilterController {
	private final FilterMapper filterMapper;
	private final FilterService filterService;

	@GetMapping()
	public ApiResponse<FilterResponse> getFilteredIssues(@RequestParam Map<String, Object> parameters) {
		List<FilterResultVO> resultVOS = filterMapper.getFilteredList(parameters);
		if (resultVOS.size() == 0) {
			return ApiResponse.success();
		}
		List<IssueResponse> issueResponses = resultVOS.stream()
			.map(i -> IssueResponse.builder().resultVO(i).build())
			.collect(
				Collectors.toList());
		FilterResponse filterResponse = FilterResponse.builder()
			.labelCount(filterService.getLabelCount())
			.mileStoneCount(filterService.getMilestoneCount())
			.closedIssueCount(filterService.getClosedIssueCount())
			.openIssueCount(filterService.getOpenIssueCount())
			.issues(issueResponses)
			.build();
		return ApiResponse.success(filterResponse);
	}
}
