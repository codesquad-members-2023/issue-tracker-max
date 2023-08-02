package org.presents.issuetracker.label.service;

import org.presents.issuetracker.label.dto.request.LabelRequestDto;
import org.presents.issuetracker.label.dto.request.LabelUpdateRequestDto;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.label.repository.LabelRepository;
import org.springframework.stereotype.Service;

@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Long create(LabelRequestDto labelRequestDto) {
        return labelRepository.save(
                Label.of(
                        labelRequestDto.getName(),
                        labelRequestDto.getDescription(),
                        labelRequestDto.getBackgroundColor(),
                        labelRequestDto.getTextColor()));
    }

    public Long update(LabelUpdateRequestDto dto) {
        Long id = dto.getId();
        Label label = labelRepository.findById(id);

        label = label.updateFrom(dto);

        return labelRepository.update(label).getId();
    }
}
