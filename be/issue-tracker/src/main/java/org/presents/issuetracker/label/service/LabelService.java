package org.presents.issuetracker.label.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.presents.issuetracker.global.dto.response.LabelIdResponse;
import org.presents.issuetracker.label.dto.request.LabelCreateRequest;
import org.presents.issuetracker.label.dto.request.LabelUpdateRequest;
import org.presents.issuetracker.label.dto.response.LabelDetailResponse;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.label.repository.LabelRepository;
import org.springframework.stereotype.Service;

@Service
public class LabelService {

	private final LabelRepository labelRepository;

	public LabelService(LabelRepository labelRepository) {
		this.labelRepository = labelRepository;
	}

	public List<LabelDetailResponse> getLabelDetails() {
		List<Label> labels = labelRepository.findAll();

		return labels.stream().map(label -> LabelDetailResponse.builder()
				.id(label.getId())
				.name(label.getName())
				.description(label.getDescription())
				.backgroundColor(label.getBackgroundColor())
				.textColor(label.getTextColor())
				.build())
			.collect(Collectors.toList());
	}

	public List<LabelPreviewResponse> getLabelPreviews() {
		List<Label> labelPreviews = labelRepository.findPreviews();

		return labelPreviews.stream()
				.map(label -> LabelPreviewResponse.of(
						label.getId(),
						label.getName(),
						label.getTextColor(),
						label.getBackgroundColor()))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public LabelIdResponse create(LabelCreateRequest labelCreateRequest) {
		Long createdId = labelRepository.save(
			Label.of(
				labelCreateRequest.getName(),
				labelCreateRequest.getDescription(),
				labelCreateRequest.getBackgroundColor(),
				labelCreateRequest.getTextColor()));

		return LabelIdResponse.from(createdId);
	}

	public LabelIdResponse update(LabelUpdateRequest labelUpdateRequest) {
		Long id = labelUpdateRequest.getId();
		Label label = labelRepository.findById(id);

		String name = Optional.ofNullable(labelUpdateRequest.getName())
				.filter(newName -> !newName.equals(label.getName()))
				.orElse(label.getName());

		String description = Optional.ofNullable(labelUpdateRequest.getDescription())
				.filter(newDescription -> !newDescription.equals(label.getDescription()))
				.orElse(label.getDescription());

		String backgroundColor = Optional.ofNullable(labelUpdateRequest.getBackgroundColor())
				.filter(newBackgroundColor -> !newBackgroundColor.equals(label.getBackgroundColor()))
				.orElse(label.getBackgroundColor());

		String textColor = Optional.ofNullable(labelUpdateRequest.getTextColor())
				.filter(newTextColor -> !newTextColor.equals(label.getTextColor()))
				.orElse(label.getTextColor());

		Label updatedLabel = Label.of(label.getId(), name, description, backgroundColor, textColor);
		labelRepository.update(updatedLabel);

		return LabelIdResponse.from(updatedLabel.getId());
	}

	public void delete(Long id) {
		labelRepository.deleteById(id);
	}
}
