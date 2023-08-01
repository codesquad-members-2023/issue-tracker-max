package codesquard.app.label.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.label.dto.LabelRequestDto;
import codesquard.app.label.entity.Label;
import codesquard.app.label.repository.LabelRepository;

@Service
@Transactional
public class LabelService {
	private final LabelRepository labelRepository;

	public LabelService(LabelRepository labelRepository) {
		this.labelRepository = labelRepository;
	}

	public Long saveLabel(LabelRequestDto labelRequestDto) {
		Label label = LabelRequestDto.toEntity(labelRequestDto);
		return labelRepository.save(label).orElseThrow(() -> new RuntimeException("임시"));
	}
}
