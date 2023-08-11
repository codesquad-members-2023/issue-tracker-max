package codesquad.issueTracker.label.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.label.domain.Label;
import codesquad.issueTracker.label.dto.CreateLabelResponseDto;
import codesquad.issueTracker.label.dto.LabelRequestDto;
import codesquad.issueTracker.label.dto.LabelResponseDto;
import codesquad.issueTracker.label.repository.LabelRepository;
import codesquad.issueTracker.label.vo.LabelVo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelService {
	private final LabelRepository labelRepository;

	public CreateLabelResponseDto save(LabelRequestDto labelRequestDto) {
		return CreateLabelResponseDto.from(labelRepository.insert(Label.toEntity(labelRequestDto)));
	}

	public void modify(Long labelId, LabelRequestDto labelRequestDto) {
		labelRepository.update(labelId, Label.toEntity(labelRequestDto));
	}

	public void delete(Long labelId) {
		labelRepository.delete(labelId);
	}

	public LabelResponseDto findAll() {
		List<LabelVo> labels = new ArrayList<>();
		labelRepository.findAll()
			.orElseThrow(() -> new CustomException(ErrorCode.LABEL_FIND_FAILED))
			.forEach(label ->
				labels.add(LabelVo.from(label)));
		return LabelResponseDto.of(labels,
			labelRepository.findMilestonesCount().orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MILESTONE)));
	}
}
