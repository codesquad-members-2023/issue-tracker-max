package org.presents.issuetracker.label.dto.response;

import org.presents.issuetracker.label.entity.vo.LabelVo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelResponse {
	private Long labelId;
	private String name;
	private String textColor;
	private String backgroundColor;

	@Builder
	private LabelResponse(Long labelId, String name, String textColor, String backgroundColor) {
		this.labelId = labelId;
		this.name = name;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	public static LabelResponse fromVo(LabelVo labelVo) {
		return LabelResponse.builder()
			.labelId(labelVo.getId())
			.name(labelVo.getName())
			.backgroundColor(labelVo.getBackgroundColor())
			.textColor(labelVo.getTextColor())
			.build();
	}
}
