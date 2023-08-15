package org.presents.issuetracker.milestone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.global.dto.response.IdResponseDto;
import org.presents.issuetracker.milestone.dto.request.MilestoneRequest;
import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.milestone.entity.Milestone;
import org.presents.issuetracker.milestone.repository.MilestoneRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;

    public List<MilestonePreviewResponse> getMilestonePreviews() {
        return milestoneRepository.findPreviews().stream()
                .map(MilestonePreviewResponse::from)
                .collect(Collectors.toList());
    }

    public IdResponseDto create(MilestoneRequest milestoneRequest) {
        Milestone milestone = Milestone.create(
                milestoneRequest.getName(),
                milestoneRequest.getDeadline(),
                milestoneRequest.getDescription());

        Long id = milestoneRepository.save(milestone);

        return IdResponseDto.builder().id(id).build();
    }
}
