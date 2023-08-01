package com.issuetrackermax.controller.issue.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AssigneeResponse {

	private Long id;
	private String name;

	@Builder
	public AssigneeResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static List<AssigneeResponse> convertToAssigneeResponseList(String laassigneeIds, String assigneeNames) {
		List<String> ids = List.of(laassigneeIds.split(","));
		List<String> names = List.of(assigneeNames.split(","));
		return IntStream.range(0, ids.size())
			.mapToObj(i -> AssigneeResponse.builder()
				.id(Long.parseLong(ids.get(i)))
				.name(names.get(i))
				.build())
			.collect(Collectors.toList());
	}

}
