package com.issuetrackermax.controller.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.filter.dto.request.FilterRequest;
import com.issuetrackermax.controller.filter.dto.response.FilterResponse;
import com.issuetrackermax.service.filter.FilterService;
import com.issuetrackermax.service.filter.dto.FilterInformation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FilterController {
	private static final String MEMBER_ID = "memberId";
	private final FilterService filterService;

	@GetMapping()
	public ApiResponse<FilterResponse> getFilteredIssues(@ModelAttribute FilterRequest filterRequest,
		HttpServletRequest httpServletRequest) {
		Integer memberId = (Integer)httpServletRequest.getAttribute(MEMBER_ID);

		FilterResponse mainPageIssue = filterService.getMainPageIssue(
			FilterInformation.from(filterRequest, memberId.longValue()));
		return ApiResponse.success(mainPageIssue);
	}

}
