package codesquard.app.label.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.label.dto.LabelSaveRequest;
import codesquard.app.label.dto.LabelUpdateRequest;
import codesquard.app.label.entity.Label;
import codesquard.app.label.repository.LabelRepository;

@Service
public class LabelService {
	private final LabelRepository labelRepository;

	public LabelService(LabelRepository labelRepository) {
		this.labelRepository = labelRepository;
	}

	@Transactional
	public Long saveLabel(LabelSaveRequest labelSaveRequest) {
		Label label = LabelSaveRequest.toEntity(labelSaveRequest);
		return labelRepository.save(label).orElseThrow(() -> new RuntimeException("임시"));
	}

	@Transactional
	public void updateLabel(Long labelId, LabelUpdateRequest labelUpdateRequest) {
		labelRepository.updateBy(labelId, LabelUpdateRequest.toEntity(labelUpdateRequest));
	}
}
