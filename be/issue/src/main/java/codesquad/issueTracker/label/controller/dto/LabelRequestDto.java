package codesquad.issueTracker.label.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LabelRequestDto {
	private String name;
	private String textColor;
	private String backgroundColor;
	private String description;
}
