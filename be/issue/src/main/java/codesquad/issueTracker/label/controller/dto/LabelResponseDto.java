package codesquad.issueTracker.label.controller.dto;

import java.util.List;

import codesquad.issueTracker.label.domain.Label;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class LabelResponseDto {
	private List<LabelVO> labels;
	private int milestoneCount;

	public LabelResponseDto(List<LabelVO> labels, int milestoneCount) {
		this.labels = labels;
		this.milestoneCount = milestoneCount;
	}

	public static LabelResponseDto of(List<LabelVO> labels, int milestoneCount){
		return new LabelResponseDto(labels, milestoneCount);
	}
}
