package com.issuetrackermax.controller.label;

import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.service.label.LabelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LabelController {
	private final LabelService labelService;
}
