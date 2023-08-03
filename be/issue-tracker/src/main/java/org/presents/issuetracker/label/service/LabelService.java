package org.presents.issuetracker.label.service;

import org.presents.issuetracker.global.dto.response.LabelResponse;
import org.presents.issuetracker.label.dto.request.LabelCreateRequest;
import org.presents.issuetracker.label.dto.request.LabelUpdateRequest;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.label.repository.LabelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<LabelPreviewResponse> getLabels() {
        List<Label> labels = labelRepository.findAll();

        return labels.stream().map(label -> LabelPreviewResponse.builder()
                        .id(label.getId())
                        .name(label.getName())
                        .backgroundColor(label.getBackgroundColor())
                        .textColor(label.getTextColor())
                        .build())
                .collect(Collectors.toList());
    }

    public LabelResponse create(LabelCreateRequest labelCreateRequest) {
        Long createdId = labelRepository.save(
                Label.of(
                        labelCreateRequest.getName(),
                        labelCreateRequest.getDescription(),
                        labelCreateRequest.getBackgroundColor(),
                        labelCreateRequest.getTextColor()));

        return LabelResponse.builder().id(createdId).build();
    }

    public LabelResponse update(LabelUpdateRequest labelUpdateRequest) {
        Long id = labelUpdateRequest.getId();
        Label label = labelRepository.findById(id);

        label = label.updateFrom(labelUpdateRequest);
        labelRepository.update(label);

        return LabelResponse.builder().id(label.getId()).build();
    }
}
