package codesquad.issueTracker.label.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.label.service.LabelService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LabelController {
	private final LabelService labelService;
}
