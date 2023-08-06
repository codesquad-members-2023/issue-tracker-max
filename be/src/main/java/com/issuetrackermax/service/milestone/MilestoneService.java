package com.issuetrackermax.service.milestone;

import org.springframework.stereotype.Service;

import com.issuetrackermax.domain.milestone.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {
	private final MilestoneRepository milestoneRepository;

}
