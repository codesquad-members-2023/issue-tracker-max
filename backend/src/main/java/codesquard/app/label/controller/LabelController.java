package codesquard.app.label.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquard.app.label.service.LabelService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LabelController {

	private final LabelService labelService;
}
