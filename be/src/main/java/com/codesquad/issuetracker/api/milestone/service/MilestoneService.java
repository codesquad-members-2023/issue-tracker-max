package com.codesquad.issuetracker.api.milestone.service;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.api.milestone.dto.response.EditMileStoneResponse;
import com.codesquad.issuetracker.api.milestone.dto.response.MilestonesResponse;
import com.codesquad.issuetracker.api.milestone.repository.MilestoneRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public long create(String organizationTitle, MilestoneRequest mileStoneRequest) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle)
                .orElseThrow();
        Milestone milestone = MilestoneRequest.toEntity(mileStoneRequest, organizationId);
        return milestoneRepository.save(milestone)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public EditMileStoneResponse read(Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow();
        return EditMileStoneResponse.from(milestone);
    }

    @Transactional
    public long update(Long milestoneId, MilestoneRequest mileStoneRequest) {
        Milestone milestone = MilestoneRequest.toEntity(milestoneId,mileStoneRequest);
        milestoneRepository.update(milestone);
        return milestoneId;
    }

    @Transactional
    public void delete(Long milestoneId) {
        milestoneRepository.deleteById(milestoneId);
    }

    public MilestonesResponse readAll(String organizationTitle) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle)
                .orElseThrow();
        List<Milestone> milestones = milestoneRepository.readAllByOrganizationId(organizationId);
        return MilestonesResponse.from(milestones);
    }

    @Transactional
    public void updateStatus(Long milestoneId, boolean isClosed) {
        milestoneRepository.updateStatus(milestoneId, isClosed);
    }
}
