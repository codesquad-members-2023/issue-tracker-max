package com.issuetrackermax.controller.milestone;

import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.service.milestone.MilestoneService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MilestoneController {
	private final MilestoneService milestoneService;
}
