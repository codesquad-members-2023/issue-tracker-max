package com.codesquad.issuetracker.api.filter.service;

import com.codesquad.issuetracker.api.filter.dto.LabelFilter;
import com.codesquad.issuetracker.api.filter.dto.MilestoneFilter;
import com.codesquad.issuetracker.api.label.repository.LabelRepository;
import com.codesquad.issuetracker.api.milestone.repository.MilestoneRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FilterService {

    private final OrganizationRepository organizationRepository;
    private final MilestoneRepository milestoneRepository;
    private final LabelRepository labelRepository;

    @Transactional(readOnly = true)
    public List<MilestoneFilter> readMilestone(String organizationTitle) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle).orElseThrow();
        return milestoneRepository.findFilterByOrganizationId(organizationId);
    }

    @Transactional(readOnly = true)
    public List<LabelFilter> readAllLabel(String organizationTitle) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle).orElseThrow();
        return labelRepository.findFilterByOrganizationId(organizationId);
    }
}
