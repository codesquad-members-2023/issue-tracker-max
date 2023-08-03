package org.presents.issuetracker.label.service;

import org.presents.issuetracker.global.dto.response.LabelResponse;
import org.presents.issuetracker.label.dto.request.LabelCreateRequest;
import org.presents.issuetracker.label.dto.request.LabelUpdateRequest;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.label.repository.LabelRepository;
import org.springframework.stereotype.Service;

@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
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
