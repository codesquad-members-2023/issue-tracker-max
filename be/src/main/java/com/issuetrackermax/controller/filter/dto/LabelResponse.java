package com.issuetrackermax.controller.filter.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelResponse {

	private Long id;
	private String title;

	@Builder
	public LabelResponse(Long id, String title) {
		this.id = id;
		this.title = title;
	}

	public static List<LabelResponse> convertToLabelResponseList(String labelIds, String labelTitles) {
		if (labelIds == null) {
			return null;
		}
		List<String> ids = List.of(labelIds.split(","));
		List<String> titles = List.of(labelTitles.split(","));
		return IntStream.range(0, ids.size())
			.mapToObj(i -> LabelResponse.builder()
				.id(Long.parseLong(ids.get(i)))
				.title(titles.get(i))
				.build())
			.collect(Collectors.toList());
	}

}
