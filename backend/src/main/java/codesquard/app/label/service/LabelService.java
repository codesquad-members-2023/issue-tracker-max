package codesquard.app.label.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.label.dto.LabelReadResponse;
import codesquard.app.label.dto.LabelSaveRequest;
import codesquard.app.label.dto.LabelUpdateRequest;
import codesquard.app.label.dto.LabelsResponse;
import codesquard.app.label.entity.Label;
import codesquard.app.label.repository.LabelRepository;
import codesquard.app.milestone.entity.MilestoneStatus;
import codesquard.app.milestone.repository.MilestoneRepository;

@Service
public class LabelService {
	private final LabelRepository labelRepository;
	private final MilestoneRepository milestoneRepository;

	public LabelService(LabelRepository labelRepository, MilestoneRepository milestoneRepository) {
		this.labelRepository = labelRepository;
		this.milestoneRepository = milestoneRepository;
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

	@Transactional
	public void deleteLabel(Long labelId) {
		labelRepository.deleteBy(labelId);
	}

	@Transactional
	public LabelReadResponse makeLabelReadResponse() {
		// 1. labels 배열
		List<LabelsResponse> labels = toDto();

		// 2. labelCount 가져오기
		Long labelCount = milestoneRepository.countLabels();

		// 3. openedMilestoneCount, closedMilestoneCount 가져오기 (List로 묶는 거 아님)
		Long openedMilestoneCount = milestoneRepository.countMilestonesBy(MilestoneStatus.OPENED);
		Long closedMilestoneCount = milestoneRepository.countMilestonesBy(MilestoneStatus.CLOSED);

		return new LabelReadResponse(openedMilestoneCount, closedMilestoneCount, labelCount, labels);
	}

	private List<LabelsResponse> toDto() {
		return labelRepository.findAll()
			.stream()
			.map(LabelsResponse::fromEntity)
			.collect(Collectors.toUnmodifiableList());
	}
}
