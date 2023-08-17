package codesquard.app.label.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.api.errors.exception.NoSuchLabelException;
import codesquard.app.label.dto.request.LabelSaveRequest;
import codesquard.app.label.dto.request.LabelUpdateRequest;
import codesquard.app.label.dto.response.LabelReadResponse;
import codesquard.app.label.dto.response.LabelsResponse;
import codesquard.app.label.entity.Label;
import codesquard.app.label.repository.LabelRepository;
import codesquard.app.milestone.entity.MilestoneStatus;
import codesquard.app.milestone.repository.MilestoneRepository;

@Transactional
@Service
public class LabelService {
	private final LabelRepository labelRepository;
	private final MilestoneRepository milestoneRepository;

	public LabelService(final LabelRepository labelRepository, final MilestoneRepository milestoneRepository) {
		this.labelRepository = labelRepository;
		this.milestoneRepository = milestoneRepository;
	}

	public Long saveLabel(final LabelSaveRequest labelSaveRequest, Long userId) {
		Label label = LabelSaveRequest.toEntity(labelSaveRequest, userId);
		return labelRepository.save(label).orElseThrow(NoSuchLabelException::new);
	}

	public Long updateLabel(final Long labelId, final Long userId, final LabelUpdateRequest labelUpdateRequest) {
		return labelRepository.updateBy(labelId, LabelUpdateRequest.toEntity(labelUpdateRequest, userId));
	}

	public Long deleteLabel(final Long labelId, final Long userId) {
		return labelRepository.deleteBy(labelId);
	}

	public LabelReadResponse makeLabelReadResponse() {
		// 1. labels 배열
		List<LabelsResponse> labels = LabelsResponse.toDtoList(labelRepository.findAll());

		// 2. labelCount 가져오기
		Long labelCount = milestoneRepository.countLabels();

		// 3. openedMilestoneCount 가져오기 (closed는 필요 없음)
		Long openedMilestoneCount = milestoneRepository.countMilestonesBy(MilestoneStatus.OPENED);

		return new LabelReadResponse(openedMilestoneCount, labelCount, labels);
	}
}
