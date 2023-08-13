package codesquad.issueTracker.label.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LabelRequestDto {
	@NotNull(message = "라벨 제목을 입력해주세요")
	private String name;
	@NotNull(message = "텍스트 색상을 입력해주세요")
	private String textColor;
	@NotNull(message = "배경 색상을 입력해주세요")
	private String backgroundColor;
	private String description;
}
