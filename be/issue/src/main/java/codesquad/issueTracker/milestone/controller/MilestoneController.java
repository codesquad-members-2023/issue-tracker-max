package codesquad.issueTracker.milestone.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.milestone.service.MilestoneService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MilestoneController {
	private final MilestoneService milestoneService;

}
