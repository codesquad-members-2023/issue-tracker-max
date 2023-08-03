package com.issuetrackermax.controller.filter.dto.response;

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

	public static List<AssigneeResponse> convertToAssigneeResponseList(String assigneeIds, String assigneeNames) {
		if (assigneeIds == null) {
			return null;
		}
		List<String> ids = List.of(assigneeIds.split(","));
		List<String> names = List.of(assigneeNames.split(","));
		return IntStream.range(0, ids.size())
			.mapToObj(i -> AssigneeResponse.builder()
				.id(Long.parseLong(ids.get(i).trim()))
				.name(names.get(i).trim())
				.build())
			.collect(Collectors.toList());
	}

}
