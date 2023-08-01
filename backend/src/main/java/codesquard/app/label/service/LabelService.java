package codesquard.app.label.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.label.dto.LabelRequest;
import codesquard.app.label.entity.Label;
import codesquard.app.label.repository.LabelRepository;

@Service
public class LabelService {
	private final LabelRepository labelRepository;

	public LabelService(LabelRepository labelRepository) {
		this.labelRepository = labelRepository;
	}

	@Transactional
	public Long saveLabel(LabelRequest labelRequest) {
		Label label = LabelRequest.toEntity(labelRequest);
		return labelRepository.save(label).orElseThrow(() -> new RuntimeException("임시"));
	}
}
