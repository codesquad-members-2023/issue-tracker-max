package codesquard.app.milestone.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquard.app.milestone.service.MilestoneService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MilestoneRestController {
	private final MilestoneService milestoneService;
}
