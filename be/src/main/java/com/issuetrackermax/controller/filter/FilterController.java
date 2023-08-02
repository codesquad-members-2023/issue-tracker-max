package com.issuetrackermax.controller.filter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.filter.dto.FilterRequest;
import com.issuetrackermax.controller.filter.dto.FilterResponse;
import com.issuetrackermax.service.filter.FilterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FilterController {
	private final FilterService filterService;

	@GetMapping()
	public ApiResponse<FilterResponse> getFilteredIssues(@ModelAttribute FilterRequest filterRequest) {
		FilterResponse mainPageIssue = filterService.getMainPageIssue(filterRequest);
		return ApiResponse.success(mainPageIssue);
	}
}
