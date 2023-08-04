package com.issuetrackermax.controller.filter.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelResponse {

	private Long id;
	private String title;
	private String textColor;
	private String backgroundColor;

	@Builder
	public LabelResponse(Long id, String title, String textColor, String backgroundColor) {
		this.id = id;
		this.title = title;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	public static List<LabelResponse> convertToLabelResponseList(String labelIds, String labelTitles,
		String labelTextColors, String labelBackgroundColors) {
		if (labelIds == null) {
			return null;
		}
		List<String> ids = List.of(labelIds.split(","));
		List<String> titles = List.of(labelTitles.split(","));
		List<String> textColors = List.of(labelTextColors.split(","));
		List<String> backgroundColors = List.of(labelBackgroundColors.split(","));

		return IntStream.range(0, ids.size())
			.mapToObj(i -> LabelResponse.builder()
				.id(Long.parseLong(ids.get(i)))
				.title(titles.get(i))
				.textColor(textColors.get(i))
				.backgroundColor(backgroundColors.get(i))
				.build())
			.collect(Collectors.toList());
	}

}
