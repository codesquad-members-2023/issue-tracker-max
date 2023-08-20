package org.presents.issuetracker.milestone.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.presents.issuetracker.global.dto.response.IdResponseDto;
import org.presents.issuetracker.milestone.dto.request.MilestoneRequest;
import org.presents.issuetracker.milestone.dto.request.MilestoneStatusUpdateRequest;
import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.milestone.entity.Milestone;
import org.presents.issuetracker.milestone.entity.vo.MilestoneInfo;
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

    public MilestoneInfo getMilestoneDetails(String status) {
        return milestoneRepository.findDetailsByStatus(status);
    }

    public IdResponseDto create(MilestoneRequest milestoneRequest) {
        Milestone milestone = Milestone.of(
                milestoneRequest.getName(),
                milestoneRequest.getDeadline(),
                milestoneRequest.getDescription());

        Long id = milestoneRepository.save(milestone);

        return IdResponseDto.builder().id(id).build();
    }

    public IdResponseDto update(MilestoneRequest milestoneRequest) {
        Long id = milestoneRequest.getId();
        Milestone milestone = milestoneRepository.findById(id);

        String name = Optional.ofNullable(milestoneRequest.getName())
                .filter(newName -> !newName.equals(milestone.getName()))
                .orElse(milestone.getName());

        LocalDateTime deadline = Optional.ofNullable(milestoneRequest.getDeadline())
                .filter(newDescription -> !newDescription.equals(milestone.getDeadline()))
                .orElse(milestone.getDeadline());

        String description = Optional.ofNullable(milestoneRequest.getDescription())
                .filter(newBackgroundColor -> !newBackgroundColor.equals(milestone.getDescription()))
                .orElse(milestone.getDescription());

        Milestone updatedMilestone = Milestone.of(milestone.getId(), name, deadline, description, milestone.getStatus());

        milestoneRepository.update(updatedMilestone);

        return IdResponseDto.builder().id(updatedMilestone.getId()).build();
    }

    public void updateStatus(MilestoneStatusUpdateRequest milestoneStatusUpdateRequest) {
        milestoneRepository.updateStatus(milestoneStatusUpdateRequest.getId(), milestoneStatusUpdateRequest.getStatus());
    }

    public void delete(Long id) {
        milestoneRepository.deleteById(id);
    }
}
