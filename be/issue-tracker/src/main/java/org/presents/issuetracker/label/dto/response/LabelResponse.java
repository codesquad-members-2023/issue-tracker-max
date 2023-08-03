package org.presents.issuetracker.label.dto.response;

import org.presents.issuetracker.label.entity.vo.LabelVo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabelResponse {
	private Long labelId;
	private String name;
	private String textColor;
	private String backgroundColor;

	public static LabelResponse fromVo(LabelVo labelVo) {
		return LabelResponse.builder()
			.labelId(labelVo.getId())
			.name(labelVo.getName())
			.backgroundColor(labelVo.getBackgroundColor())
			.textColor(labelVo.getTextColor())
			.build();
	}
}
